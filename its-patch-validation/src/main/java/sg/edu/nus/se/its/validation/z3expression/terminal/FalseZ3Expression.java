package sg.edu.nus.se.its.validation.z3expression.terminal;

import com.microsoft.z3.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;

public class FalseZ3Expression implements Z3Expression {
  @Override
  public Expr interpret(TranslationContext translationContext, Context ctx) {
    return ctx.mkFalse();
  }

  @Override
  public String toString() {
    return "false";
  }
}
