package sg.edu.nus.se.its.validation.z3expression.terminal;

import com.microsoft.z3.Context;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals.ConditionZ3Expression;

public interface BooleanLiteralZ3Expression extends Z3Expression {
    Z3Expression getBooleanLiteral(TranslationContext translationContext, Context ctx);
}
