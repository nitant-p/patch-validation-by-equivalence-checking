package sg.edu.nus.se.its.validation;

import org.junit.jupiter.api.Test;
import sg.edu.nus.se.its.model.Program;
import sg.edu.nus.se.its.util.TestUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Unit for primitive boolean return statements.
 * Since boolean in C is represented by 0 and 1, we can compare the return values directly.
 * TODO: Python boolean return statements
 */
public class ReturnBooleanTest {
  @Test
  void testReturnBooleanDiffValue() {
    PatchValidator validator = new PatchValidator();
    Program returnZero = TestUtils.loadProgramByName("ret1.c");
    Program returnOne = TestUtils.loadProgramByName("ret2.c");
    boolean result = validator.patchValidation(returnZero, returnOne);
    assertFalse(result);
  }

  @Test
  void testBooleanAndReturn() {
//    recompileProgramIntoJson("bool_and.c");
    PatchValidator validator = new PatchValidator();
    System.out.println("___Testing Boolean And___");
    Program referenceSolution = TestUtils.loadProgramByName("bool_and.c");
    Program submittedProgram = TestUtils.loadProgramByName("bool_and.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testBooleanOrReturn() {
//    recompileProgramIntoJson("bool_or.c");
    PatchValidator validator = new PatchValidator();
    System.out.println("___Testing Boolean Or___");
    Program referenceSolution = TestUtils.loadProgramByName("bool_or.c");
    Program submittedProgram = TestUtils.loadProgramByName("bool_or.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testBooleanVarReturn() {
//    recompileProgramIntoJson("bool_var.c");
    PatchValidator validator = new PatchValidator();
    System.out.println("___Testing Boolean Var___");
    Program referenceSolution = TestUtils.loadProgramByName("bool_var.c");
    Program submittedProgram = TestUtils.loadProgramByName("bool_and.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testBooleanAssignment() {
//    recompileProgramIntoJson("bool_assignment.c");
    PatchValidator validator = new PatchValidator();
    System.out.println("\n___Testing Boolean Assignment___");
    Program referenceSolution = TestUtils.loadProgramByName("bool_or.c");
    Program submittedProgram = TestUtils.loadProgramByName("bool_assignment.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testTernaryLessThan() {
//    recompileProgramIntoJson("bool_lt.c");
    PatchValidator validator = new PatchValidator();
    System.out.println("\n___Testing Boolean Less Than___");
    Program referenceSolution = TestUtils.loadProgramByName("bool_lt.c");
    Program submittedProgram = TestUtils.loadProgramByName("bool_lt.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testTernaryGreaterThan() {
//    recompileProgramIntoJson("bool_gt.c");
//    recompileProgramIntoJson("bool_lt.c");
    PatchValidator validator = new PatchValidator();
    System.out.println("\n___Testing Boolean Greater Than___");
    Program referenceSolution = TestUtils.loadProgramByName("bool_lt.c");
    Program submittedProgram = TestUtils.loadProgramByName("bool_gt.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertFalse(result);
  }

  @Test
  void testBooleanEqualsTo() {
//    recompileProgramIntoJson("bool_eq.c");
    PatchValidator validator = new PatchValidator();
    System.out.println("\n___Testing Boolean Equal___");
    Program referenceSolution = TestUtils.loadProgramByName("bool_eq.c");
    Program submittedProgram = TestUtils.loadProgramByName("bool_eq.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testBooleanNot() {
//    recompileProgramIntoJson("bool_not.c");
    PatchValidator validator = new PatchValidator();
    System.out.println("\n___Testing Boolean Not___");
    Program referenceSolution = TestUtils.loadProgramByName("bool_not.c");
    Program submittedProgram = TestUtils.loadProgramByName("bool_not.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void testBooleanNotEquals() {
//     recompileProgramIntoJson("bool_notEq.c");
    PatchValidator validator = new PatchValidator();
    System.out.println("\n___Testing Boolean Not___");
    Program referenceSolution = TestUtils.loadProgramByName("bool_not.c");
    Program submittedProgram = TestUtils.loadProgramByName("bool_notEq.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }


  @Test
  void testBooleanLTEquals() {
//    recompileProgramIntoJson("bool_lte.c");
//    recompileProgramIntoJson("bool_lt.c");
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("bool_lte.c");
    Program submittedProgram = TestUtils.loadProgramByName("bool_lt.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertFalse(result);
  }

  @Test
  void testBooleanGTEquals() {
//    recompileProgramIntoJson("bool_gte.c");
//    recompileProgramIntoJson("bool_lte.c");
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("bool_gte.c");
    Program submittedProgram = TestUtils.loadProgramByName("bool_lte.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    System.out.println("__________Boolean Done___________\n");
    assertTrue(result);
  }

}
