package sg.edu.nus.se.its.validation.z3expression.nonterminal;

import com.microsoft.z3.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;

public interface ArrayOperationZ3Expression extends ArrayZ3Expression {
    @Override
    BoolExpr interpret(TranslationContext translationContext, Context ctx);
    ArrayVariableZ3Expression getArray();
}
