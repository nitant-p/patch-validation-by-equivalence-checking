package sg.edu.nus.se.its.validation.tokenizer.token;

import sg.edu.nus.se.its.validation.tokenizer.token.array.ArrayAssignToken;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.ArrayOperationZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.ArrayVariableZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.SpreadZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.IntZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.StringZ3Expression;

import java.util.List;
import java.util.Stack;

public class ArrayDeclarationToken implements Token {
    @Override
    public String getValue() {
        return "{}";
    }

    @Override
    public void updateZ3ExpressionTree(Stack<Z3Expression> z3ExpressionStack, TranslationContext translationContext, String expressionName, List<Z3Expression> finalExpressions) {
        ArrayOperationZ3Expression array = (ArrayOperationZ3Expression) z3ExpressionStack.pop();
        SpreadZ3Expression spread = (SpreadZ3Expression) z3ExpressionStack.pop();
        for (int i = 0; i < spread.size(); i ++) {
            IntegerToken indexToken = new IntegerToken(String.valueOf(i));
            ArrayAssignToken assignToken = new ArrayAssignToken();
            // pop array pop index pop element
            z3ExpressionStack.push(spread.get());
            indexToken.updateZ3ExpressionTree(z3ExpressionStack, translationContext, expressionName, finalExpressions);
            z3ExpressionStack.push(array);
            assignToken.updateZ3ExpressionTree(z3ExpressionStack, translationContext, expressionName, finalExpressions);
            z3ExpressionStack.pop();
        }
        z3ExpressionStack.add(array.getArray());
    }
}
