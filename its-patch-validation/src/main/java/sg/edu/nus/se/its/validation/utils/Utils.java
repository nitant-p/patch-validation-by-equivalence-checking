package sg.edu.nus.se.its.validation.utils;

import java.util.regex.Pattern;

public class Utils {

  public static final String SPREAD_REGEX = "(?<=(char))(.*?)(?=\\))|(?<=(int))(.*?)(?=\\))|(?<=(float))(.*?)(?=\\))";
  public static final String VARIABLE_REGEX = "[a-zA-Z][a-zA-Z0-9]*";
  public static final String ARITHMETIC_OPERATOR_REGEX = "[+*/-]{1}";

  //order matters here for logical operator regex
  public static final String LOGICAL_OPERATOR_REGEX = "(&&|\\|\\||==|!=|!|<=|>=|<|>)";

  // public static final String FUNCTION_CALL_REGEX = VARIABLE_REGEX + "\\(\\d+\\)"; // TODO: Make this take in any number of arguments and any type of arguments, hybrid type of arguments
  public static final String FUNCTION_CALL_REGEX = "\\w+\\([\"\\w\\.]+\"?\\)";

  public static final String INTEGER_REGEX = "\\b\\d+\\b(?!\\.)";
  public static final String NEGATIVE_INTEGER_REGEX = "-\\(" + INTEGER_REGEX + "\\)";
  public static final String FLOAT_REGEX = "-?\\d+\\.\\d+";
  public static final String NEGATIVE_FLOAT_REGEX = "-\\(" + FLOAT_REGEX + "\\)";
  public static final String ADDITION_OPERATOR_REGEX = "\\+";
  public static final String MINUS_OPERATOR_REGEX = "\\-";
  public static final String MULTIPLICATION_OPERATOR_REGEX = "\\*";
  public static final String DIVISION_OPERATOR_REGEX = "\\/";
  public static final String ITE_REGEX = "ite";
  public static final String TRUE_REGEX = "true";
  public static final String FALSE_REGEX = "false";
  public static final String EQUALITY_REGEX = "==";
  public static final String NOT_EQUAL_REGEX = "!=";
  public static final String GT_REGEX = ">";
  public static final String GT_EQ_REGEX = ">=";
  public static final String LT_REGEX = "<";
  public static final String LT_EQ_REGEX = "<=";
  public static final String ARRAY_ASSIGN_REGEX = "ArrayAssign";
  public static final String ARRAY_CREATE_REGEX = "ArrayCreate\\(([\\w])\\)|ArrayCreate";
  public static final String ARRAY_DEREFERENCE_REGEX = Pattern.quote("[]");
  public static final String OR_REGEX = "\\|\\|";
  public static final String AND_REGEX = "&&";
  public static final String NOT_REGEX = "!";
  public static final String ARRAY_DECLARATION_REGEX = "ArrayDeclaration";
  public static final String STRING_LITERAL_REGEX = "\"([^\"\\\\]|\\\\[\\s\\S])*\"";
  public static final String MODULUS_OPERATOR_REGEX = "%";
  public static final String STRING_REGEX = "\".*\"";
  public static final String STRING_FORMAT_REGEX = "StrFormat";
  public static final String STRING_APPEND_REGEX = "StrAppend";
  public static final String OUTPUT_REGEX = "\\$out";
  public static final String LIST_HEAD_REGEX = "ListHead\\([^,]+,[^\\)]+\\)";
  public static final String LIST_TAIL_REGEX = "ListTail";
  public static final String SCANF_REGEX = "$in";
  public static final String IGNORE_KEYWORD_REGEX = "char|int|float";
  // public static final String GT_REGEX = ">";
  // public static final String GT_REGEX = ">";
  // public static final String OR
  // public static final String AND

  public static boolean isArithmeticOperator(String s) {
    return s.matches(ARITHMETIC_OPERATOR_REGEX);
  }
  public static boolean isSpread(String s) {
    return s.charAt(0) == ',';
  }
  public static boolean isIgnoreKeyword(String s) {
    return s.matches(IGNORE_KEYWORD_REGEX);
  }
  public static boolean isFunctionCall(String s) {
    return s.matches(FUNCTION_CALL_REGEX);
  }

