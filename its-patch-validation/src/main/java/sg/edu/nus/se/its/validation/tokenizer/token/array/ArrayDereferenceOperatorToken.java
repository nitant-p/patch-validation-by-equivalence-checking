package sg.edu.nus.se.its.validation.tokenizer.token.array;

import sg.edu.nus.se.its.validation.tokenizer.token.Token;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.ArrayAssignZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.ArrayDereferenceZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.ArrayVariableZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.ArrayZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.IntZ3Expression;

import java.util.List;
import java.util.Stack;

public class ArrayDereferenceOperatorToken implements Token {

    @Override
    public String getValue() {
        return "[]";
    }

    @Override
    public void updateZ3ExpressionTree(Stack<Z3Expression> z3ExpressionStack, TranslationContext translationContext, String expressionName, List<Z3Expression> finalExpressions) {
        Z3Expression e = z3ExpressionStack.pop();
        ArrayVariableZ3Expression array;
        if (e instanceof ArrayVariableZ3Expression)
            array = (ArrayVariableZ3Expression) e;
        else array = ((ArrayAssignZ3Expression) e).getArray();
        IntZ3Expression arrayIndex = (IntZ3Expression) z3ExpressionStack.pop();
        z3ExpressionStack.push(new ArrayDereferenceZ3Expression(array, arrayIndex));
    }
}
