package sg.edu.nus.se.its.validation;

import org.junit.jupiter.api.Test;
import sg.edu.nus.se.its.model.Program;
import sg.edu.nus.se.its.util.TestUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class is to test for return variable statements.
 * Includes: assignments to variables, return statements with variables
 */
public class ReturnVariableTest {

  /**
   * Test for variable assignments, with same return value.
   */
  @Test
  void testVariableReturnValue() {
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("c14.c");
    Program submittedProgram = TestUtils.loadProgramByName("c14.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  /**
   * Test for variable assignments, with different return values.
   */
  @Test
  void testTwoVariableReturnValues() {
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("c15.c");
    Program submittedProgram = TestUtils.loadProgramByName("c14.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertFalse(result);
  }

}
