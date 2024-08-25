package sg.edu.nus.se.its.validation.z3expression.nonterminal;

import com.microsoft.z3.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;

public class ArrayCreateZ3Expression implements ArrayOperationZ3Expression {

    private final ArrayVariableZ3Expression array;
    private final String arrayName;

    public ArrayCreateZ3Expression(String arrayName) {
        this.arrayName = arrayName;
        this.array = new ArrayVariableZ3Expression(arrayName);
    }

    @Override
    public BoolExpr interpret(TranslationContext translationContext, Context ctx) {
        return null;
    }

    @Override
    public ArrayVariableZ3Expression getArray() {
        return array;
    }

    @Override
    public String toString() {
        return arrayName + "[]";
    }
}
