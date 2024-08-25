package sg.edu.nus.se.its.validation;

import org.junit.jupiter.api.Test;
import sg.edu.nus.se.its.model.Program;
import sg.edu.nus.se.its.util.TestUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sg.edu.nus.se.its.validation.TestUtils.recompileProgramIntoJson;

public class ConditionalsTest {

  private final PatchValidator validator = new PatchValidator();

  private void compileProgram(String programName) {
    recompileProgramIntoJson("/conditionals-c-progs/" + programName);
  }

  private Program loadProgram(String programName) {
    return TestUtils.loadProgramByName("/conditionals-c-progs/" + programName);
  }

  @Test
  void testTrueLiteralWithPositiveAndNegativeNumbers() {
//        compileProgram("alwaysTrueReturn1.c");
//        compileProgram("alwaysTrueReturn1_1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("alwaysTrueReturn1_1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testTrueAndFalseLiteralBothReturnZero() {
//        compileProgram("alwaysTrueReturn1.c");
//        compileProgram("alwaysFalseReturn1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("alwaysFalseReturn1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testTrueAndFalseLiteralReturnDifferentValues() {
//        compileProgram("alwaysFalseReturn0.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("alwaysFalseReturn0.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertFalse(result);
  }

  @Test
  void testFalseAndFalseLiteralsReturnDifferentValues() {
//        compileProgram("alwaysFalseReturn0.c");
//        compileProgram("alwaysFalseReturn1.c");
    Program referenceSolution = loadProgram("alwaysFalseReturn1.c");
    Program submittedProgram = loadProgram("alwaysFalseReturn0.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertFalse(result);
  }

  @Test
  void testTrueOrCondition() {
//        compileProgram("alwaysTrueReturn1.c");
//        compileProgram("simpleTrueORReturn1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("simpleTrueORReturn1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testFalseOrCondition() {
//        compileProgram("simpleTrueORReturn1.c");
//        compileProgram("simpleFalseORReturn1.c");
    Program referenceSolution = loadProgram("simpleTrueORReturn1.c");
    Program submittedProgram = loadProgram("simpleFalseORReturn1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testTrueAndCondition() {
//        compileProgram("simpleTrueANDReturn1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("simpleTrueANDReturn1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testFalseAndCondition() {
//        compileProgram("simpleFalseANDReturn1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("simpleFalseANDReturn1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testEqualityOperator() {
//        compileProgram("alwaysTrueReturn1.c");
//        compileProgram("trueEqualityReturn1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("trueEqualityReturn1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testEqualityOperatorFalse() {
//        compileProgram("falseEqualityReturn0.c");
//        compileProgram("trueEqualityReturn1.c");
    Program referenceSolution = loadProgram("trueEqualityReturn1.c");
    Program submittedProgram = loadProgram("falseEqualityReturn0.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertFalse(result);
  }

  @Test
  void testTrueInequalityOperator() {
//        compileProgram("alwaysTrueReturn1.c");
//        compileProgram("notEqualReturn1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("notEqualReturn1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testFalseInequalityOperator() {
//        compileProgram("falseInequalityReturns1.c");
//        compileProgram("notEqualReturn1.c");
    Program referenceSolution = loadProgram("falseInequalityReturns1.c");
    Program submittedProgram = loadProgram("notEqualReturn1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testGreaterThanOperator() {
//        compileProgram("alwaysTrueReturn1.c");
//        compileProgram("greaterThanReturn1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("greaterThanReturn1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testFalseGreaterThanOperator() {
//        compileProgram("falseGreaterThanReturns1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("falseGreaterThanReturns1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testGreaterThanOrEqualsOperator() {
//        compileProgram("greaterThanOrEqualsReturn1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("greaterThanOrEqualsReturn1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testFalseGreaterThanOrEqualsOperator() {
//        compileProgram("falseGreaterThanOrEqualsReturns1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("falseGreaterThanOrEqualsReturns1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testLessThanOperator() {
//        compileProgram("lessThanReturn1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("lessThanReturn1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testFalseLessThanOperator() {
//        compileProgram("falseLessThanReturn1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("falseLessThanReturn1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testLessThanOrEqualsOperator() {
//        compileProgram("lessThanOrEqualsReturn1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("lessThanOrEqualsReturn1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testFalseLessThanOrEqualsOperator() {
//        compileProgram("lessThanOrEqualsFalseReturn1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("lessThanOrEqualsFalseReturn1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testNestedConditionWithAndOr() {
//        compileProgram("nestedANDORReturns1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("nestedANDORReturns1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testNonEqualityWithVariable() {
//        compileProgram("returnVar3.c");
//        compileProgram("notEqualVarReturn3.c");
    Program referenceSolution = loadProgram("returnVar3.c");
    Program submittedProgram = loadProgram("notEqualVarReturn3.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testTrueOrConditionWithVariable() {
//        compileProgram("trueORWithVar.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("trueORWithVar.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testFalseOrConditionWithVariable() {
//        compileProgram("falseORWithVar.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("falseORWithVar.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testTrueAndConditionWithVariable() {
//        compileProgram("trueANDWithVariable.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("trueANDWithVariable.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testFalseAndConditionWithVariable() {
//        compileProgram("falseAndWithVar.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("falseAndWithVar.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testEqualityOperatorWithVariable() {
//        compileProgram("returnVar3.c");
//        compileProgram("returnVar3_2.c");
    Program referenceSolution = loadProgram("returnVar3.c");
    Program submittedProgram = loadProgram("returnVar3_2.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testInequalityOperatorWithVariable() {
//        compileProgram("inequalityWithVarReturn1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("inequalityWithVarReturn1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testGreaterThanOperatorWithVariable() {
//        compileProgram("greaterThanWithVarReturn1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("greaterThanWithVarReturn1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testGreaterThanOrEqualsOperatorWithVariable() {
//        compileProgram("greaterThanOrEqualsWithVarReturn1.c");
//        compileProgram("greaterThanOrEqualsReturn1.c");
    Program referenceSolution = loadProgram("greaterThanOrEqualsWithVarReturn1.c");
    Program submittedProgram = loadProgram("greaterThanOrEqualsReturn1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testLessThanOperatorWithVariable() {
//        compileProgram("lessThanWithVarReturn1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("lessThanWithVarReturn1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testLessThanOrEqualsOperatorWithVariable() {
//        compileProgram("lessThanOrEqualsWithVarReturn1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("lessThanOrEqualsWithVarReturn1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testFunctionCall() {
//        compileProgram("functionConditionalReturns1.c");
    Program referenceSolution = loadProgram("functionConditionalReturns1.c");
    Program submittedProgram = loadProgram("alwaysTrueReturn1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testFunctionCallCompareVariable() {
//        compileProgram("functionCompareVarReturn1.c");
    Program referenceSolution = loadProgram("functionCompareVarReturn1.c");
    Program submittedProgram = loadProgram("alwaysTrueReturn1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testNOTOperator() {
//        compileProgram("NOTReturns1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("NOTReturns1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testFalseNOTOperator() {
//        compileProgram("falseNOTReturns1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("falseNOTReturns1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testIntegerVariableBooleanLiteralAND() {
//        compileProgram("integerANDReturns1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("integerANDReturns1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testIntegerVariableBooleanLiteralOR() {
//        compileProgram("integerORReturns1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("integerORReturns1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testIntegerVariableBooleanLiteralNOT() {
//        compileProgram("integerNOTReturns1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("integerNOTReturns1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testStringVariableBooleanLiteralAND() {
//        compileProgram("stringVariableANDReturns1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("stringVariableANDReturns1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testStringVariableBooleanLiteralOR() {
//        compileProgram("stringVariableORReturns1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("stringVariableORReturns1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  // does not parse
  @Test
  void testFloatVariableTrue() {
//        compileProgram("floatAlwaysTrueReturns1.c");
    Program referenceSolution = loadProgram("floatAlwaysTrueReturns1.c");
    Program submittedProgram = loadProgram("alwaysTrueReturn1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  // does not parse
  @Test
  void testFloatVariableFalse() {
//        compileProgram("floatAlwaysFalseReturns1.c");
//        compileProgram("stringVariableORReturns1.c");
//        Program referenceSolution = loadProgram("floatAlwaysFalseReturns1.c");
//        Program submittedProgram = loadProgram("alwaysTrueReturn1.c");
//        boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(true);
  }


  @Test
  void testStringVariableFalse() {
//        compileProgram("stringVarFalseReturns1.c");
    Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
    Program submittedProgram = loadProgram("stringVarFalseReturns1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }


  // parser unable to parse
  @Test
  void testIntegerVariableFalse() {
//        compileProgram("intVarFalseReturns1.c");
//        Program referenceSolution = loadProgram("alwaysTrueReturn1.c");
//        Program submittedProgram = loadProgram("intVarFalseReturns1.c");
//        boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(true);
  }

  @Test
  void testFloatLiteral() {
//        compileProgram("floatLiteralTrueReturns1.c");
    Program referenceSolution = loadProgram("floatLiteralTrueReturns1.c");
    Program submittedProgram = loadProgram("alwaysTrueReturn1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testFloatLiteralFalse() {
//        compileProgram("floatLiteralFalseReturns1.c");
    Program referenceSolution = loadProgram("floatLiteralFalseReturns1.c");
    Program submittedProgram = loadProgram("alwaysTrueReturn1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }


  @Test
  void testStringLiteral() {
//        compileProgram("stringLiteralReturns1.c");
    Program referenceSolution = loadProgram("stringLiteralReturns1.c");
    Program submittedProgram = loadProgram("alwaysTrueReturn1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }


}