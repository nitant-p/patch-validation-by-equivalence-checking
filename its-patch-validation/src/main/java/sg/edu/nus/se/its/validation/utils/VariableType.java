package sg.edu.nus.se.its.validation.utils;

import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.ArrayVariableZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.BoolVariableZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.FloatVariableZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.IntVariableZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.StringVariableZ3Expression;

public enum VariableType {
  INTEGER,
  FLOAT,
  STRING,
  BOOL,
  WHILELOOP,
  FORLOOP,
  WHILELOOPCONDITION,
  INT_ARRAY,
  CHAR_ARRAY,
  FLOAT_ARRAY,
  DOUBLE_ARRAY,
  SHORT_ARRAY,
  LONG_ARRAY,
  BYTE_ARRAY,
  BOOLEAN_ARRAY;

  public Z3Expression getVariableZ3Expression(String variableName) {switch (this) {
      case INTEGER:
        return new IntVariableZ3Expression(variableName);
      case FLOAT:
        return new FloatVariableZ3Expression(variableName);
      case BOOL:
        return new BoolVariableZ3Expression(variableName);
      case STRING:
        return new StringVariableZ3Expression(variableName);
      case INT_ARRAY:
      case CHAR_ARRAY:
      case FLOAT_ARRAY:
        return new ArrayVariableZ3Expression(variableName);
      default:
        throw new RuntimeException(
                "Z3ExpressionTreeBuilder::trackVariables - the variable (" +
                        variableName +
                        ") does not have a recognisable type");
      }
    }
}
