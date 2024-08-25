package sg.edu.nus.se.its.validation.z3expression.nonterminal;

import com.microsoft.z3.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.IntVariableZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.IntZ3Expression;

public class ArrayAssignZ3Expression implements ArrayOperationZ3Expression {

    private final ArrayVariableZ3Expression array;
    private final IntZ3Expression arrayIndex;
    private final Z3Expression arrayElement;

    public ArrayAssignZ3Expression(ArrayVariableZ3Expression array, IntZ3Expression arrayIndex, Z3Expression arrayElement) {
        this.array = array;
        this.arrayIndex = arrayIndex;
        this.arrayElement = arrayElement;
    }

    @Override
    public BoolExpr interpret(TranslationContext translationContext, Context ctx) {
        Expr<ArraySort<IntSort, Sort>> arrayExpr = array.interpret(translationContext, ctx);
        Expr<IntSort> indexExpr = arrayIndex.interpret(translationContext, ctx);
        Expr elementExpr = arrayElement.interpret(translationContext, ctx);
        return ctx.mkEq(ctx.mkSelect(arrayExpr, indexExpr), elementExpr);
    }

    @Override
    public ArrayVariableZ3Expression getArray() {
        return array;
    }

    @Override
    public String toString() {
        return String.format("%s[%s] = %s", array.toString(), arrayIndex, arrayElement);
    }
}
