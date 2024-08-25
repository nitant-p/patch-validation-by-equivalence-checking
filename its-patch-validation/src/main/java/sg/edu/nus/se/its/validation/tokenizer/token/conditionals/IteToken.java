package sg.edu.nus.se.its.validation.tokenizer.token.conditionals;

import sg.edu.nus.se.its.validation.tokenizer.token.Token;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals.*;
import sg.edu.nus.se.its.validation.z3expression.terminal.BooleanLiteralZ3Expression;

import java.util.List;
import java.util.Stack;

public class IteToken implements Token {
  public String getValue() {
    return "ite";
  }

  @Override
  public void updateZ3ExpressionTree(Stack<Z3Expression> z3ExpressionStack, TranslationContext translationContext, String expressionName, List<Z3Expression> finalExpressions) {
    Z3Expression variable = translationContext.getVariableZ3Expression(expressionName);
    // condition is expected to be popped first
    Z3Expression condition = z3ExpressionStack.pop();
    // check for boolean literal
    Z3Expression trueBranchValue = z3ExpressionStack.pop();
    Z3Expression trueBranchExpr = new TrueBranchZ3Expression(variable, trueBranchValue);
    Z3Expression falseBranchValue = z3ExpressionStack.pop();
    Z3Expression falseBranchExpr = new FalseBranchZ3Expression(variable, falseBranchValue);

    IteZ3Expression completeCondition;
    if (condition instanceof BooleanLiteralZ3Expression) {
      completeCondition = new IteZ3Expression((BooleanLiteralZ3Expression) condition, trueBranchExpr, falseBranchExpr);
    } else {
      completeCondition = new IteZ3Expression((ConditionZ3Expression) condition, trueBranchExpr, falseBranchExpr);
    }
    z3ExpressionStack.push(completeCondition);
  }
}
