package sg.edu.nus.se.its.validation.z3expression.nonterminal;

import com.microsoft.z3.CharSort;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.SeqSort;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.IntZ3Expression;

public class ArrayDereferenceZ3Expression implements Z3Expression {

    private final ArrayVariableZ3Expression array;
    private final IntZ3Expression index;

    public ArrayDereferenceZ3Expression(ArrayVariableZ3Expression array, IntZ3Expression index) {
        this.array = array;
        this.index = index;
    }

    @Override
    public Expr interpret(TranslationContext translationContext, Context ctx) {
        return ctx.mkSelect(array.interpret(translationContext, ctx), index.interpret(translationContext, ctx));
    }

    @Override
    public String toString() {
        return String.format("%s[%s]", array, index);
    }
}
