package sg.edu.nus.se.its.validation.z3expression.nonterminal;

import com.google.ortools.constraintsolver.Assignment;
import com.microsoft.z3.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals.ConditionZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.IntVariableZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.IntZ3Expression;

import java.util.ArrayList;
import java.util.HashMap;

public class ForLoopZ3Expression implements Z3Expression {
    private String loopName;
    private Expr returnVariableName;

    public ForLoopZ3Expression(String loopName) {
        this.loopName = loopName;
    }

    public void setReturnVariableName(Expr returnVariableName) {
        this.returnVariableName = returnVariableName;
    }

    public Expr interpret(TranslationContext translationContext, Context ctx) {
        return null;
    }

    public ArrayList<Expr> interpretLoop(TranslationContext translationContext, Context ctx) {
        ArrayList<Z3Expression> forLoopBodyExpressions = translationContext.getForLoopBody(this.loopName);
        ArrayList<Expr> forLoopBodyArray = new ArrayList<>();
        ArrayList<Z3Expression> forLoopCondition = translationContext.getForLoopCondition(this.loopName);
        Z3Expression forLoopUpdate = translationContext.getForLoopUpdate(this.loopName);
        AssignmentZ3Expression cond = (AssignmentZ3Expression) forLoopCondition.get(0);
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
                IntVariableZ3Expression loopUpdateVariable = (IntVariableZ3Expression) translationContext.getVariableZ3Expression(loopVariableName);
            }

            // Left is Constant, Right is Variable
            if (left instanceof  IntZ3Expression && right instanceof IntVariableZ3Expression) {
                loopVariableName = ((IntVariableZ3Expression) right).getName();
            }

            // Both are Constants
            if (left instanceof IntZ3Expression && right instanceof IntZ3Expression) {
                System.out.println("Error: For loop condition not supported");
            }
        }  else {
            System.out.println("Error: For loop condition not supported");
        }

        HashMap<String, Z3Expression> variableMap = translationContext.getVariableMap();
        ArrayList<Expr> finalExprs = new ArrayList<>();
        // Handle outside body stuff
        // Get the variable from within the body, check if it is initialized
        // Add the variable to

        // Handle inside body stuff
        int loopFrom = translationContext.getVariableInit(loopVariableName);
        AdditionZ3Expression ass = (AdditionZ3Expression) ((AssignmentZ3Expression) forLoopBodyExpressions.get(0)).getAssignment();
        IntVariableZ3Expression curr = (IntVariableZ3Expression) variableMap.get(loopVariableName);

        IntVariableZ3Expression curr2 = null;

        if (this.returnVariableName != null) {
            curr2 = ((IntVariableZ3Expression) variableMap.get(this.returnVariableName.toString()));
        }

        for (int i = loopFrom; i < loopUntil; i++) {
            variableMap.put(loopVariableName + "$" + i, new IntVariableZ3Expression(loopVariableName + "$" + i));
            IntVariableZ3Expression v = (IntVariableZ3Expression) variableMap.get(loopVariableName + "$" + i);
            finalExprs.add(new AssignmentZ3Expression(v, new AdditionZ3Expression(curr, ass.right)).interpret(translationContext, ctx));

            curr = v;

            if (this.returnVariableName != null) {
                variableMap.put(this.returnVariableName + "$" + i, new IntVariableZ3Expression(this.returnVariableName + "$" + i));
                IntVariableZ3Expression s = (IntVariableZ3Expression) variableMap.get(this.returnVariableName + "$" + i);
                finalExprs.add(new AssignmentZ3Expression(s, new AdditionZ3Expression(curr2, ass.right)).interpret(translationContext, ctx));
                curr2 = s;
                curr = s;
            }

        }

        finalExprs.add(new AssignmentZ3Expression(variableMap.get("$ret"), curr).interpret(translationContext, ctx));

        return finalExprs;
    }
}
