package sg.edu.nus.se.its.validation.translator;

import com.microsoft.z3.*;
import java.io.InputStream;
import java.io.IOException;
import java.util.*;
import java.util.logging.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.javatuples.Pair;
import sg.edu.nus.se.its.model.*;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.builder.Z3ExpressionTreeBuilder;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.ForLoopZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.WhileLoopZ3Expression;

public class TranslationHandler {
  private static final Logger LOGGER = Logger.getLogger(TranslationHandler.class.getName());
  private Context ctx;
  private TranslationContext translationContext;

  public TranslationHandler(Context ctx, HashMap<Integer, Expr> scanfExpressions, boolean isReference, boolean hasScanf) {
    this.ctx = ctx;
    this.translationContext = new TranslationContext(scanfExpressions, isReference, hasScanf);
  }

  public ArrayList<Expr> translateFunctionToZ3Constraints(Function function) {
    LOGGER.info("*********************************");
    LOGGER.info("Translating function:\n" + function);
    this.storeVariableTypesInTranslationContext(function); // Does not store $cond
    HashMap<Integer, ArrayList<Pair<String, Expression>>> locexprs = function.getLocexprs();
    int locKey = 1;
    HashMap<Integer, String> locdescs = function.getLocdescs();
    ArrayList<Expr> translatedExpressions = new ArrayList<>();
    for (ArrayList<Pair<String, Expression>> locexpr : locexprs.values()) {
      String description = locdescs.get(locKey);
      boolean isForLoopBody = isForLoopBody(description);
      boolean isWhileLoopBody = isWhileLoopBody(description);
      boolean isForUpdate = isForUpdate(description);
      // Check this location is the body

      int loopsBodyTerminate = locexprs.get(locKey).size();
      for (Pair<String, Expression> expressionPair : locexpr) {
          loopsBodyTerminate--;
          String expressionName = expressionPair.getValue0();
        Expression expression = expressionPair.getValue1();
        String expressionType = expression.getType();
        String loopName = extractLoopName(description);

        // If expressionName is $cond, get the line number and reconstruct the expressionPair as $cond{line}
        if (expressionName.equals("$cond")) {
          // Todo: When and how to interpret Loops and add to the translated expressions
          // Todo: Reset condition
          // Todo: Reset loop bodiAes
          expressionPair = classifyLoopCondition(description, expressionPair);
          expressionName = expressionPair.getValue0();
          storeLoopConditionVariableTypesInTranslationContext(expressionName, loopName);
          List<Z3Expression> expressionTree = Z3ExpressionTreeBuilder.buildZ3ExpressionTree(this.translationContext, expressionPair);

          String whilePattern = "while";
          Pattern whileP = Pattern.compile(whilePattern);
          Matcher m = whileP.matcher(loopName);
          if (m.find()) {
            this.translationContext.storeWhileLoopCondition(loopName, (ArrayList<Z3Expression>) expressionTree);
          }

          String loopPattern = "for";
          Pattern loopP = Pattern.compile(loopPattern);
          m = loopP.matcher(loopName);
          if (m.find()) {
            this.translationContext.storeForLoopCondition(loopName, expressionTree);
          }

          // Break the loop
          continue;
        }

        if (isForUpdate) {
            List<Z3Expression> expressionTree = Z3ExpressionTreeBuilder.buildZ3ExpressionTree(this.translationContext, expressionPair);
            this.translationContext.storeForLoopUpdate(loopName, (ArrayList<Z3Expression>) expressionTree);

            continue;
        }

        if (isWhileLoopBody)  {
          List<Z3Expression> expressionTree = Z3ExpressionTreeBuilder.buildZ3ExpressionTree(this.translationContext, expressionPair);
          this.translationContext.storeWhileLoopBody(loopName, (ArrayList<Z3Expression>) expressionTree);
          // Break the loop
          if (loopsBodyTerminate == 0) {
            // Evaluate loop
              // Create and add these into WhileLoopZ3Expression;
              WhileLoopZ3Expression whileLoopZ3Expression = new WhileLoopZ3Expression(loopName);

              // Interpret
              translatedExpressions.remove(1);
              // Add interpreted into translatedExpressions
              translatedExpressions.addAll(whileLoopZ3Expression.interpretLoop(translationContext, ctx));
          }

          continue;
        }

        if (isForLoopBody) {
            List<Z3Expression> expressionTree = Z3ExpressionTreeBuilder.buildZ3ExpressionTree(this.translationContext, expressionPair);
            this.translationContext.storeForLoopBody(loopName, (ArrayList<Z3Expression>) expressionTree);
            // Break the loop
            if (loopsBodyTerminate == 0) {
                // Evaluate loop
                // Create and add these into WhileLoopZ3Expression;
                ForLoopZ3Expression forLoopZ3Expression = new ForLoopZ3Expression(loopName);

                // Interpret
                if (translatedExpressions.size() > 2) {
                    Expr removed = translatedExpressions.remove(2);
                    Expr[] exprs = removed.getArgs();
                    forLoopZ3Expression.setReturnVariableName(exprs[1]);
                }

//                for (Expr e : translatedExpressions) {
//                    System.out.println("First: " + e.getArgs()[0]);
//                    System.out.println("Second: " + e.getArgs()[1]);
//                }

                // Add interpreted into translatedExpressions
                translatedExpressions.addAll(forLoopZ3Expression.interpretLoop(translationContext, ctx));
            }

            continue;
        }


        LOGGER.info("------------------");
        LOGGER.info("expressionName: " + expressionName);
        LOGGER.info("expression: " + expression);
        LOGGER.info("expressionType " + expressionType);
        LOGGER.info("------------------");

        if (!expressionName.equals("$in")) {
          translatedExpressions.addAll(this.translateExpression(expressionPair));
        }
      }
      locKey++;
    }
    LOGGER.info("translation of function done");
    LOGGER.info("*********************************");
    return translatedExpressions;
  }

