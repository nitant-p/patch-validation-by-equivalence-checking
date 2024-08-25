package sg.edu.nus.se.its.validation.tokenizer.token;

import sg.edu.nus.se.its.validation.tokenizer.token.Token;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.StringFormatZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.StringAppendZ3Expression;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StringFormatToken implements Token {
    private String value;

    public StringFormatToken() {
        this.value = "StrFormat";
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public void updateZ3ExpressionTree(Stack<Z3Expression> z3ExpressionStack, TranslationContext translationContext, String expressionName, List<Z3Expression> finalExpressions) {
        Z3Expression formatString = z3ExpressionStack.pop();

        // Pop the arguments from the stack
        ArrayList<Z3Expression> arguments = new ArrayList<>();
        while (!z3ExpressionStack.isEmpty()) {
            arguments.add(z3ExpressionStack.pop());
        }

        z3ExpressionStack.push(new StringFormatZ3Expression(formatString, arguments));
    }
}

