package sg.edu.nus.se.its.validation;

import org.junit.jupiter.api.Test;
import sg.edu.nus.se.its.model.Program;
import sg.edu.nus.se.its.util.TestUtils;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sg.edu.nus.se.its.validation.TestUtils.recompileProgramIntoJson;

/**
 * Basic unit tests for Patch Validator.
 */
public class ReturnStringTest {
  private static final Logger LOGGER = Logger.getLogger(BasicTest.class.getName());

  /**
   * Test 2 identical programs with int variables
   */
  @Test
  void testSameProgram() {
    PatchValidator validator = new PatchValidator();
    recompileProgramIntoJson("printf.c");
    Program returnString = TestUtils.loadProgramByName("printf.c");
    Program returnString2 = TestUtils.loadProgramByName("printf.c");
    boolean result = validator.patchValidation(returnString, returnString2);
    assertTrue(result);
  }

  /**
   * Test 2 different programs with no arguements
   */
  @Test
  void testPrintFNoArguments() {
    PatchValidator validator = new PatchValidator();
    recompileProgramIntoJson("printf4.c");
    recompileProgramIntoJson("printf3.c");
    Program returnString = TestUtils.loadProgramByName("printf4.c");
    Program returnString2 = TestUtils.loadProgramByName("printf3.c");
    boolean result = validator.patchValidation(returnString, returnString2);
    assertFalse(result);
  }

  /**
   * Test programs with same format string but different variable values.
   */
  @Test
  void testPrintFIntArguments() {
    PatchValidator validator = new PatchValidator();
    recompileProgramIntoJson("printf.c");
    recompileProgramIntoJson("printf2.c");
    Program returnString = TestUtils.loadProgramByName("printf.c");
    Program returnString2 = TestUtils.loadProgramByName("printf2.c");
    boolean result = validator.patchValidation(returnString, returnString2);
    assertFalse(result);
  }

  /**
   * Test programss, one has no return value
   */
  @Test
  void testReturnVSNoReturnValue() {
    PatchValidator validator = new PatchValidator();
    recompileProgramIntoJson("printf.c");
    recompileProgramIntoJson("printf7.c");
    Program returnString = TestUtils.loadProgramByName("printf.c");
    Program returnString2 = TestUtils.loadProgramByName("printf7.c");
    boolean result = validator.patchValidation(returnString, returnString2);
    assertFalse(result);
  }

  /**
   * Test same output, different return values
   */
  @Test
  void testDifferentReturnValues() {
    PatchValidator validator = new PatchValidator();
    recompileProgramIntoJson("printf5.c");
    recompileProgramIntoJson("printf7.c");
    Program returnString = TestUtils.loadProgramByName("printf5.c");
    Program returnString2 = TestUtils.loadProgramByName("printf7.c");
    boolean result = validator.patchValidation(returnString, returnString2);
    assertFalse(result);
  }

  /**
   * Tests same value with different variable types (int vs float)
   */
  @Test
  void testSameValueDifferentType() {
    PatchValidator validator = new PatchValidator();
    recompileProgramIntoJson("printf.c");
    recompileProgramIntoJson("printf_float.c");
    Program returnString = TestUtils.loadProgramByName("printf.c");
    Program returnString2 = TestUtils.loadProgramByName("printf_float.c");
    boolean result = validator.patchValidation(returnString, returnString2);
    assertFalse(result);
  }

  /**
   * Tests same value with floats
   */
  @Test
  void testPrintfFloat() {
    PatchValidator validator = new PatchValidator();
    recompileProgramIntoJson("printf_float.c");
    Program returnString = TestUtils.loadProgramByName("printf_float.c");
    Program returnString2 = TestUtils.loadProgramByName("printf_float.c");
    boolean result = validator.patchValidation(returnString, returnString2);
    assertTrue(result);

  }

  /**
   * Test programs with same return type (Strings) but different value.
   */
  @Test
  void testPrintFStringArguments() {
    PatchValidator validator = new PatchValidator();
    recompileProgramIntoJson("printf_string.c");
    recompileProgramIntoJson("printf_string2.c");
    Program returnString = TestUtils.loadProgramByName("printf_string.c");
    Program returnString2 = TestUtils.loadProgramByName("printf_string2.c");
    boolean result = validator.patchValidation(returnString, returnString2);
    assertFalse(result);
  }

  /**
   * Test programs with same return type (Strings) but different value.
   */
  @Test
  void testNoOutput() {
    recompileProgramIntoJson("printf6.c");
    PatchValidator validator = new PatchValidator();
    Program returnString = TestUtils.loadProgramByName("printf6.c");
    Program returnString2 = TestUtils.loadProgramByName("printf6.c");
    boolean result = validator.patchValidation(returnString, returnString2);
    assertTrue(result);

  }

  /**
   * Test programs with same return type (Strings) but different value.
   */
  @Test
  void testNoOutputDifferentReturn() {
    recompileProgramIntoJson("printf6.c");
    recompileProgramIntoJson("printf8.c");
    PatchValidator validator = new PatchValidator();
    Program returnString = TestUtils.loadProgramByName("printf6.c");
    Program returnString2 = TestUtils.loadProgramByName("printf8.c");
    boolean result = validator.patchValidation(returnString, returnString2);
    assertFalse(result);

  }

  // /**
  //  * Test programs with same return type (Strings) but different value.
  //  */
  // @Test
  // void testReturnStringsDiffValue() {
  //   PatchValidator validator = new PatchValidator();
  //   Program returnString = TestUtils.loadProgramByName("ret4.c");
  //   Program returnString2 = TestUtils.loadProgramByName("ret5.c");
  //   boolean result = validator.patchValidation(returnString, returnString2);
  //   assertFalse(result);

  // }


  // /**
  //  * Test programs with same return type (Strings) but different value.
  //  */
  // @Test
  // void testSameStringNoArguments() {
  //   PatchValidator validator = new PatchValidator();
  //   recompileProgramIntoJson("printf3.c");
  //   Program returnString = TestUtils.loadProgramByName("printf4.c");
  //   Program returnString2 = TestUtils.loadProgramByName("printf4.c");
  //   boolean result = validator.patchValidation(returnString, returnString2);
  //   assertFalse(result);

  // }


//   /**
//    * Test programs with same return type (Strings) but different value.
//    */
//   @Test
//   void testTypeMismatchError() {
//     recompileProgramIntoJson("printf_incorrect.c");
//     Program returnString = TestUtils.loadProgramByName("printf_incorrect.c");
//     //assertThrows(RuntimeException.class, () -> StringFormatZ3Expression.interpret());
//   }


}
