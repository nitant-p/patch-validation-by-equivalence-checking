//package sg.edu.nus.se.its.validation;
//
//import org.junit.jupiter.api.Test;
//import sg.edu.nus.se.its.model.Program;
//import sg.edu.nus.se.its.parser.ParserServiceImpl;
//import sg.edu.nus.se.its.util.TestUtils;
//
//import java.io.File;
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
///**
// * Unit test for integer return statements.
// * Includes: Return integers
// * Does not include: Return variables (return a) or return expressions (return a + b)
// */
//public class ReturnIntegerTest {
//
//  private static void recompileProgramIntoJson(String programName) {
//    String testFilePath = "../its-core/src/test/resources/source/" + programName;
//
//    File testFile = new File(testFilePath);
//    if (testFile.exists() && !testFile.isDirectory()) {
//      System.out.println("File exists: " + testFile.getAbsolutePath());
//    } else {
//      System.out.println("File does not exist: " + testFile.getAbsolutePath());
//    }
//
//    ParserServiceImpl parserService = new ParserServiceImpl();
//    Program program = null;
//    try {
//      program = parserService.parse(testFile);
//    } catch (IOException e) {
//      fail("Unexpected exception during service call.", e);
//    }
//
//    String filePath = "../its-core/src/test/resources/model/" + programName + ".json";
//
//    boolean success = TestUtils.storeProgramAsJsonFile(program, filePath);
//
//    if (success) {
//      System.out.println("Program " + programName + " was successfully saved to JSON.");
//    } else {
//      System.out.println("Failed to save the Program to JSON.");
//    }
//
//  }
//
//  /**
//   * Test both programs that return same integer values.
//   * ret1: return 0
//   * ret1: return 0
//   * expected result: 0 === 0 => true
//   */
//  @Test
//  void testReturnIntegerSameValue() {
//    //recompileProgramIntoJson("ret1.c");
//    PatchValidator validator = new PatchValidator();
//    Program returnZero = TestUtils.loadProgramByName("ret1.c");
//    Program returnZero2 = TestUtils.loadProgramByName("ret1.c");
//    boolean result = validator.patchValidation(returnZero, returnZero2);
//    assertTrue(result);
//  }
//
//  /**
//   * Test both programs that return different integer values.
//   * ret1: return 0
//   * ret2: return 1
//   * expected result: 0 !== 1 => false
//   */
//  @Test
//  void testReturnIntegerDiffValue() {
//    //recompileProgramIntoJson("ret2.c");
//    // recompileProgramIntoJson("ret1.c");
//    PatchValidator validator = new PatchValidator();
//    Program returnZero = TestUtils.loadProgramByName("ret1.c");
//    Program returnOne = TestUtils.loadProgramByName("ret2.c");
//    boolean result = validator.patchValidation(returnOne, returnZero);
//    assertFalse(result);
//  }
//
//  /**
//   * Test assignment
//   */
//  @Test
//  void testReturnVariableSameValue() {
//    //recompileProgramIntoJson("ret0.c");
//    PatchValidator validator = new PatchValidator();
//    Program returnZero = TestUtils.loadProgramByName("ret0.c");
//    Program returnZero2 = TestUtils.loadProgramByName("ret2.c");
//    boolean result = validator.patchValidation(returnZero, returnZero2);
//    assertTrue(result);
//  }
//
//
//
//
//
//
//
//
//}