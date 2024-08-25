package sg.edu.nus.se.its.validation.tokenizer.token.array;

import sg.edu.nus.se.its.validation.tokenizer.token.Token;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.ArrayAssignZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.ArrayOperationZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.ArrayVariableZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.IntZ3Expression;

import java.util.List;
import java.util.Stack;

public class ArrayAssignToken implements Token {
    @Override
    public String getValue() {
        return "ArrayAssign";
    }

    @Override
    public void updateZ3ExpressionTree(Stack<Z3Expression> z3ExpressionStack, TranslationContext translationContext, String expressionName, List<Z3Expression> finalExpressions) {
        Z3Expression a = z3ExpressionStack.pop();
        ArrayVariableZ3Expression array;
        if (a instanceof ArrayVariableZ3Expression)
            array = (ArrayVariableZ3Expression) a;
        else array = ((ArrayOperationZ3Expression) a).getArray();
        IntZ3Expression arrayIndex = (IntZ3Expression) z3ExpressionStack.pop();
        Z3Expression arrayElement = z3ExpressionStack.pop();
        ArrayAssignZ3Expression toAdd = new ArrayAssignZ3Expression(array, arrayIndex, arrayElement);
        z3ExpressionStack.push(toAdd);
        finalExpressions.add(toAdd);
    }
}
