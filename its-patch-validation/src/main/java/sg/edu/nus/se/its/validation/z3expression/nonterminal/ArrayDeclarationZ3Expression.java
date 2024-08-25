package sg.edu.nus.se.its.validation.z3expression.nonterminal;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.terminal.StringZ3Expression;

public class ArrayDeclarationZ3Expression implements ArrayOperationZ3Expression {

    private final ArrayVariableZ3Expression array;
    private final StringZ3Expression value;

    public ArrayDeclarationZ3Expression(ArrayVariableZ3Expression array, StringZ3Expression value) {
        this.array = array;
        this.value = value;
    }

    @Override
    public BoolExpr interpret(TranslationContext translationContext, Context ctx) {
        Expr valueStringExpr = value.interpret(translationContext, ctx);
        Expr arrayExpr = array.interpret(translationContext, ctx);
        return ctx.mkEq(ctx.mkSelect(arrayExpr, ctx.mkInt(0)), valueStringExpr);
    }

    @Override
    public ArrayVariableZ3Expression getArray() {
        return null;
    }
}
