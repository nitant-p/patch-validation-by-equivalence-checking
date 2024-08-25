package sg.edu.nus.se.its.validation.tokenizer.token;

import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.IntVariableZ3Expression;

import java.util.List;
import java.util.Stack;

public class VariableToken implements Token {
  private final String value;

  public VariableToken(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  @Override
  public void updateZ3ExpressionTree(Stack<Z3Expression> z3ExpressionStack, TranslationContext translationContext, String expressionName, List<Z3Expression> finalExpressions) {
    if (translationContext.getVariableZ3Expression(value) == null) {
      System.out.println("Variable not found: " + value);
      z3ExpressionStack.push(new IntVariableZ3Expression(value)); //
    } else {
      z3ExpressionStack.push(translationContext.getVariableZ3Expression(value));
    }
  }
}
