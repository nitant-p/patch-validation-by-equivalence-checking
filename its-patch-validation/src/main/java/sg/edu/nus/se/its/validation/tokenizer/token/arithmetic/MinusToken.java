package sg.edu.nus.se.its.validation.tokenizer.token.arithmetic;

import sg.edu.nus.se.its.validation.tokenizer.token.Token;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.MinusZ3Expression;

import java.util.List;
import java.util.Stack;

public class MinusToken implements Token {
  private String value;

  public MinusToken() {
    this.value = "-";
  }

  public String getValue() {
    return this.value;
  }

  @Override
  public void updateZ3ExpressionTree(Stack<Z3Expression> z3ExpressionStack, TranslationContext translationContext, String expressionName, List<Z3Expression> finalExpressions) {
    Z3Expression left = null;
    if (z3ExpressionStack.size() <= 1) {
      left = new sg.edu.nus.se.its.validation.z3expression.terminal.IntZ3Expression(0);
    } else {
      left = z3ExpressionStack.pop();
    }
    Z3Expression right = z3ExpressionStack.pop();
    z3ExpressionStack.push(new MinusZ3Expression(left, right));
  }
}
