package sg.edu.nus.se.its.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static sg.edu.nus.se.its.validation.TestUtils.hasExpectedReturnValue;

public class StringTest {
  @Test
  void testSimpleStringReturnChar0() {
    String fileName = "simple_string_return_char.c";
//        new TestUtils.CProgramGenerator()
//                .addStatement("char a[] = \"Hello World\"")
//                        .addReturn("a", 0)
//                                .generateCProgram(fileName);
    assertTrue(hasExpectedReturnValue(fileName, "\"H\""));
  }

  @Test
  void testSimpleStringReturnChar5() {
    String fileName = "simple_string_return_char5.c";
//        new TestUtils.CProgramGenerator()
//                .addStatement("char a[] = \"Hello World\"")
//                        .addReturn("a", 5)
//                                .generateCProgram("char", fileName);
    assertTrue(hasExpectedReturnValue(fileName, "\" \""));
  }

  @Test
  void testSimpleStringReturnChar6() {
    String fileName = "simple_string_return_char6.c";
//        new TestUtils.CProgramGenerator()
//                .addStatement("char a[] = \"Hello World\"")
//                        .addReturn("a", 6)
//                                .generateCProgram("char", fileName);
    assertTrue(hasExpectedReturnValue(fileName, "\"W\""));
  }
}
