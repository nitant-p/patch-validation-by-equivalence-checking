package sg.edu.nus.se.its.validation.tokenizer;

import com.microsoft.z3.*;

import java.lang.RuntimeException;
import java.util.*;
import java.util.regex.*;
import sg.edu.nus.se.its.validation.tokenizer.token.arithmetic.*;
import sg.edu.nus.se.its.validation.tokenizer.token.array.ArrayAssignToken;
import sg.edu.nus.se.its.validation.tokenizer.token.array.ArrayCreateToken;
import sg.edu.nus.se.its.validation.tokenizer.token.array.ArrayDereferenceOperatorToken;
import sg.edu.nus.se.its.validation.tokenizer.token.conditionals.*;
import sg.edu.nus.se.its.validation.utils.Utils;
import sg.edu.nus.se.its.validation.tokenizer.token.*;

public class Tokenizer {
    public static ArrayList<Token> tokenize(String string) throws RuntimeException {
        ArrayList<Token> tokens = new ArrayList<>();

      java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(
              Utils.FUNCTION_CALL_REGEX + "|" +
                      Utils.ITE_REGEX + "|" +
                      Utils.FALSE_REGEX + "|" +
                      Utils.STRING_REGEX + "|" +
                      Utils.STRING_APPEND_REGEX + "|" +
                      Utils.STRING_FORMAT_REGEX + "|"  +
                      Utils.OUTPUT_REGEX + "|" +
                      Utils.LIST_HEAD_REGEX + "|" +
                      Utils.LIST_TAIL_REGEX + "|" +
                      Utils.SCANF_REGEX + "|" +
                      Utils.STRING_LITERAL_REGEX + "|" +
                      Utils.VARIABLE_REGEX + "|" +
                      Utils.TRUE_REGEX + "|" +
                      Utils.INTEGER_REGEX + "|" +
                      Utils.NEGATIVE_INTEGER_REGEX + "|" +
                      Utils.FLOAT_REGEX + "|" +
                      Utils.NEGATIVE_FLOAT_REGEX + "|" +
                      Utils.ARITHMETIC_OPERATOR_REGEX + "|" +
                      Utils.ARRAY_ASSIGN_REGEX + "|" +
                      Utils.ARRAY_CREATE_REGEX + "|" +
                      Utils.LOGICAL_OPERATOR_REGEX + "|" +
                      Utils.ARRAY_DECLARATION_REGEX + "|" +
                      Utils.ARRAY_DEREFERENCE_REGEX + "|" +
                      Utils.MODULUS_OPERATOR_REGEX + "|" +
                      Utils.VARIABLE_REGEX + "|" +
                      Utils.SPREAD_REGEX + "|" +
                      Utils.IGNORE_KEYWORD_REGEX);
    Matcher matcher = pattern.matcher(string);
    while (matcher.find()) {
      String s = matcher.group();
      // NOTE: Order matters here. Token type will be decided by the topmost true result.
      if (Utils.isSpread(s)) {
        tokens.add(new SpreadToken(s));
      } else if (Utils.isIgnoreKeyword(s)) {
        tokens.add(new IgnoreKeywordToken());
      } else if (Utils.isArrayCreateToken(s)) {
        tokens.add(new ArrayCreateToken());
      } else if (Utils.isTrue(s)) {
        tokens.add(new TrueToken());
      } else if (Utils.isFunction(s)) {
        System.out.println(s + " this is a function ");
        tokens.add(new FunctionToken(s));
      } else if (Utils.isFalse(s)) {
        tokens.add(new FalseToken());
      } else if (Utils.isFloat(s)) {
        tokens.add(new FloatToken(s));
      } else if (Utils.isNegativeInteger(s)) {
        String integer = "-" + s.substring(2, s.length() - 1);
        tokens.add(new IntegerToken(integer));
      } else if (Utils.isInteger(s)) {
        tokens.add(new IntegerToken(s));
      } else if (Utils.isMinusOperator(s)) {
        tokens.add(new MinusToken());
      } else if (Utils.isAdditionOperator(s)) {
        tokens.add(new AdditionToken());
      } else if (Utils.isDivisionOperator(s)) {
        tokens.add(new DivisionToken());
      } else if (Utils.isMultiplicationOperator(s)) {
        tokens.add(new MultiplicationToken());
      } else if (Utils.isModulusOperator(s)) {
        tokens.add(new ModulusToken());
      } else if (Utils.isNegativeFloat(s)) {
        String f = "-" + s.substring(2, s.length() - 1);
        tokens.add(new FloatToken(f));
      } else if (Utils.isArrayAssignToken(s)) {
        tokens.add(new ArrayAssignToken());
      } else if (Utils.isLogicalOperator(s)) {
        if (Utils.isEqualityOperator(s)) {
          tokens.add(new EqualityToken());
        } else if (Utils.isNotEqualOperator(s)) {
          tokens.add(new NotEqualsToken());
        } else if (Utils.isGreaterThanOrEqualsOperator(s)) {
          tokens.add(new GreaterThanEqualsToken());
        } else if (Utils.isGreaterThanOperator(s)) {
          tokens.add(new GreaterThanToken());
        } else if (Utils.isLessThanOrEqualsOperator(s)) {
          tokens.add(new LessThanEqualsToken());
        } else if (Utils.isLessThanOperator(s)) {
          tokens.add(new LessThanToken());
        }  else if (Utils.isAndOperator(s)) {
          tokens.add(new AndToken());
        } else if (Utils.isOrOperator(s)) {
          tokens.add(new OrToken());
        } else if (Utils.isNotOperator(s)) {
          tokens.add(new NotToken());
        } else {
          throw new RuntimeException("Tokenizer::tokenize: Unrecognised conditional token: " + s);
        }
      } else if (Utils.isIte(s)) {
        tokens.add(new IteToken());
      } else if (Utils.isArrayDeclarationToken(s)) {
        tokens.add(new ArrayDeclarationToken());
      } else if (Utils.isListHead(s)) {
        tokens.add(new ListHeadToken(s));
      } else if (Utils.isListTailToken(s)) {
      } else if (Utils.isScanfToken(s)) {
      } else if (Utils.isString(s)) {
        tokens.add(new StringToken(s));
      } else if (Utils.isOutput(s)) {
        tokens.add(new OutputToken());
      } else if (Utils.isStrFormat(s)) {
        tokens.add(new StringFormatToken());
      } else if (Utils.isStrAppend(s)) {
        tokens.add(new StringAppendToken());
      } else if (Utils.isVariable(s)) {
        tokens.add(new VariableToken(s));
      } else if (Utils.isArrayDereferenceOperator(s)) {
        tokens.add(new ArrayDereferenceOperatorToken());
      } else {
        throw new RuntimeException("Tokenizer::tokenize: Unrecognised token");
      }
    }

        return tokens;
    }
}
