package sg.edu.nus.se.its.validation.tokenizer.token;

import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.FloatZ3Expression;

import java.util.List;
import java.util.Stack;

public class FloatToken implements Token{
  private String value;

  public FloatToken(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  @Override
  public void updateZ3ExpressionTree(Stack<Z3Expression> z3ExpressionStack, TranslationContext translationContext, String expressionName, List<Z3Expression> finalExpressions) {
    z3ExpressionStack.push(new FloatZ3Expression(Float.parseFloat(value)));
  }
}
