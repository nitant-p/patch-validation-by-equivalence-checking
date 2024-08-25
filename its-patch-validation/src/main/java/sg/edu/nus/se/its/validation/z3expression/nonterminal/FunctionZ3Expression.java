package sg.edu.nus.se.its.validation.z3expression.nonterminal;

import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.IntZ3Expression;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class FunctionZ3Expression implements Z3Expression {

  private String function;
  private String argString;
  private ArrayList<String> argList;
  private String argType;

  public FunctionZ3Expression(String function) {
    System.out.println("FunctionZ3Expression: " + function);
    this.function = function.substring(0, function.indexOf("("));
    System.out.println("FunctionZ3Expression: " + this.function);
    Pattern pattern = Pattern.compile(("\\((.*?)\\)"));
    Matcher matcher = pattern.matcher(function);

    if (matcher.find()) {
      this.argString = matcher.group(1);
      System.out.println("FunctionZ3Expression: " + this.argString);
      String[] argumentsArray = argString.split(",\\s*");
      System.out.println("FunctionZ3Expression: " + argumentsArray[0]);
      this.argList = new ArrayList<>();
      for (String argument : argumentsArray) {
        // Remove quotes if argument is a string literal
        if (argument.startsWith("\"") && argument.endsWith("\"")) {
          argument = argument.substring(1, argument.length() - 1);
        }
        argList.add(argument);
      }
    }

  }

  @Override
  public String toString() {
    return function + "(" + argString + ")";
  }

  public ArrayList<String> getArgList() {
    return argList;
  }

  @Override
  public Expr interpret(TranslationContext translationContext, Context ctx) {
    // do the logic here
    /**
     * Kangwei approach
     * Whenever actual function is parsed int func( return x * x; }  ->translation handler
     * Get return value of that function -> store expressions into translation context
     *
     * When function token is encountered X  when function token is encountered => FunctionZ3Expression interpret => get expression from translation context and then pass into parameter value
     * // involves tokeniser
     *
     * Get function declaration from translation context
     * Pass in the parameter
     * Get return value
     *
     * translation context would have all required details of the declared function
     *
     */
    // translationContext.storeFunction(function, argList);
    Expr[] argsExpr = new Expr[argList.size()];
    for (int i = 0; i < argList.size(); i++) {
      System.out.println("FunctionZ3Expression: " + argList.get(i));
      // TODO: change to multiple types
      if (argList.get(i).matches("-?\\d+")) {
        IntZ3Expression intZ3Expression = new IntZ3Expression(Integer.parseInt(argList.get(i)));
        argsExpr[i] = intZ3Expression.interpret(translationContext, ctx);
      } else {
        // find argument type in translation context
        String argType = translationContext.getVariableType(argList.get(i)).toString();
        if (argType.equals("int")) {
          argsExpr[i] = ctx.mkIntConst(argList.get(i));
        } else if (argType.equals("float")) {
          argsExpr[i] = ctx.mkRealConst(argList.get(i));
        } else if (argType.equals("bool")) {
          argsExpr[i] = ctx.mkBoolConst(argList.get(i));
        } else {
          System.out.println("FunctionZ3Expression: Argument type not found");
        }
      }

    }
    translationContext.storeFunction(function, argsExpr);
    System.out.println("Stored function: " + function + " with args: " + argsExpr);

    // TODO: change to multiple types
    return ctx.mkFuncDecl(function, ctx.getIntSort(), ctx.getIntSort()).apply(argsExpr);
  }


}
