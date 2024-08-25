package sg.edu.nus.se.its.validation;

import org.junit.jupiter.api.Test;
import sg.edu.nus.se.its.model.Program;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sg.edu.nus.se.its.validation.TestUtils.recompileProgramIntoJson;

/**
 * This class is to test for scanf operations in C programs.
 */
public class ScanfTest {

  @Test
  void testMatchingPrograms() {
    recompileProgramIntoJson("scanf1.c");
    recompileProgramIntoJson("scanf1.c");
    PatchValidator validator = new PatchValidator();
    Program ref = TestUtils.loadProgramByName("scanf1.c");
    Program sub = TestUtils.loadProgramByName("scanf1.c");
    boolean result = validator.patchValidation(ref, sub);
    assertTrue(result);
  }

  @Test
  void testSimpleDifferentPrograms() {
    recompileProgramIntoJson("scanf1.c");
    recompileProgramIntoJson("scanf2.c");
    PatchValidator validator = new PatchValidator();
    Program ref = TestUtils.loadProgramByName("scanf1.c");
    Program sub = TestUtils.loadProgramByName("scanf2.c");
    boolean result = validator.patchValidation(ref, sub);
    assertFalse(result);
  }

  @Test
  void testComplexTrue() {
    recompileProgramIntoJson("scanf3.c");
    recompileProgramIntoJson("scanf4.c");
    PatchValidator validator = new PatchValidator();
    Program ref = TestUtils.loadProgramByName("scanf3.c");
    Program sub = TestUtils.loadProgramByName("scanf4.c");
    boolean result = validator.patchValidation(ref, sub);
    assertTrue(result);
  }

  @Test
  void testScanfWithSimpleArithmeticTrue1() {
    recompileProgramIntoJson("scanf5.c");
    recompileProgramIntoJson("scanf5.c");
    PatchValidator validator = new PatchValidator();
    Program ref = TestUtils.loadProgramByName("scanf5.c");
    Program sub = TestUtils.loadProgramByName("scanf5.c");
    boolean result = validator.patchValidation(ref, sub);
    assertTrue(result);
  }

  @Test
  void testScanfWithSimpleArithmeticTrue2() {
    recompileProgramIntoJson("scanf5.c");
    recompileProgramIntoJson("scanf6.c");
    PatchValidator validator = new PatchValidator();
    Program ref = TestUtils.loadProgramByName("scanf5.c");
    Program sub = TestUtils.loadProgramByName("scanf6.c");
    boolean result = validator.patchValidation(ref, sub);
    assertTrue(result);
  }

  @Test
  void testScanfWithSimpleArithmeticTrue3() {
    recompileProgramIntoJson("scanf9.c");
    recompileProgramIntoJson("scanf10.c");
    PatchValidator validator = new PatchValidator();
    Program ref = TestUtils.loadProgramByName("scanf9.c");
    Program sub = TestUtils.loadProgramByName("scanf10.c");
    boolean result = validator.patchValidation(ref, sub);
    assertTrue(result);
  }

  @Test
  void testScanfWithSimpleArithmeticFalse1() {
    recompileProgramIntoJson("scanf7.c");
    recompileProgramIntoJson("scanf8.c");
    PatchValidator validator = new PatchValidator();
    Program ref = TestUtils.loadProgramByName("scanf7.c");
    Program sub = TestUtils.loadProgramByName("scanf8.c");
    boolean result = validator.patchValidation(ref, sub);
    assertFalse(result);
  }
}
