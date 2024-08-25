package sg.edu.nus.se.its.validation.z3expression.terminal;

import com.microsoft.z3.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals.*;

public class StringVariableZ3Expression implements Z3Expression, BooleanLiteralZ3Expression {

    private final String name;

    public StringVariableZ3Expression(String name) {
        this.name = name;
    }

    @Override
    public Expr interpret(TranslationContext translationContext, Context ctx) {
        return ctx.mkConst(name, ctx.getStringSort());
    }

    public String getName() {
        return this.name;
    }

    @Override
    public Z3Expression getBooleanLiteral(TranslationContext translationContext, Context ctx) {
        // empty string considered false
        Z3Expression left = this;
        Z3Expression right = new StringZ3Expression("");
        ConditionZ3Expression condition =  new EqualityZ3Expression(left, right);
        return new IteZ3Expression(condition, new FalseZ3Expression(), new TrueZ3Expression());
    }

}