    private boolean isForUpdate(String description) {
        Pattern patternForUpdate = Pattern.compile("update of the 'for' loop at line (\\d+)");
        Matcher matcher = patternForUpdate.matcher(description);
        return matcher.find();
    }

    private Pair<String, Expression> classifyLoopCondition(String description, Pair<String, Expression> expressionPair) {
        // I want to extract the line number and while/loop from the description
// Define patterns for each type of loop part
        Pattern patternLoopCondition = Pattern.compile("the condition of the 'for' loop at line (\\d+)");
        Pattern patternWhileCondition = Pattern.compile("the condition of the 'while' loop at line (\\d+)");
        //  Pattern patternUpdate = Pattern.compile("update of the 'for' loop at line (\\d+)");
        //  Pattern patternBody = Pattern.compile("inside the body of the 'for' loop beginning at line (\\d+)");

        // Check for "For-Condition"
        Matcher matcher = patternLoopCondition.matcher(description);
        if (matcher.find()) {
            String variableName = "$cond%s";
            variableName = String.format(variableName, matcher.group(1));
            return Pair.with(variableName, expressionPair.getValue1());
        }
        matcher = patternWhileCondition.matcher(description);
        if (matcher.find()) {
            String variableName = "$cond%s";
            variableName = String.format(variableName, matcher.group(1));
            return Pair.with(variableName, expressionPair.getValue1());
        }
        // Check for "For-Update"
//    matcher = patternUpdate.matcher(input);
//    if (matcher.find()) {
//      return Pair.with("For-Update", matcher.group(1));
//    }
//    // Check for "For-Body"
//    matcher = patternBody.matcher(input);
//    if (matcher.find()) {
//      return Pair.with("For-Body", matcher.group(1));
//    }
        // If none match, return "Not-For"
        return Pair.with("Not-Loop", expressionPair.getValue1());
    }

    private boolean isForLoopBody (String description) {
        Pattern patternForLoopBody = Pattern.compile("inside the body of the 'for' loop beginning at line (\\d+)");
        Matcher matcher = patternForLoopBody.matcher(description);
        return matcher.find();
    }

    private boolean isWhileLoopBody(String description) {
        Pattern patternWhileLoopBody = Pattern.compile("inside the body of the 'while' loop beginning at line (\\d+)");
        Matcher matcher = patternWhileLoopBody.matcher(description);
        return matcher.find();
    }

