package sg.edu.nus.se.its.validation.z3expression.terminal;

import com.microsoft.z3.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals.ConditionZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals.FalseConditionZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals.TrueConditionZ3Expression;

public class FloatZ3Expression implements Z3Expression, BooleanLiteralZ3Expression {
  private float value;

  public FloatZ3Expression(float value) {
    this.value = value;
  }

  @Override
  public Expr interpret(TranslationContext translationContext, Context ctx) {
    return ctx.mkReal(Float.toString(value));
  }

  @Override
  public String toString() {
    return String.format("Float(%s)", this.value);
  }

  @Override
  public ConditionZ3Expression getBooleanLiteral(TranslationContext translationContext, Context ctx) {
    if ((int) this.value != 0) {
      return new TrueConditionZ3Expression(value);
    } else {
      return new FalseConditionZ3Expression(value);
    }
  }


}
