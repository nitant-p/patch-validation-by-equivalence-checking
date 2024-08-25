package sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;

public interface ConditionZ3Expression extends Z3Expression {
    @Override
    BoolExpr interpret(TranslationContext translationContext, Context ctx);


//    ConditionZ3Expression getCondition(TranslationContext translationContext, String expressionName);

    Z3Expression getLeft();

    Z3Expression getRight();

    String returnSymbol();

}