    private String extractLoopName(String description) {
        Pattern patternLoopCondition = Pattern.compile("the condition of the 'for' loop at line (\\d+)");
        Pattern patternWhileCondition = Pattern.compile("the condition of the 'while' loop at line (\\d+)");
        Pattern patternForLoopBody = Pattern.compile("inside the body of the 'for' loop beginning at line (\\d+)");
        Pattern patternWhileLoopBody = Pattern.compile("inside the body of the 'while' loop beginning at line (\\d+)");
        Pattern patternForLoopUpdate = Pattern.compile("update of the 'for' loop at line (\\d+)");

        Matcher matcher = patternLoopCondition.matcher(description);
        String variableName = "";
        if (matcher.find()) {
            variableName = "forLoop%s";
            variableName = String.format(variableName, matcher.group(1));
            return variableName;
        }
        matcher = patternForLoopBody.matcher(description);
        if (matcher.find()) {
            variableName = "forLoop%s";
            variableName = String.format(variableName, matcher.group(1));
            return variableName;
        }
        matcher = patternWhileCondition.matcher(description);
        if (matcher.find()) {
            variableName = "whileLoop%s";
            variableName = String.format(variableName, matcher.group(1));
            return variableName;
        }
        matcher = patternWhileLoopBody.matcher(description);
        if (matcher.find()) {
            variableName = "whileLoop%s";
            variableName = String.format(variableName, matcher.group(1));
            return variableName;
        }
        matcher = patternForLoopUpdate.matcher(description);
        if (matcher.find()) {
            variableName = "forLoop%s";
            variableName = String.format(variableName, matcher.group(1));
            return variableName;
        }
        return variableName;
    }

  private List<Expr> translateExpression(Pair<String, Expression> expressionPair) {
    LOGGER.info("------------------");
    LOGGER.info("Translating expression into z3 Expr");
    List<Z3Expression> expressionsTree = Z3ExpressionTreeBuilder.buildZ3ExpressionTree(this.translationContext, expressionPair);
    LOGGER.info("Z3Expression tree built: " + expressionsTree);
    LOGGER.info("------------------");
    return expressionsTree.stream().map(e -> e.interpret(translationContext, ctx)).collect(Collectors.toList());
  }

  public Expr[] getArgList(String functionName) {
    return this.translationContext.getArgList(functionName);
  }

  private void storeVariableTypesInTranslationContext(Function function) {
    HashMap<String, String> variableTypes = function.getTypes();
    for (String key : variableTypes.keySet()) {
      translationContext.storeVariableType(key, variableTypes.get(key));
    }

    String rettype = function.getRettype();
    translationContext.storeVariableType("$ret", rettype);
    translationContext.storeVariableType("$out", "String");
  }

  private void storeLoopConditionVariableTypesInTranslationContext(String loopCondition, String loopName) {
    translationContext.storeVariableType(loopCondition, loopName);
  }

  public static Pair<String, String> classifyAndExtractLine(String input) {
    // Define patterns for each type of loop part
    Pattern patternLoopCondition = Pattern.compile("the condition of the 'for' loop at line (\\d+)");
    Pattern patternWhileCondition = Pattern.compile("the condition of the 'while' loop at line (\\d+)");
    //  Pattern patternUpdate = Pattern.compile("update of the 'for' loop at line (\\d+)");
    //  Pattern patternBody = Pattern.compile("inside the body of the 'for' loop beginning at line (\\d+)");

    // Check for "For-Condition"
    Matcher matcher = patternLoopCondition.matcher(input);
    if (matcher.find()) {
      String variableName = "$cond%s";
      variableName = String.format(variableName, matcher.group(1));
      return Pair.with(variableName, matcher.group(1));
    }
    matcher = patternWhileCondition.matcher(input);
    if (matcher.find()) {
      String variableName = "$cond%s";
      variableName = String.format(variableName, matcher.group(1));
      return Pair.with(variableName, matcher.group(1));
    }
    // Check for "For-Update"
//    matcher = patternUpdate.matcher(input);
//    if (matcher.find()) {
//      return Pair.with("For-Update", matcher.group(1));
//    }
//    // Check for "For-Body"
//    matcher = patternBody.matcher(input);
//    if (matcher.find()) {
//      return Pair.with("For-Body", matcher.group(1));
//    }
    // If none match, return "Not-For"
    return Pair.with("Not-Loop", "");
  }

  public void storeFunctionNames(ArrayList<String> functionNames) {
      translationContext.storeFunctionNames(functionNames);
  }

  public ArrayList<String> getFunctionArg(String functionName) {
      return translationContext.getFunctionArg(functionName);
  }

  public HashMap<String, Expr[]> getFunctionArgMap() {
    return translationContext.getFunctionArgMap();
  }

  public Expr getReturn() {
    return this.translationContext.getReturn();
  }
}
