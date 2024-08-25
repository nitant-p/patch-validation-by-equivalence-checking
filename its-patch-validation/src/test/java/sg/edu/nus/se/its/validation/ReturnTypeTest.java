package sg.edu.nus.se.its.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;


/**
 * Unit test for return statements with different return types.
 */
public class ReturnTypeTest {

  /**
   * Test programs with different return type (integer, string) but same return value.
   * ret2: return 1
   * ret3: return "1"
   */
  @Test
  void testReturnSameValueDiffType() {
    assertFalse(sg.edu.nus.se.its.validation.TestUtils.hasSameReturnValue("ret3.c", "ret2.c"));
  }

  /**
   * Test programs with different return type (integer, string) and different return value.
   * ret3: return "1"
   * ret1: return 0
   */
  @Test
  void testReturnDiffValueDiffType() {
    assertFalse(sg.edu.nus.se.its.validation.TestUtils.hasSameReturnValue("ret3.c", "ret1.c"));
  }

}
