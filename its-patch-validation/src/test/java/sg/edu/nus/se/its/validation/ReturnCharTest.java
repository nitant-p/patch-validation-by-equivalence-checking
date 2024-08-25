package sg.edu.nus.se.its.validation;

import org.junit.jupiter.api.Test;
import sg.edu.nus.se.its.model.Program;
import sg.edu.nus.se.its.util.TestUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * This unit test is for return statements that return characters.
 */
public class ReturnCharTest {


  /**
   * Test programs with same return type (Characters) and same value.
   */
  @Test
  void testReturnCharSameValue() {
    PatchValidator validator = new PatchValidator();
    Program returnChar = TestUtils.loadProgramByName("ret6.c");
    Program returnChar2 = TestUtils.loadProgramByName("ret6.c");
    boolean result = validator.patchValidation(returnChar, returnChar2);
    assertTrue(result);
  }

  /**
   * Test programs with same return type (Characters) but different value.
   * ret6: return 'a'
   * ret7: return 'b'
   */
  @Test
  void testReturnCharDiffValue() {
    assertTrue(sg.edu.nus.se.its.validation.TestUtils.hasSameReturnValue("ret6.c", "ret7.c"));
  }
}
