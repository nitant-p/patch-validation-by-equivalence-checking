package sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals;

import com.microsoft.z3.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals.ConditionZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.BooleanLiteralZ3Expression;

public class IteZ3Expression implements Z3Expression {
  private ConditionZ3Expression incompleteCondition = null;
  private BooleanLiteralZ3Expression incompleteBooleanLiteralCondition = null;
  private Z3Expression trueBranch;
  private Z3Expression falseBranch;

  public IteZ3Expression(ConditionZ3Expression incompleteCondition, Z3Expression trueBranch,
                         Z3Expression falseBranch) {
    this.incompleteCondition = incompleteCondition;
    this.trueBranch = trueBranch;
    this.falseBranch = falseBranch;
  }

  public IteZ3Expression(BooleanLiteralZ3Expression incompleteCondition, Z3Expression trueBranch,
                         Z3Expression falseBranch) {
    this.incompleteBooleanLiteralCondition = incompleteCondition;
    this.trueBranch = trueBranch;
    this.falseBranch = falseBranch;
  }

  @Override
  public Expr interpret(TranslationContext translationContext, Context ctx) {
    Expr incompleteConditionExpr;
    if (incompleteCondition == null) {
      incompleteConditionExpr = this.incompleteBooleanLiteralCondition
              .getBooleanLiteral(translationContext, ctx)
              .interpret(translationContext, ctx);
    } else {
      incompleteConditionExpr = this.incompleteCondition.interpret(translationContext, ctx);
    }
    Expr trueBranch = this.trueBranch.interpret(translationContext, ctx);
    Expr falseBranch = this.falseBranch.interpret(translationContext, ctx);
    return ctx.mkITE(incompleteConditionExpr, trueBranch, falseBranch);
  }

  @Override
  public String toString() {
    return String.format("ITE(%s, %s, %s)", this.incompleteCondition, this.trueBranch,
        this.falseBranch);
  }
}