  public static boolean isVariable(String s) {
    return s.matches(VARIABLE_REGEX);
  }

  public static boolean isInteger(String s) {
    return s.matches(INTEGER_REGEX);
  }

  public static boolean isFloat(String s) {
    return s.matches(FLOAT_REGEX);
  }
  public static boolean isAdditionOperator(String s) {
    return s.matches(ADDITION_OPERATOR_REGEX);
  }

  public static boolean isMinusOperator(String s) {
    return s.matches(MINUS_OPERATOR_REGEX);
  }

  public static boolean isMultiplicationOperator(String s) {
    return s.matches(MULTIPLICATION_OPERATOR_REGEX);
  }

  public static boolean isFunction(String s) {
    return s.matches(FUNCTION_CALL_REGEX);
  }

  public static boolean isDivisionOperator(String s) {
    return s.matches(DIVISION_OPERATOR_REGEX);
  }

  public static boolean isLogicalOperator(String s) {
    return s.matches(LOGICAL_OPERATOR_REGEX);
  }

  public static boolean isIte(String s) {
    return s.matches(ITE_REGEX);
  }

  public static boolean isNegativeInteger(String s) {
    return s.matches(NEGATIVE_INTEGER_REGEX);
  }

  public static boolean isNegativeFloat(String s) {
    return s.matches(NEGATIVE_FLOAT_REGEX);
  }

  public static boolean isTrue(String s) {
    return s.matches(TRUE_REGEX);
  }

  public static boolean isFalse(String s) {
    return s.matches(FALSE_REGEX);
  }

  public static boolean isLte(String s) {
    return s.matches(LT_REGEX) ? true : false;
  }

  public static boolean isArrayAssignToken(String s) {
    return s.matches(ARRAY_ASSIGN_REGEX);
  }

  public static boolean isArrayCreateToken(String s) {
    return s.matches(ARRAY_CREATE_REGEX);
  }

  public static boolean isArrayDereferenceOperator(String s) {
    return s.matches(ARRAY_DEREFERENCE_REGEX);
  }


  public static boolean isString(String s) {
     return s.matches(STRING_REGEX);
  }

  public static boolean isStrFormat(String s) {
    return s.matches(STRING_FORMAT_REGEX) ? true : false;
  }

  public static boolean isStrAppend(String s) {
    return s.matches(STRING_APPEND_REGEX) ? true : false;
  }

  public static boolean isOutput(String s) {
    return s.matches(OUTPUT_REGEX) ? true : false;
  }

  public static boolean isArrayDeclarationToken(String s) {
    return s.matches(ARRAY_DECLARATION_REGEX);
  }

  public static boolean isModulusOperator(String s) {
      return s.matches(MODULUS_OPERATOR_REGEX);
  }

  public static boolean isListHead(String s) {
    return s.matches(LIST_HEAD_REGEX);
  }

  public static boolean isListTailToken(String s) {
    return s.matches(LIST_TAIL_REGEX);
  }

  public static boolean isScanfToken(String s) {
    return s.matches(SCANF_REGEX);
  }

  public static boolean isEqualityOperator(String s) {
    return s.matches(EQUALITY_REGEX);
  }

  public static boolean isNotEqualOperator(String s) {
    return s.matches(NOT_EQUAL_REGEX);
  }

  public static boolean isGreaterThanOperator(String s) {
    return s.matches(GT_REGEX);
  }

  public static boolean isLessThanOperator(String s) {
    return s.matches(LT_REGEX);
  }

  public static boolean isGreaterThanOrEqualsOperator(String s) {
    return s.matches(GT_EQ_REGEX);
  }

  public static boolean isLessThanOrEqualsOperator(String s) {
    return s.matches(LT_EQ_REGEX);
  }

  public static boolean isAndOperator(String s) {
    return s.matches(AND_REGEX);
  }

  public static boolean isOrOperator(String s) {
    return s.matches(OR_REGEX);
  }

  public static boolean isNotOperator(String s) {
    return s.matches(NOT_REGEX);
  }
}
