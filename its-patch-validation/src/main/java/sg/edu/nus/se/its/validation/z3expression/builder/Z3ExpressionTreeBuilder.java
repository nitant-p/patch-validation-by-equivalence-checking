package sg.edu.nus.se.its.validation.z3expression.builder;

import java.lang.RuntimeException;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.microsoft.z3.*;
import org.javatuples.Pair;
import sg.edu.nus.se.its.model.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.utils.Utils;
import sg.edu.nus.se.its.validation.utils.VariableType;
import sg.edu.nus.se.its.validation.tokenizer.Tokenizer;
import sg.edu.nus.se.its.validation.tokenizer.token.*;
import sg.edu.nus.se.its.validation.z3expression.terminal.*;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.*;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;


public class Z3ExpressionTreeBuilder {
    private static final Logger LOGGER = Logger.getLogger(Z3ExpressionTreeBuilder.class.getName());

    public static List<Z3Expression> buildZ3ExpressionTree(TranslationContext translationContext, Pair<String, Expression> expressionPair) {
        LOGGER.info("Building z3 expression tree from expression: " + expressionPair);
        translationContext.trackVariables(expressionPair);
        String expressionName = expressionPair.getValue0(); // j
        Expression expression = expressionPair.getValue1(); // func(10)
        LOGGER.info("Expression str is: " + expression);

        Stack<Z3Expression> z3ExpressionStack = new Stack<>();
        List<Z3Expression> finalExpressions = new ArrayList<>();

        /**
         * Check if the expression is a function call
         */
        translationContext.processExpression(expression.toString());

        // DOES NOT MEET REGEX
        // System.out.println("Expression: " + expression.toString() + " " + expression.toString().matches("\\w+\\((\"[\\w\\.]+\"|'\\w+'|\\w+)\\)"));

        ArrayList<Token> tokens = Tokenizer.tokenize(expression.toString());

    for (int i = tokens.size() - 1; i >= 0; i--) {
      Token token = tokens.get(i);
      System.out.println("Token: " + token.getValue());
      token.updateZ3ExpressionTree(z3ExpressionStack, translationContext, expressionName, finalExpressions);
    }

    if (!finalExpressions.isEmpty()) {
      System.out.println("final expression is not empty ");
      return finalExpressions;
    }

    if (Z3ExpressionTreeBuilderUtils.isAssignment(expressionPair)) {
      Z3Expression left = translationContext.getVariableZ3Expression(expressionName);
      Z3Expression val = z3ExpressionStack.pop();
      translationContext.addVariableInit(expressionName, val);
      finalExpressions.add(new AssignmentZ3Expression(left, val));
        translationContext.addAssignment(left.toString(), val);
    } else finalExpressions.add(z3ExpressionStack.pop());
    return finalExpressions;
  }

}