package sg.edu.nus.se.its.validation.tokenizer.token;

import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.ScanfZ3Expression;

import java.lang.RuntimeException;
import java.util.List;
import java.util.Stack;

/**
 * Token corresponding to a ListHead section in an expression.
 */
public class ListHeadToken implements Token {
  String value;

  /**
   * Constructor for the token.
   * @param value The value within the ListHead section.
   */
  public ListHeadToken(String value) {
    this.value = value;
  }

  /**
   * Returns the value of the ListHead section.
   * @return The value.
   */
  public String getValue() {
    return this.value;
  }

  /**
   * Updates the z3ExpressionTree with a new ScanfZ3Expression.
   * @param z3ExpressionStack The z3ExpressionStack.
   * @param translationContext The translation context.
   * @param expressionName The expression name.
   * @param finalExpressions The list of final expressions.
   */
  @Override
  public void updateZ3ExpressionTree(Stack<Z3Expression> z3ExpressionStack, TranslationContext translationContext, String expressionName, List<Z3Expression> finalExpressions) {
    int listTailCount = 0;
    int index = this.value.indexOf("ListTail(");
    while (index != -1) {
      listTailCount++;
      index = this.value.indexOf("ListTail(", index + 1);
    }
    z3ExpressionStack.push(new ScanfZ3Expression(listTailCount));
  }
}
