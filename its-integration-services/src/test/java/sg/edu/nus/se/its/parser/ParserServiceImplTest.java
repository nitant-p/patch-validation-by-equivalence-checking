package sg.edu.nus.se.its.parser;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import sg.edu.nus.se.its.model.Program;
import sg.edu.nus.se.its.util.TestUtils;

/**
 * Simple test class for the ParserService implementation.
 */
public class ParserServiceImplTest {

  @Test
  void test_arith_c() {
    String testFilePath =
        System.getProperty("user.dir") + "/../common-tests/basic_test/programs/c/arith.c";
    File testFile = new File(testFilePath);

    ParserServiceImpl parserService = new ParserServiceImpl();
    Program program = null;
    try {
      program = parserService.parse(testFile);
    } catch (IOException e) {
      fail("Unexpected exception during service call.", e);
    }

    String testModelPath =
        System.getProperty("user.dir") + "/../common-tests/basic_test/models/arith.c.json";
    Program referenceProgram = TestUtils.loadProgramByFilePath(testModelPath);
    TestUtils.programEquivalenceCheck(referenceProgram, program);
  }

  @Test
  void test_c1_py() {
    String testFilePath =
        System.getProperty("user.dir") + "/../common-tests/basic_test/programs/python/c1.py";
    File testFile = new File(testFilePath);

    ParserServiceImpl parserService = new ParserServiceImpl();
    Program program = null;
    try {
      program = parserService.parse(testFile);
    } catch (IOException e) {
      fail("Unexpected exception during service call.", e);
    }

    String testModelPath =
        System.getProperty("user.dir") + "/../common-tests/basic_test/models/c1.py.json";
    Program referenceProgram = TestUtils.loadProgramByFilePath(testModelPath);
    TestUtils.programEquivalenceCheck(referenceProgram, program);
  }

}
