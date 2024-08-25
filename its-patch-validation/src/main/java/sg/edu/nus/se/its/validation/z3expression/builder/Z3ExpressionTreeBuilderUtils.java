package sg.edu.nus.se.its.validation.z3expression.builder;

import com.microsoft.z3.*;
import org.javatuples.Pair;
import sg.edu.nus.se.its.model.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.utils.VariableType;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.ArrayVariableZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.StringZ3Expression;

import java.util.Collection;

public class Z3ExpressionTreeBuilderUtils {

  public static boolean isAssignment(Pair<String, Expression> expressionPair) {
    System.out.println(" checking assignment..... " + expressionPair);
    String expressionName = expressionPair.getValue0();
    Expression expression = expressionPair.getValue1();
    String expressionType = expression.getType();

    if (expressionType.equals("Operation") && expression.toString().contains("ite")) {
      return false;
    }
    if ((expressionType.equals("Constant")) ||
       expressionType.equals("Variable") ||
       !expressionName.equals("$ret") ||
            (expressionType.equals("Operation") ) ) { // TODO: WHAT DOES THIS BREAK
      return true;
    }
    return false;
  }

  public static Sort getSort(String variableName, TranslationContext translationContext, Context ctx) {
    VariableType type = translationContext.getVariableType(variableName);
    switch (type) {
    case INT_ARRAY:
    case BYTE_ARRAY:
    case LONG_ARRAY:
    case SHORT_ARRAY:
      return ctx.getIntSort();
    case FLOAT_ARRAY:
    case DOUBLE_ARRAY:
      return ctx.getRealSort();
    case BOOLEAN_ARRAY:
      return ctx.getBoolSort();
    case CHAR_ARRAY:
      return ctx.getStringSort();
    }
    throw new RuntimeException(type + " is not a supported array type :(");
  }

}
