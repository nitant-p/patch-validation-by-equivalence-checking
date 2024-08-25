package sg.edu.nus.se.its.validation.tokenizer.token;

import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.TrueZ3Expression;

import java.util.List;
import java.util.Stack;

public class TrueToken implements Token {

  public String getValue() {
    return "true";
  }

  @Override
  public void updateZ3ExpressionTree(Stack<Z3Expression> z3ExpressionStack, TranslationContext translationContext, String expressionName, List<Z3Expression> finalExpressions) {
    z3ExpressionStack.push(new TrueZ3Expression());
  }
}
