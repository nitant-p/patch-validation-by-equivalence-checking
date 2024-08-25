package sg.edu.nus.se.its.validation.z3expression.terminal;

import com.microsoft.z3.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;

/**
 * Class representing a scanf z3expression.
 */
public class ScanfZ3Expression implements Z3Expression {
  /**
   * The index of the scanf input.
   */
  private int inputNum;

  /**
   * Constructor for this object.
   * @param inputNum The index of the scanf input.
   */
  public ScanfZ3Expression(int inputNum) {
    this.inputNum = inputNum;
  }

  /**
   * The interpret method for this class.
   * @param translationContext The translation context.
   * @param ctx The z3 context.
   * @return Returns the Expr corresponding to the scanf input denoted by this class.
   */
  @Override
  public Expr interpret(TranslationContext translationContext, Context ctx) {
    Expr variableExpr = translationContext.getScanfInputExpr(inputNum);
    return variableExpr;
  }

  /**
   * Converts this object to a string format.
   * @return The string.
   */
  @Override
  public String toString() {
    return String.format("ScanfVariable(input no." + Integer.toString(inputNum) + ")");
  }
}
