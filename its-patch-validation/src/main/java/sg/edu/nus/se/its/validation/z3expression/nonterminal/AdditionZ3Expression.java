package sg.edu.nus.se.its.validation.z3expression.nonterminal;

import com.microsoft.z3.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;

public class AdditionZ3Expression implements Z3Expression {
  public Z3Expression left;
  public Z3Expression right;

  public AdditionZ3Expression(Z3Expression left, Z3Expression right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public Expr interpret(TranslationContext translationContext, Context ctx) {
    Expr leftExpr = this.left.interpret(translationContext, ctx);
    Expr rightExpr = this.right.interpret(translationContext, ctx);
    return ctx.mkAdd(leftExpr, rightExpr);
  }

  @Override
  public String toString() {
    return String.format("Addition(%s, %s)", this.left, this.right);
  }
}
