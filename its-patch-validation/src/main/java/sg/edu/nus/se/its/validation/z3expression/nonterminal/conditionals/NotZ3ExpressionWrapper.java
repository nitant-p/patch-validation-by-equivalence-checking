package sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.BooleanLiteralZ3Expression;

public class NotZ3ExpressionWrapper implements ConditionZ3Expression{
    private Z3Expression expr;
    public NotZ3ExpressionWrapper(Z3Expression condition) {
        this.expr = condition;
    }

    @Override
    public BoolExpr interpret(TranslationContext translationContext, Context ctx) {
        if (this.expr instanceof BooleanLiteralZ3Expression) {
            this.expr = ((BooleanLiteralZ3Expression) this.expr).getBooleanLiteral(translationContext, ctx);
        }
        Expr condition = this.expr.interpret(translationContext, ctx);
        return ctx.mkNot(condition);
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
        return "!";
    }

    @Override
    public String toString() {
        return String.format("Not Condition: !(%s)", this.expr);
    }

}
