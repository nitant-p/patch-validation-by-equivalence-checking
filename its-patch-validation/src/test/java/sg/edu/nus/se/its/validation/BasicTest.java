package sg.edu.nus.se.its.validation;

import org.junit.jupiter.api.Test;
import sg.edu.nus.se.its.model.Program;
import sg.edu.nus.se.its.util.TestUtils;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Basic unit tests for Patch Validator.
 */
public class BasicTest {
  private static final Logger LOGGER = Logger.getLogger(BasicTest.class.getName());

  @Test
    // Same program
  void testSameProgramVerification() {
    LOGGER.info("Testing: testSameProgramVerification");
    //recompileProgramIntoJson("c1.c");
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("c1.c");
    Program submittedProgram = TestUtils.loadProgramByName("c1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  //@Test //Same program different variables
  //void basicSemanticEquivalence () {
  //  PatchValidator validator = new PatchValidator();
  //  Program referenceSolution = TestUtils.loadProgramByName("c2.c");
  //  //changed variable names - currently says "not equivalent"
  //  Program submittedProgram = TestUtils.loadProgramByName("i2.c");
  //  boolean result = validator.patchValidation(referenceSolution, submittedProgram);
  //  assertTrue(result);
  //}

  // @Test // Both programs return same integer
  // void testSameReturnValue() {
  //   PatchValidator validator = new PatchValidator();
  //   Program referenceSolution = TestUtils.loadProgramByName("c3.c");
  //   Program submittedProgram = TestUtils.loadProgramByName("c4.c");
  //   boolean result = validator.patchValidation(referenceSolution, submittedProgram);
  //   assertTrue(result);
  // }

  //@Test // Same program, returns 0 vs 1
  //void testSameProgramDifferentReturnValue() {
  //  PatchValidator validator = new PatchValidator();
  //  Program referenceSolution = TestUtils.loadProgramByName("c1.c");
  //  //changed variable names - currently says "not equivalent"
  //  Program submittedProgram = TestUtils.loadProgramByName("c1_1.c");
  //  boolean result = validator.patchValidation(referenceSolution, submittedProgram);
  //  assertFalse(result);
  //}

  //@Test //2 variable with same return ,
  //void testVariableReturnValue() {
  //  System.out.println("------Testing Variable Return Values----------");
  //  PatchValidator validator = new PatchValidator();
  //  Program referenceSolution = TestUtils.loadProgramByName("c13.c");
  //  Program submittedProgram = TestUtils.loadProgramByName("c13.c");
  //  boolean result = validator.patchValidation(referenceSolution, submittedProgram);
  //  assertTrue(result);
  //}

  //@Test //One integer, one variable return with same values
  //void testIntegerAndVariableSame() {
  //  System.out.println("------Testing Variable Return Values----------");
  //  PatchValidator validator = new PatchValidator();
  //  Program referenceSolution = TestUtils.loadProgramByName("c2.c");
  //  Program submittedProgram = TestUtils.loadProgramByName("c14.c");
  //  boolean result = validator.patchValidation(referenceSolution, submittedProgram);
  //  assertTrue(result);
  //}

  // @Test //One integer, one variable return with different values
  // void testIntegerAndVariableDifferent() {
  //   System.out.println("------Testing Variable Return Values----------");
  //   PatchValidator validator = new PatchValidator();
  //   Program referenceSolution = TestUtils.loadProgramByName("c2.c");
  //   Program submittedProgram = TestUtils.loadProgramByName("c13.c");
  //   boolean result = validator.patchValidation(referenceSolution, submittedProgram);
  //   assertFalse(result);
  // }

  //@Test //2 variables both have different return values
  //void testTwoVariableReturnValues() {
  //  System.out.println("------Testing Variable Return Values----------");
  //  PatchValidator validator = new PatchValidator();
  //  Program referenceSolution = TestUtils.loadProgramByName("c13.c");
  //  Program submittedProgram = TestUtils.loadProgramByName("c14.c");
  //  boolean result = validator.patchValidation(referenceSolution, submittedProgram);
  //  assertFalse(result);
  //}

  //@Test
  //void testSimpleIfStatement() {
  //  PatchValidator validator = new PatchValidator();
  //  Program referenceSolution = TestUtils.loadProgramByName("c3.c");
  //  Program submittedProgram = TestUtils.loadProgramByName("i3.c");
  //  boolean result = validator.patchValidation(referenceSolution, submittedProgram);
  //  assertTrue(result);
  //}

  //@Test
  //void testSideEffectSecondLastLineRedundantReassignment() {
  //  PatchValidator validator = new PatchValidator();
  //  Program referenceSolution = TestUtils.loadProgramByName("c15.c");
  //  Program submittedProgram = TestUtils.loadProgramByName("c16.c");
  //  boolean result = validator.patchValidation(referenceSolution, submittedProgram);
  //  assertTrue(result);
  //}

  //@Test
  //void testSideEffectNonRedundantReassignment() {
////    recompileProgramIntoJson("sideeffects10.c");
  //  Program referenceSolution = TestUtils.loadProgramByName("sideeffects10.c");
  //  assert referenceSolution != null;
  //  Expr<?> e = PatchValidator.z3Translate(referenceSolution);
  //  assert e != null;
  //  assertEquals(1005, Integer.parseInt(e.toString()));
  //}

  //@Test
  //void testFunctionCalling() {
  //  // recompileProgramIntoJson("func.c");
  //  Program referenceSolution = TestUtils.loadProgramByName("func.c");
  //  assert referenceSolution != null;
  //  Expr<?> e = PatchValidator.z3Translate(referenceSolution);
  //  assert e != null;
  //  assertEquals(100, Integer.parseInt(e.toString()));
  //}

  //@Test
  //void testBinary() {
  //  // recompileProgramIntoJson("binary.c");
  //  Program referenceSolution = TestUtils.loadProgramByName("binary.c");
  //  assert referenceSolution != null;
  //  Expr<?> e = PatchValidator.z3Translate(referenceSolution);
  //  assert e != null;
  //  assertEquals(4, Integer.parseInt(e.toString()));
  //}

  @Test
  void testBasicArray() {
    Program ref = TestUtils.loadProgramByName("array_with_return.c");
    Program sub = TestUtils.loadProgramByName("array_with_diff_return.c");
    PatchValidator validator = new PatchValidator();
    assertFalse(validator.patchValidation(ref, sub));
  }

  @Test
  void testBasicArraySameProgram() {
    Program ref = TestUtils.loadProgramByName("array_with_return.c");
    Program sub = TestUtils.loadProgramByName("array_with_return.c");
    PatchValidator validator = new PatchValidator();
    assertTrue(validator.patchValidation(ref, sub));
  }

  //// simple if condition
  //@Test
  //void testSimpleConditionalStatement() {
////    recompileProgramIntoJson("c3.c");
////    recompileProgramIntoJson("i3.c");
  //  PatchValidator validator = new PatchValidator();
  //  Program referenceSolution = TestUtils.loadProgramByName("c3.c");
  //  Program submittedProgram = TestUtils.loadProgramByName("i3.c");
  //  boolean result = validator.patchValidation(referenceSolution, submittedProgram);
  //  assertTrue(result);
  //}

  //@Test
  //void testSimpleAndConditionalStatementTrue() {
////    recompileProgramIntoJson("trueIfStatementAndCond.c");
////    recompileProgramIntoJson("trueIfStatementAndCond2.c");
  //  PatchValidator validator = new PatchValidator();
  //  Program referenceSolution = TestUtils.loadProgramByName("trueIfStatementAndCond.c");
  //  Program submittedProgram = TestUtils.loadProgramByName("trueIfStatementAndCond2.c");
  //  boolean result = validator.patchValidation(referenceSolution, submittedProgram);
  //  assertTrue(result);
  //}

  //@Test
  //void testNestedOperations() {
  //  // recompileProgramIntoJson("nestedOperations1.c");
  //  // recompileProgramIntoJson("nestedOperations2.c");
  //  PatchValidator validator = new PatchValidator();
  //  Program referenceSolution = TestUtils.loadProgramByName("nestedOperations1.c");
  //  Program submittedProgram = TestUtils.loadProgramByName("nestedOperations2.c");
  //  boolean result = validator.patchValidation(referenceSolution, submittedProgram);
  //  assertTrue(result);
  //}

  //// @Test
  //// void testNestedFunctions() {
  ////  // recompileProgramIntoJson("nestedFunctions1.c");
  ////  // recompileProgramIntoJson("nestedFunctions2.c");
  ////  PatchValidator validator = new PatchValidator();
  ////  Program referenceSolution = TestUtils.loadProgramByName("nestedFunctions1.c");
  ////  Program submittedProgram = TestUtils.loadProgramByName("nestedFunctions2.c");
  ////  boolean result = validator.patchValidation(referenceSolution, submittedProgram);
  ////  assertFalse(result);
  //// }

  //@Test
  //void testNestedFunctionsSameProgram() {
  // // recompileProgramIntoJson("nestedFunctions2.c");
  // PatchValidator validator = new PatchValidator();
  // Program referenceSolution = TestUtils.loadProgramByName("nestedFunctions2.c");
  // Program submittedProgram = TestUtils.loadProgramByName("nestedFunctions2.c");
  // boolean result = validator.patchValidation(referenceSolution, submittedProgram);
  // assertTrue(result);
  //}

  @Test
  void testRefactor() {
    LOGGER.info("Testing: testRefactor");
//    recompileProgramIntoJson("refactor1.c");
//    recompileProgramIntoJson("refactor2.c");
    Program ref = TestUtils.loadProgramByName("refactor1.c");
    Program sub = TestUtils.loadProgramByName("refactor2.c");
    PatchValidator validator = new PatchValidator();
    boolean result = validator.patchValidation(ref, sub);
    assertTrue(result);
  }

}
