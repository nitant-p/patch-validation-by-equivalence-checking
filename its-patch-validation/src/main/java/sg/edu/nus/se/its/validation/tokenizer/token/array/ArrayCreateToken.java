package sg.edu.nus.se.its.validation.tokenizer.token.array;

import sg.edu.nus.se.its.validation.tokenizer.token.Token;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.ArrayCreateZ3Expression;

import java.util.List;
import java.util.Stack;

public class ArrayCreateToken implements Token {
    @Override
    public String getValue() {
        return "ArrayCreate";
    }

    @Override
    public void updateZ3ExpressionTree(Stack<Z3Expression> z3ExpressionStack, TranslationContext translationContext, String expressionName, List<Z3Expression> finalExpressions) {
        z3ExpressionStack.push(new ArrayCreateZ3Expression(expressionName));
    }
}
