package sg.edu.nus.se.its.validation.z3expression.nonterminal;

import com.microsoft.z3.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.builder.Z3ExpressionTreeBuilderUtils;

public class ArrayVariableZ3Expression implements ArrayZ3Expression {
    private final String arrayName;

    public ArrayVariableZ3Expression(String arrayName) {
        this.arrayName = arrayName;
    }

    @Override
    public Expr<ArraySort<IntSort, Sort>> interpret(TranslationContext translationContext, Context ctx) {
        return ctx.mkArrayConst(arrayName, ctx.getIntSort(), Z3ExpressionTreeBuilderUtils.getSort(arrayName, translationContext, ctx));
    }

    @Override
    public String toString() {
        return "(arrVar)" + arrayName;
    }
}
