package sg.edu.nus.se.its.validation.z3expression.terminal;

import com.microsoft.z3.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals.ConditionZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals.FalseConditionZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals.TrueConditionZ3Expression;

public class StringZ3Expression implements Z3Expression, BooleanLiteralZ3Expression {
  private String value;

  public StringZ3Expression(String value) {
    this.value = value;
  }

  public String getValue() {
    return value.substring(1, value.length() - 1);
  }

  @Override
  public Expr interpret(TranslationContext translationContext, Context ctx) {
    return ctx.mkString(this.value);
  }

  @Override
  public String toString() {
    return String.format("String(%s)", this.value);
  }


  @Override
  public ConditionZ3Expression getBooleanLiteral(TranslationContext translationContext, Context ctx) {
    // In C, a string is false if it is the null pointer, so we check for null rather than empty
    if (this.value.isEmpty()) {
      return new FalseConditionZ3Expression(value);
    } else {
      return new TrueConditionZ3Expression(value);
    }
  }
}
