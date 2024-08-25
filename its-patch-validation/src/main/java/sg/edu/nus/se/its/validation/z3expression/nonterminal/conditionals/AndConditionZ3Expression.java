package sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals;

import com.microsoft.z3.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.BooleanLiteralZ3Expression;

public class AndConditionZ3Expression implements ConditionZ3Expression {
    private Z3Expression left;
    private Z3Expression right;

    public AndConditionZ3Expression(Z3Expression left, Z3Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public BoolExpr interpret(TranslationContext translationContext, Context ctx) {
        if (left instanceof BooleanLiteralZ3Expression) {
            this.left = ((BooleanLiteralZ3Expression) left).getBooleanLiteral(translationContext, ctx);
        }
        if (right instanceof BooleanLiteralZ3Expression) {
            this.right = ((BooleanLiteralZ3Expression) right).getBooleanLiteral(translationContext, ctx);
        }
        Expr leftExpr = this.left.interpret(translationContext, ctx);
        Expr rightExpr = this.right.interpret(translationContext, ctx);
        return ctx.mkAnd(leftExpr, rightExpr);
    }

    @Override
    public Z3Expression getLeft() {
        return null;
    }

    @Override
    public Z3Expression getRight() {
        return null;
    }

    @Override
    public String returnSymbol() {
        return "&&";
    }

    @Override
    public String toString() {
        return String.format("And Condition: (%s && %s)", this.left, this.right);
    }


}
