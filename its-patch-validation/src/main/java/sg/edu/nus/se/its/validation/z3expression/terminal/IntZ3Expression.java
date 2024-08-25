package sg.edu.nus.se.its.validation.z3expression.terminal;

import com.microsoft.z3.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals.ConditionZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals.FalseConditionZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals.TrueConditionZ3Expression;

public class IntZ3Expression implements Z3Expression, BooleanLiteralZ3Expression {
  private int value;

  public IntZ3Expression(int value) {
    this.value = value;
  }

  @Override
  public Expr interpret(TranslationContext translationContext, Context ctx) {
    return ctx.mkInt(value);
  }

  @Override
  public String toString() {
    return String.format("Int(%s)", this.value);
  }

  public int getValue() {
    return this.value;
  }

  @Override
  public Z3Expression getBooleanLiteral(TranslationContext translationContext, Context ctx) {
    if (this.value != 0) {
      return new TrueConditionZ3Expression(value);
    } else {
      return new FalseConditionZ3Expression(value);
    }
  }
}
