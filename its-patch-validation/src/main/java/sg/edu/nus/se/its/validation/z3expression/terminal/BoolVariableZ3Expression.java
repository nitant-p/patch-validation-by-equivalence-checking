package sg.edu.nus.se.its.validation.z3expression.terminal;

import com.microsoft.z3.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.Z3ExpressionVariable;

public class BoolVariableZ3Expression implements Z3Expression, Z3ExpressionVariable {
  private String name;

  public BoolVariableZ3Expression(String name) {
    this.name = name;
  }

  @Override
  public Expr interpret(TranslationContext translationContext, Context ctx) {
    return ctx.mkBoolConst(name);
  }

  @Override
  public String toString() {
    return String.format("BoolVariable(%s)", this.name);
  }

  public String getName() {
    return this.name;
  }
}
