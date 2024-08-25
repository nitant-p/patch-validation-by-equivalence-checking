package sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals;

import com.microsoft.z3.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;

public class TrueBranchZ3Expression implements Z3Expression {
    private Z3Expression left;
    private Z3Expression right;

    public TrueBranchZ3Expression(Z3Expression left, Z3Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Expr interpret(TranslationContext translationContext, Context ctx) {
        Expr leftExpr = this.left.interpret(translationContext, ctx);
        Expr rightExpr = this.right.interpret(translationContext, ctx);
        return ctx.mkEq(leftExpr, rightExpr);
    }

    @Override
    public String toString() {
        return String.format("True Branch:(%s = %s)", this.left, this.right);
    }


}
