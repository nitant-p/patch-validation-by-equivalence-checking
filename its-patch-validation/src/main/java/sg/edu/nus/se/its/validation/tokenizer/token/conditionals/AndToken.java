package sg.edu.nus.se.its.validation.tokenizer.token.conditionals;

import com.microsoft.z3.IntNum;
import sg.edu.nus.se.its.validation.tokenizer.token.Token;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals.AndConditionZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals.BooleanLiteralFactory;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals.ConditionZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.IntZ3Expression;

import java.util.List;
import java.util.Stack;

public class AndToken implements Token {
    private String value;

    public AndToken() {
        this.value = "&&";
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public void updateZ3ExpressionTree(Stack<Z3Expression> z3ExpressionStack, TranslationContext translationContext, String expressionName, List<Z3Expression> finalExpressions) {
        Z3Expression left = z3ExpressionStack.pop();
        Z3Expression right = z3ExpressionStack.pop();
        z3ExpressionStack.push(new AndConditionZ3Expression(left, right));
    }
}