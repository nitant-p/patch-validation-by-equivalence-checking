package sg.edu.nus.se.its.validation.z3expression.terminal;

import com.microsoft.z3.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.Z3ExpressionVariable;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.conditionals.*;

public class IntVariableZ3Expression implements Z3Expression, BooleanLiteralZ3Expression, Z3ExpressionVariable{
  private String name;

  public IntVariableZ3Expression(String name) {
    this.name = name;
  }

  @Override
  public IntExpr interpret(TranslationContext translationContext, Context ctx) {
    if ((!(name.startsWith("REF_") || name.startsWith("SUB_"))) &&
        translationContext.getHasScanf()) {
      name = translationContext.getIsReference() ? "REF_" + name : "SUB_" + name;
    }
    IntExpr intConst = ctx.mkIntConst(name);
    if (name.contains("$ret")) {
      translationContext.setReturn(intConst);
    }
    return intConst;
  }

  @Override
  public String toString() {
    return String.format("IntVariable(%s)", this.name);
  }

  public String getName() {
    return this.name;
  }

  public Z3Expression getBooleanLiteral(TranslationContext translationContext, Context ctx) {
    // create a new condition that checks if the value is 0 - false boolean literal is returned
    Z3Expression left = this;
    Z3Expression right = new IntZ3Expression(0);
    ConditionZ3Expression condition =  new EqualityZ3Expression(left, right);
    return new IteZ3Expression(condition, new FalseZ3Expression(), new TrueZ3Expression());
  }

}
