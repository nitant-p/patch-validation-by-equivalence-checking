package sg.edu.nus.se.its.validation;

import org.junit.jupiter.api.Test;
import sg.edu.nus.se.its.model.Program;
import sg.edu.nus.se.its.util.TestUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sg.edu.nus.se.its.validation.TestUtils.recompileProgramIntoJson;

public class NestingTest {

  @Test
  void testPlusOperatorTrue() {
    String prog1 = "nesting/test-plus-1.c";
    String prog2 = "nesting/test-plus-2.c";
    recompileProgramIntoJson(prog1);
    recompileProgramIntoJson(prog2);
    Program referenceSolution = TestUtils.loadProgramByName(prog1);
    Program submittedProgram = TestUtils.loadProgramByName(prog1);
    PatchValidator validator = new PatchValidator();
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void TestPlusOperatorFalse() {
    String prog1 = "nesting/test-plus-1.c";
    String prog2 = "nesting/test-plus-2.c";
    recompileProgramIntoJson(prog1);
    recompileProgramIntoJson(prog2);
    Program referenceSolution = TestUtils.loadProgramByName(prog1);
    Program submittedProgram = TestUtils.loadProgramByName(prog2);
    PatchValidator validator = new PatchValidator();
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertFalse(result);
  }

  @Test
  void TestMinusOperatorTrue() {
    String prog1 = "nesting/test-minus-1.c";
    String prog2 = "nesting/test-minus-1.c";
    recompileProgramIntoJson(prog1);
    recompileProgramIntoJson(prog2);
    Program referenceSolution = TestUtils.loadProgramByName(prog1);
    Program submittedProgram = TestUtils.loadProgramByName(prog2);
    PatchValidator validator = new PatchValidator();
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void TestMinusOperatorFalse() {
    String prog1 = "nesting/test-minus-1.c";
    String prog2 = "nesting/test-minus-2.c";
    recompileProgramIntoJson(prog1);
    recompileProgramIntoJson(prog2);
    Program referenceSolution = TestUtils.loadProgramByName(prog1);
    Program submittedProgram = TestUtils.loadProgramByName(prog2);
    PatchValidator validator = new PatchValidator();
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertFalse(result);
  }

  @Test
  void TestMultiplyOperatorTrue() {
    String prog1 = "nesting/test-multiply-1.c";
    String prog2 = "nesting/test-multiply-2.c";
    recompileProgramIntoJson(prog1);
    recompileProgramIntoJson(prog2);
    Program referenceSolution = TestUtils.loadProgramByName(prog1);
    Program submittedProgram = TestUtils.loadProgramByName(prog2);
    PatchValidator validator = new PatchValidator();
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void TestMultiplyOperatorFalse() {
    String prog1 = "nesting/test-multiply-1.c";
    String prog2 = "nesting/test-multiply-2.c";
    recompileProgramIntoJson(prog1);
    recompileProgramIntoJson(prog2);
    Program referenceSolution = TestUtils.loadProgramByName(prog1);
    Program submittedProgram = TestUtils.loadProgramByName(prog2);
    PatchValidator validator = new PatchValidator();
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertFalse(result);
  }

  @Test
  void TestDivideOperatorTrue() {
    String prog1 = "nesting/test-divide-1.c";
    String prog2 = "nesting/test-divide-2.c";
    recompileProgramIntoJson(prog1);
    recompileProgramIntoJson(prog2);
    Program referenceSolution = TestUtils.loadProgramByName(prog1);
    Program submittedProgram = TestUtils.loadProgramByName(prog2);
    PatchValidator validator = new PatchValidator();
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void TestDivideOperatorFalse() {
    String prog1 = "nesting/test-divide-1.c";
    String prog2 = "nesting/test-divide-2.c";
    recompileProgramIntoJson(prog1);
    recompileProgramIntoJson(prog2);
    Program referenceSolution = TestUtils.loadProgramByName(prog1);
    Program submittedProgram = TestUtils.loadProgramByName(prog2);
    PatchValidator validator = new PatchValidator();
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertFalse(result);
  }

  @Test
  void TestMultipleOperatorsTrue() {
    String prog1 = "nesting/test-multiple-operators-1.c";
    String prog2 = "nesting/test-multiple-operators-1.c";
    recompileProgramIntoJson(prog1);
    recompileProgramIntoJson(prog2);
    Program referenceSolution = TestUtils.loadProgramByName(prog1);
    Program submittedProgram = TestUtils.loadProgramByName(prog2);
    PatchValidator validator = new PatchValidator();
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void TestMultipleOperatorsTrueDiffVariableName() {
    String prog1 = "nesting/test-multiple-operators-1.c";
    String prog2 = "nesting/test-multiple-operators-2.c";
    recompileProgramIntoJson(prog1);
    recompileProgramIntoJson(prog2);
    Program referenceSolution = TestUtils.loadProgramByName(prog1);
    Program submittedProgram = TestUtils.loadProgramByName(prog2);
    PatchValidator validator = new PatchValidator();
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void TestMultipleOperatorsTrueReturnOperationVsInt() {
    String prog1 = "nesting/test-multiple-operators-1.c";
    String prog2 = "nesting/test-multiple-operators-3.c";
    recompileProgramIntoJson(prog1);
    recompileProgramIntoJson(prog2);
    Program referenceSolution = TestUtils.loadProgramByName(prog1);
    Program submittedProgram = TestUtils.loadProgramByName(prog2);
    PatchValidator validator = new PatchValidator();
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void TestMultipleOperatorsTrueFloats() {
    String prog1 = "nesting/test-multiple-operators-4.c";
    String prog2 = "nesting/test-multiple-operators-4.c";
    recompileProgramIntoJson(prog1);
    recompileProgramIntoJson(prog2);
    Program referenceSolution = TestUtils.loadProgramByName(prog1);
    Program submittedProgram = TestUtils.loadProgramByName(prog2);
    PatchValidator validator = new PatchValidator();
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void TestMulipleOperatorsFalseDiffVariableResult() {
    String prog1 = "nesting/test-multiple-operators-1.c";
    String prog2 = "nesting/test-multiple-operators-5.c";
    recompileProgramIntoJson(prog1);
    recompileProgramIntoJson(prog2);
    Program referenceSolution = TestUtils.loadProgramByName(prog1);
    Program submittedProgram = TestUtils.loadProgramByName(prog2);
    PatchValidator validator = new PatchValidator();
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertFalse(result);
  }

  @Test
  void TestMulipleOperatorsFalseReturnOperationVsInt() {
    String prog1 = "nesting/test-multiple-operators-1.c";
    String prog2 = "nesting/test-multiple-operators-6.c";
    recompileProgramIntoJson(prog1);
    recompileProgramIntoJson(prog2);
    Program referenceSolution = TestUtils.loadProgramByName(prog1);
    Program submittedProgram = TestUtils.loadProgramByName(prog2);
    PatchValidator validator = new PatchValidator();
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertFalse(result);
  }

  @Test
  void TestNestedOperationsWithFunctionsTrue() {
    String prog1 = "nesting/test-nested-functions-1.c";
    String prog2 = "nesting/test-nested-functions-1.c";
    recompileProgramIntoJson(prog1);
    recompileProgramIntoJson(prog2);
    Program referenceSolution = TestUtils.loadProgramByName(prog1);
    Program submittedProgram = TestUtils.loadProgramByName(prog2);
    PatchValidator validator = new PatchValidator();
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void TestNestedOperationsWithFunctionsTrueReturnOperationVsInt() {
    String prog1 = "nesting/test-nested-functions-1.c";
    String prog2 = "nesting/test-nested-functions-2.c";
    recompileProgramIntoJson(prog1);
    recompileProgramIntoJson(prog2);
    Program referenceSolution = TestUtils.loadProgramByName(prog1);
    Program submittedProgram = TestUtils.loadProgramByName(prog2);
    PatchValidator validator = new PatchValidator();
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  @Test
  void TestNestedOperationsWithFunctionsFalseDiffVariable() {
    String prog1 = "nesting/test-nested-functions-1.c";
    String prog2 = "nesting/test-nested-functions-3.c";
    recompileProgramIntoJson(prog1);
    recompileProgramIntoJson(prog2);
    Program referenceSolution = TestUtils.loadProgramByName(prog1);
    Program submittedProgram = TestUtils.loadProgramByName(prog2);
    PatchValidator validator = new PatchValidator();
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }
}
