package sg.edu.nus.se.its.validation.z3expression.terminal;

import com.microsoft.z3.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals.ConditionZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals.EqualityZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals.IteZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.Z3ExpressionVariable;

public class FloatVariableZ3Expression implements Z3Expression, Z3ExpressionVariable, BooleanLiteralZ3Expression {
  private String name;

  public FloatVariableZ3Expression(String name) {
    this.name = name;
  }

  @Override
  public Expr interpret(TranslationContext translationContext, Context ctx) {
    return ctx.mkRealConst(name);
  }

  @Override
  public String toString() {
    return String.format("FloatVariable(%s)", this.name);
  }

  public String getName() {
    return this.name;
  }

  public Z3Expression getBooleanLiteral(TranslationContext translationContext, Context ctx) {
    // create a new condition that checks if the value is 0 - false boolean literal is returned
    Z3Expression left = this;
    Z3Expression right = new FloatZ3Expression(0);
    ConditionZ3Expression condition =  new EqualityZ3Expression(left, right);
    return new IteZ3Expression(condition, new FalseZ3Expression(), new TrueZ3Expression());
  }

}
