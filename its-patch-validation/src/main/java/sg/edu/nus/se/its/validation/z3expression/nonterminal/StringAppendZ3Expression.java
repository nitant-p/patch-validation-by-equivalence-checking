package sg.edu.nus.se.its.validation.z3expression.nonterminal;

import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;

public class StringAppendZ3Expression implements Z3Expression {
    private Z3Expression output;
    private Z3Expression stringFormat;

    public StringAppendZ3Expression(Z3Expression output, Z3Expression stringFormat) {
        this.output = output;
        this.stringFormat = stringFormat;
    }

    @Override
    public Expr interpret(TranslationContext translationContext, Context ctx) {
        Expr leftExpr = this.output.interpret(translationContext, ctx);
        Expr rightExpr = this.stringFormat.interpret(translationContext, ctx);
        ctx.mkEq(leftExpr, rightExpr);
        return ctx.mkString(rightExpr.toString());
        //return ctx.mkConcat(ctx.mkString(leftExpr.toString()), ctx.mkString(rightExpr.toString()));
        //return ctx.mkString("HI");
    }

    @Override
    public String toString() {
        return String.format("StringAppend(%s, %s)", this.output, this.stringFormat);
    }
}
