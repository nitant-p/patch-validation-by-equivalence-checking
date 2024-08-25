package sg.edu.nus.se.its.validation.z3expression.nonterminal;

import com.microsoft.z3.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import java.util.ArrayList;

public class WhileLoopConditionZ3Expression implements Z3Expression {
    private Z3Expression left;
    private Z3Expression right;

    public WhileLoopConditionZ3Expression(Z3Expression left, Z3Expression right) {
        this.left = left;
        this.right = right;
    }

    public Expr interpret(TranslationContext translationContext, Context ctx) {
        return ctx.mkLt(this.left.interpret(translationContext, ctx), this.right.interpret(translationContext, ctx));
    }
}
