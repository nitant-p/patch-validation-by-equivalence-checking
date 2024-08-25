package sg.edu.nus.se.its.validation.tokenizer.token;

import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;

import java.util.List;
import java.util.Stack;

public interface Token {
  String getValue();

  default void updateZ3ExpressionTree(Stack<Z3Expression> z3ExpressionStack, TranslationContext translationContext, String expressionName, List<Z3Expression> finalExpressions) {
    throw new RuntimeException("updateZ3ExpressionTree not implemented for the token (" + this + ")");
  }
}
