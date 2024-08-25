package sg.edu.nus.se.its.validation.tokenizer.token;

import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.StringVariableZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.StringZ3Expression;

import java.util.List;
import java.util.Stack;

public class OutputToken implements Token {

    private final String value;

    public OutputToken() {
        this.value = "$out";
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public void updateZ3ExpressionTree(Stack<Z3Expression> z3ExpressionStack, TranslationContext translationContext, String expressionName, List<Z3Expression> finalExpressions) {
        z3ExpressionStack.push(new StringVariableZ3Expression("$out"));
    }
}
