package sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals;

import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.IntZ3Expression;

public class BooleanLiteralFactory {

    public static ConditionZ3Expression getBooleanLiteral(IntZ3Expression intExpression) {
        // Assume that any non-zero integer is true, and zero is false
        if (intExpression.getValue() != 0) {
            return new TrueConditionZ3Expression(intExpression.getValue());
        } else {
            return new FalseConditionZ3Expression(intExpression.getValue());
        }
    }


}