package sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals;

import com.microsoft.z3.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;

public class LessThanZ3Expression implements ConditionZ3Expression {
    private Z3Expression left;
    private Z3Expression right;

    public LessThanZ3Expression(Z3Expression left, Z3Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public BoolExpr interpret(TranslationContext translationContext, Context ctx) {
        Expr leftExpr = this.left.interpret(translationContext, ctx);
        Expr rightExpr = this.right.interpret(translationContext, ctx);
        return ctx.mkLt(leftExpr, rightExpr);
    }

    @Override
    public String toString() {
        return String.format("Less Than Condition: (%s < %s)", this.left, this.right);
    }

    public Z3Expression getLeft() {
        return this.left;
    }

    public Z3Expression getRight() {
        return this.right;
    }

    public String returnSymbol() {
        return "<";
    }
}
