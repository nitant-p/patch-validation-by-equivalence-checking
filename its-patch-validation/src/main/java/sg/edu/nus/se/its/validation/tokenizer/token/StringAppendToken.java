package sg.edu.nus.se.its.validation.tokenizer.token;

import sg.edu.nus.se.its.validation.tokenizer.token.Token;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.StringAppendZ3Expression;

import java.util.List;
import java.util.Stack;

public class StringAppendToken implements Token {
    private String value;

    public StringAppendToken() {
        this.value = "StrAppend";
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public void updateZ3ExpressionTree(Stack<Z3Expression> z3ExpressionStack, TranslationContext translationContext, String expressionName, List<Z3Expression> finalExpressions) {
        Z3Expression output = z3ExpressionStack.pop();
        Z3Expression formatString = z3ExpressionStack.pop();
        z3ExpressionStack.push(new StringAppendZ3Expression(output, formatString));
    }
}
