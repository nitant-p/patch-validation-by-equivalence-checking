package sg.edu.nus.se.its.validation.tokenizer;

import sg.edu.nus.se.its.validation.tokenizer.token.StringToken;
import sg.edu.nus.se.its.validation.tokenizer.token.Token;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.utils.Utils;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.SpreadZ3Expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SpreadToken implements Token {

    private List<Token> spread = new ArrayList<>();

    public SpreadToken(String s) {
        if (s.charAt(2) == '"') {
            for (char c : s.substring(3, s.length() - 1).toCharArray())
                spread.add(new StringToken(String.valueOf(c)));
        } else spread = Tokenizer.tokenize(s);
    }

    @Override
    public String getValue() {
        return spread.toString();
    }

    @Override
    public void updateZ3ExpressionTree(Stack<Z3Expression> z3ExpressionStack, TranslationContext translationContext, String expressionName, List<Z3Expression> finalExpressions) {
        SpreadZ3Expression spreadZ3Expression = new SpreadZ3Expression();
        for (Token token : spread) {
            token.updateZ3ExpressionTree(z3ExpressionStack, translationContext, expressionName, finalExpressions);
            spreadZ3Expression.add(z3ExpressionStack.pop());
        }
        spreadZ3Expression.finishedAdding();
        z3ExpressionStack.push(spreadZ3Expression);
    }
}
