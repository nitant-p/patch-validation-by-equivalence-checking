package sg.edu.nus.se.its.validation.tokenizer.token.arithmetic;

import sg.edu.nus.se.its.validation.tokenizer.token.Token;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.DivisionZ3Expression;

import java.util.List;
import java.util.Stack;

public class DivisionToken implements Token {

  public String getValue() {
    return "/";
  }

  @Override
  public void updateZ3ExpressionTree(Stack<Z3Expression> z3ExpressionStack, TranslationContext translationContext, String expressionName, List<Z3Expression> finalExpressions) {
    Z3Expression left = z3ExpressionStack.pop();
    Z3Expression right = z3ExpressionStack.pop();
    z3ExpressionStack.push(new DivisionZ3Expression(left, right));
  }
}
