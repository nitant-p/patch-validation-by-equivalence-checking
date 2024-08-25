package sg.edu.nus.se.its.validation;

import org.junit.jupiter.api.Test;
import sg.edu.nus.se.its.model.Program;
import sg.edu.nus.se.its.util.TestUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class is to test for binary operations for the following types.
 * Integer, Float
 */
public class ArithmeticOperationsTest {

  /**
   * Test for addition.
   */
  @Test
  void testAddition() {
    //girecompileProgramIntoJson("add1.c");
    //recompileProgramIntoJson("add2.c");
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("add1.c");
    Program submittedProgram = TestUtils.loadProgramByName("add2.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  /**
   * Test for addition with negative numbers.
   */
  @Test
  void testAdditionWithNegativeNumbers() {
    //recompileProgramIntoJson("add3.c");
    //recompileProgramIntoJson("add3.c");
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("add3.c");
    Program submittedProgram = TestUtils.loadProgramByName("add4.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  /**
   * Test for addition with one positive and one negative number.
   */
  @Test
  void testAdditionWithPositiveAndNegativeNumbers() {
    //recompileProgramIntoJson("add5.c");
    //recompileProgramIntoJson("add6.c");
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("add5.c");
    Program submittedProgram = TestUtils.loadProgramByName("add6.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }


  /**
   * Test for additional whitespace in addition.
   */
  @Test
  void testAdditionWithAdditionalWhitespace() {
    //recompileProgramIntoJson("add9.c");
    //recompileProgramIntoJson("add10.c");
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("add9.c");
    Program submittedProgram = TestUtils.loadProgramByName("add10.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  /**
   * Test for floats addition.
   */
  @Test
  void testAdditionWithFloats() {
    //recompileProgramIntoJson("add11.c");
    //recompileProgramIntoJson("add17.c");
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("add11.c");
    Program submittedProgram = TestUtils.loadProgramByName("add17.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  /**
   * Test increment ++
   */
  @Test
  void testIncrement() {
    //recompileProgramIntoJson("add15.c");
    //recompileProgramIntoJson("add16.c");
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("add15.c");
    Program submittedProgram = TestUtils.loadProgramByName("add15.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  /**
   * Test for subtraction.
   */
  @Test
  void testSubtraction() {
    //recompileProgramIntoJson("sub1.c");
    //recompileProgramIntoJson("sub2.c");
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("sub1.c");
    Program submittedProgram = TestUtils.loadProgramByName("sub2.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  /**
   * Test for subtraction with negative numbers.
   */
  @Test
  void testSubtractionWithNegativeNumbers() {
    //recompileProgramIntoJson("sub3.c");
    //recompileProgramIntoJson("sub4.c");
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("sub3.c");
    Program submittedProgram = TestUtils.loadProgramByName("sub4.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertFalse(result);
  }


  /**
   * Test for subtraction with floats.
   */
  @Test
  void testSubtractionWithFloats() {
    //recompileProgramIntoJson("sub6.c");
    //recompileProgramIntoJson("sub7.c");
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("sub6.c");
    Program submittedProgram = TestUtils.loadProgramByName("sub7.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  /**
   * Test for multiplication.
   */
  @Test
  void testMultiplication() {
    //recompileProgramIntoJson("mult1.c");
    //recompileProgramIntoJson("mult2.c");
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("mult1.c");
    Program submittedProgram = TestUtils.loadProgramByName("mult2.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  /**
   * Test for multiplication with negative numbers.
   */
  @Test
  void testMultiplicationWithNegativeNumbers() {
    //recompileProgramIntoJson("mult3.c");
    //recompileProgramIntoJson("mult4.c");
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("mult4.c");
    Program submittedProgram = TestUtils.loadProgramByName("mult4.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  /**
   * Test for multiplication with floats.
   */
  @Test
  void testMultiplicationWithFloats() {
    //recompileProgramIntoJson("mult5.c");
    //recompileProgramIntoJson("mult6.c");
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("mult5.c");
    Program submittedProgram = TestUtils.loadProgramByName("mult6.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  /**
   * Test for division.
   */
  @Test
  void testDivision() {
    //recompileProgramIntoJson("div1.c");
    //recompileProgramIntoJson("div2.c");
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("div1.c");
    Program submittedProgram = TestUtils.loadProgramByName("div2.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }


  /**
   * Test for dividing zero by a non-zero number
   */
  @Test
  void testDivisionOfZeroByNonZero() {
    //recompileProgramIntoJson("div6.c");
    //recompileProgramIntoJson("div5.c");
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("div6.c");
    Program submittedProgram = TestUtils.loadProgramByName("div5.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  /**
   * Test for integer division, eg 10/3.3 or 20/6
   */
  @Test
  void testIntegerDivision() {
    //recompileProgramIntoJson("div4.c");
    //recompileProgramIntoJson("div7.c");
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("div4.c");
    Program submittedProgram = TestUtils.loadProgramByName("div7.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }


  /**
   * Test for division by floats.
   */
  @Test
  void testDivisionByFloats() {
    //recompileProgramIntoJson("div10.c");
    //recompileProgramIntoJson("div11.c");
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("div10.c");
    Program submittedProgram = TestUtils.loadProgramByName("div11.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  /**
   * Test for modulus.
   */
  @Test
  void testModulus() {
    //recompileProgramIntoJson("mod1.c");
    //recompileProgramIntoJson("mod2.c");
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("mod1.c");
    Program submittedProgram = TestUtils.loadProgramByName("mod2.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  /**
   * Test with negative dividend
   */
  @Test
  void testModulusWithNegativeNumbers() {
    //recompileProgramIntoJson("mod3.c");
    //recompileProgramIntoJson("mod4.c");
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("mod3.c");
    Program submittedProgram = TestUtils.loadProgramByName("mod4.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  /**
   * Test assignment
   */
  @Test
  void testAssignment() {
    //recompileProgramIntoJson("assign1.c");
    //recompileProgramIntoJson("assign2.c");
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("assign1.c");
    Program submittedProgram = TestUtils.loadProgramByName("assign2.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

}
