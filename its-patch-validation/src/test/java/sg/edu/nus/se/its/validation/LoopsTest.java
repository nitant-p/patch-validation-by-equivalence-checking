package sg.edu.nus.se.its.validation;

import org.junit.jupiter.api.Test;
import sg.edu.nus.se.its.model.Program;
import sg.edu.nus.se.its.util.TestUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sg.edu.nus.se.its.validation.TestUtils.recompileProgramIntoJson;

public class LoopsTest {

  // Test 2 same while loops exactly
  @Test
  void testSameWhileLoops() {
    recompileProgramIntoJson("loop_while_same1.c");
    recompileProgramIntoJson("loop_while_same2.c");
    Program referenceSolution = TestUtils.loadProgramByName("loop_while_same1.c");
    PatchValidator validator = new PatchValidator();
    Program submittedProgram = TestUtils.loadProgramByName("loop_while_same2.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  // Test 2 same for loops exactly
  @Test
  void testSameForLoops() {
    recompileProgramIntoJson("loop_for_same1.c");
    recompileProgramIntoJson("loop_for_same2.c");
    Program referenceSolution = TestUtils.loadProgramByName("loop_for_same1.c");
    PatchValidator validator = new PatchValidator();
    Program submittedProgram = TestUtils.loadProgramByName("loop_for_same2.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  //Test similar while and for loops
  @Test
  void testSimilarLoops() {
    recompileProgramIntoJson("loop_for_same1.c");
    recompileProgramIntoJson("loop_while_same1.c");
    Program referenceSolution = TestUtils.loadProgramByName("loop_for_same1.c");
    PatchValidator validator = new PatchValidator();
    Program submittedProgram = TestUtils.loadProgramByName("loop_while_same1.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  // 2 different while loops
  @Test
  void testDifferentWhileLoops() {
    recompileProgramIntoJson("loop_while_same1.c");
    recompileProgramIntoJson("loop_while_different2.c");
    Program referenceSolution = TestUtils.loadProgramByName("loop_while_same1.c");
    PatchValidator validator = new PatchValidator();
    Program submittedProgram = TestUtils.loadProgramByName("loop_while_different2.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertFalse(result);
  }

  // 2 different for loops
  @Test
  void testDifferentForLoops() {
    recompileProgramIntoJson("loop_for_same1.c");
    recompileProgramIntoJson("loop_for_different2.c");
    Program referenceSolution = TestUtils.loadProgramByName("loop_for_same1.c");
    PatchValidator validator = new PatchValidator();
    Program submittedProgram = TestUtils.loadProgramByName("loop_for_different2.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertFalse(result);
  }

  // Test 2 loops with return statements within
  @Test
  void testSameLoopsWithReturnWithin() {
    recompileProgramIntoJson("loop_return_same1.c");
    recompileProgramIntoJson("loop_return_same2.c");
    Program referenceSolution = TestUtils.loadProgramByName("loop_return_same1.c");
    PatchValidator validator = new PatchValidator();
    Program submittedProgram = TestUtils.loadProgramByName("loop_return_same2.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  // Test 2 different loops with return statements within
  @Test
  void testDifferentLoopsWithReturnWithin() {
    recompileProgramIntoJson("loop_return_same1.c");
    recompileProgramIntoJson("loop_return_different2.c");
    Program referenceSolution = TestUtils.loadProgramByName("loop_return_same1.c");
    PatchValidator validator = new PatchValidator();
    Program submittedProgram = TestUtils.loadProgramByName("loop_return_different2.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertFalse(result);
  }

  @Test
  void testForLoopDifferentInitial() {
    recompileProgramIntoJson("loop_for_same1.c");
    recompileProgramIntoJson("loop_for_different_initial.c");
    Program referenceSolution = TestUtils.loadProgramByName("loop_for_same1.c");
    PatchValidator validator = new PatchValidator();
    Program submittedProgram = TestUtils.loadProgramByName("loop_for_different_initial.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertFalse(result);
  }
}
