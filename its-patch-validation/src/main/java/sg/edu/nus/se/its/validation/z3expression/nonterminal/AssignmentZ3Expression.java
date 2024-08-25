package sg.edu.nus.se.its.validation.z3expression.nonterminal;

import com.microsoft.z3.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.StringZ3Expression;

public class AssignmentZ3Expression implements Z3Expression {
  private Z3Expression name;
  private Z3Expression assignment;

  public AssignmentZ3Expression(Z3Expression name, Z3Expression assignment) {
    this.name = name;
    this.assignment = assignment;
  }

  @Override
  public Expr interpret(TranslationContext translationContext, Context ctx) {
    System.out.println("name: " +this.name + "assignment: " +this.assignment);
    //try to make it return a string variable z3 expression name/asssign should be same class
    return ctx.mkEq(this.name.interpret(translationContext, ctx), this.assignment.interpret(translationContext, ctx));
  }

  @Override
  public String toString() {
    return String.format("Assignment(%s, %s)", name, assignment);
  }

  public Z3Expression getName() {
    return this.name;
  }

  public Z3Expression getAssignment() {
    return this.assignment;
  }
}
