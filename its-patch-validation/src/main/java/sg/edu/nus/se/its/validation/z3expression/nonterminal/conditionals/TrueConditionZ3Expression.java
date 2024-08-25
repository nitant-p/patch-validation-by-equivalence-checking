package sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals;

import com.microsoft.z3.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;

public class TrueConditionZ3Expression implements ConditionZ3Expression {

    private Object literalValue;
    private Z3Expression expression = null;

    public TrueConditionZ3Expression(int literalValue) {
        this.literalValue = literalValue;
    }

    public TrueConditionZ3Expression(float literalValue) {
        this.literalValue = literalValue;
    }

    public TrueConditionZ3Expression(String literalValue) {
        this.literalValue = literalValue;
    }

    public TrueConditionZ3Expression(Z3Expression expression) {
        this.expression = expression;
    }

    @Override
    public BoolExpr interpret(TranslationContext translationContext, Context ctx) {
        return ctx.mkTrue();
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
        return "";
    }

    @Override
    public String toString() {
        if (literalValue instanceof Integer) {
            return String.format("True Condition - integer: %d", literalValue);
        } else if (literalValue instanceof Float) {
            return String.format("True Condition - float: %f", literalValue);
        } else if (literalValue instanceof String) {
            return String.format("True Condition - string: %s", literalValue);
        } else {
            if (expression != null) {
                return String.format("True Condition - variable: %s", expression);
            } else {
                return "True Condition - unknown type";
            }
        }
    }
}
