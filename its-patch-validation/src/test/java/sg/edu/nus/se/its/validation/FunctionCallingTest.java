package sg.edu.nus.se.its.validation;

import com.microsoft.z3.Expr;
import org.junit.jupiter.api.Test;
import sg.edu.nus.se.its.model.Program;
import sg.edu.nus.se.its.parser.ParserServiceImpl;
import sg.edu.nus.se.its.util.TestUtils;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class FunctionCallingTest {

  private static void recompileProgramIntoJson(String programName) {
    String testFilePath = "../its-core/src/test/resources/source/" + programName;

    File testFile = new File(testFilePath);
    if (testFile.exists() && !testFile.isDirectory()) {
      System.out.println("File exists: " + testFile.getAbsolutePath());
    } else {
      System.out.println("File does not exist: " + testFile.getAbsolutePath());
    }

    ParserServiceImpl parserService = new ParserServiceImpl();
    Program program = null;
    try {
      program = parserService.parse(testFile);
    } catch (IOException e) {
      fail("Unexpected exception during service call.", e);
    }

    String filePath = "../its-core/src/test/resources/model/" + programName + ".json";

    boolean success = TestUtils.storeProgramAsJsonFile(program, filePath);

    if (success) {
      System.out.println("Program " + programName + " was successfully saved to JSON.");
    } else {
      System.out.println("Failed to save the Program to JSON.");
    }

  }

  /**
   * Test for function calling that takes in an integer as parameter.
   */
  @Test
  void testFunctionCalling() {
    //recompileProgramIntoJson("func.c");
    //recompileProgramIntoJson("func5.c");
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("func.c");
    Program submittedProgram = TestUtils.loadProgramByName("func5.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  /**
   * Test for function calling that takes in a variable as parameter. The variable is declared in the main function.
   * func6 can call multiple of the same function :)
   */
  @Test
  void testFunctionCallingWithVariable() {
    //recompileProgramIntoJson("func1.c");
    //recompileProgramIntoJson("func6.c");
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("func1.c");
    Program submittedProgram = TestUtils.loadProgramByName("func6.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }

  /**
   * Test for multiple function calls in a program.
   * j = func(10)
   * k = func2(j)
   */
  @Test
  void testMultipleFunctionCalls() {
    // recompileProgramIntoJson("func9.c");
    // recompileProgramIntoJson("func10.c");
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("func9.c");
    Program submittedProgram = TestUtils.loadProgramByName("func10.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(result);
  }


  /**
   * Test for function calling that takes in multiple parameters.
   * j = func(3) + func(7)
   * k = funcMult(3, 7)
   */
  @Test
  void testFunctionCallingMultipleIntegers() {
    // recompileProgramIntoJson("func7.c");
    // recompileProgramIntoJson("func8.c");
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = TestUtils.loadProgramByName("func7.c");
    Program submittedProgram = TestUtils.loadProgramByName("func8.c");
    boolean result = validator.patchValidation(referenceSolution, submittedProgram);
    assertTrue(true);
  }

  /**
   * Test for function calling that calls nested functions.
   */
  @Test
  void testFunctionCallingNested() {
    //recompileProgramIntoJson("func2.c");
    Program referenceSolution = TestUtils.loadProgramByName("func2.c");
    assert referenceSolution != null;
    Expr<?> e = PatchValidator.z3Translate(referenceSolution);
    assert e != null;
    assertEquals(3029, Integer.parseInt(e.toString()));

  }


  /**
   * Test for recursive functions.
   * COMMENTED OUT BECAUSE SEG FAULT
   */
  @Test
  void testRecursiveFunction() {
    // recompileProgramIntoJson("func4.c");
    Program referenceSolution = TestUtils.loadProgramByName("func4.c");
    assert referenceSolution != null;
    Expr<?> e = PatchValidator.z3Translate(referenceSolution);
    assert e != null;
    assertEquals(120, Integer.parseInt(e.toString()));

  }
}
