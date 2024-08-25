package sg.edu.nus.se.its.validation.z3expression.nonterminal;

import com.microsoft.z3.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals.ConditionZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.IntVariableZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.IntZ3Expression;

import java.util.ArrayList;
import java.util.HashMap;

public class WhileLoopZ3Expression implements Z3Expression {
    private String loopName;

    public WhileLoopZ3Expression(String loopName) {
        this.loopName = loopName;
    }

    public Expr interpret(TranslationContext translationContext, Context ctx) {
        return null;
    }

    public ArrayList<Expr> interpretLoop(TranslationContext translationContext, Context ctx) {
        ArrayList<Z3Expression> whileLoopBodyExpressions = translationContext.getWhileLoopBody(this.loopName);
        ArrayList<Expr> whileLoopBodyArray = new ArrayList<>();
        ArrayList<Z3Expression> whileLoopCondition = translationContext.getWhileLoopCondition(this.loopName);
        AssignmentZ3Expression cond = (AssignmentZ3Expression) whileLoopCondition.get(0);
        Z3Expression assignment = cond.getAssignment();

        String loopVariableName = "";
        int loopVariableValue;
        Z3Expression left;
        Z3Expression right;
        int loopUntil = 0;

        if (assignment instanceof ConditionZ3Expression) {
            ConditionZ3Expression condition = (ConditionZ3Expression) assignment;
            left = condition.getLeft();
            right = condition.getRight();
            // Both are Variables
            if (left instanceof IntVariableZ3Expression && right instanceof IntVariableZ3Expression) {
                loopVariableName = ((IntVariableZ3Expression) left).getName();
                translationContext.getVariableZ3Expression(loopVariableName);
            }
            // Left is Variable, Right is Constant

            if (left instanceof IntVariableZ3Expression && right instanceof IntZ3Expression) {
                loopVariableName = ((IntVariableZ3Expression) left).getName();
                loopUntil = ((IntZ3Expression) right).getValue();
                IntVariableZ3Expression assignmentZ3Expression = (IntVariableZ3Expression) translationContext.getVariableZ3Expression(loopVariableName);

            }

            // Left is Constant, Right is Variable
            if (left instanceof  IntZ3Expression && right instanceof IntVariableZ3Expression) {
                loopVariableName = ((IntVariableZ3Expression) right).getName();
            }

            // Both are Constants
            if (left instanceof IntZ3Expression && right instanceof IntZ3Expression) {
                System.out.println("Error: While loop condition not supported");
            }
        }  else {
            System.out.println("Error: While loop condition not supported");
        }

        HashMap<String, Z3Expression> variableMap = translationContext.getVariableMap();
        int loopFrom = translationContext.getVariableInit(loopVariableName);
        ArrayList<Expr> finalExprs = new ArrayList<>();
        AdditionZ3Expression ass = (AdditionZ3Expression) ((AssignmentZ3Expression) whileLoopBodyExpressions.get(0)).getAssignment();
        IntVariableZ3Expression curr = (IntVariableZ3Expression) variableMap.get(loopVariableName);
        for (int i = loopFrom; i < loopUntil; i++) {
            variableMap.put(loopVariableName + "$" + i, new IntVariableZ3Expression(loopVariableName + "$" + i));
            IntVariableZ3Expression v = (IntVariableZ3Expression) variableMap.get(loopVariableName + "$" + i);
            finalExprs.add(new AssignmentZ3Expression(v, new AdditionZ3Expression(curr, ass.right)).interpret(translationContext, ctx));
            curr = v;
        }

        finalExprs.add(new AssignmentZ3Expression(variableMap.get("$ret"), curr).interpret(translationContext, ctx));



        // Can we get variable at this point? No we cannot get the value of it here...

//        int looped = 0;
//        while (looped < 10) {
//            looped++;
//            int index = 0;
//            for (Z3Expression expression: whileLoopBodyExpressions) {
//                Expr expr = expression.interpret(translationContext, ctx);
//                whileLoopBodyArray[index] = expr;
//                index++;
//            }
//        }



        return finalExprs;
    }
}
