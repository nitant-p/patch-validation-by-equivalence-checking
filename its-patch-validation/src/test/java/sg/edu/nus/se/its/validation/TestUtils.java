package sg.edu.nus.se.its.validation;

import com.microsoft.z3.Expr;
import sg.edu.nus.se.its.model.Program;
import sg.edu.nus.se.its.parser.ParserServiceImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class TestUtils extends sg.edu.nus.se.its.util.TestUtils {

  private final static String SOURCE = "../its-core/src/test/resources/source/";

  static boolean hasExpectedReturnValue(String fileName, Object expected) {
    recompileProgramIntoJson(fileName);
    Program referenceSolution = sg.edu.nus.se.its.util.TestUtils.loadProgramByName(fileName);
    assert referenceSolution != null;
    Expr<?> e = PatchValidator.z3Translate(referenceSolution);
    assert e != null;
    return expected.toString().equals(e.toString());
  }

  static boolean hasSameReturnValue(String refFileName, String subFileName) {
    recompileProgramIntoJson(refFileName);
    recompileProgramIntoJson(subFileName);
    PatchValidator validator = new PatchValidator();
    Program referenceSolution = sg.edu.nus.se.its.util.TestUtils.loadProgramByName(refFileName);
    Program submittedProgram = sg.edu.nus.se.its.util.TestUtils.loadProgramByName(subFileName);
    return validator.patchValidation(referenceSolution, submittedProgram);
  }

  static void recompileProgramIntoJson(String programName) {
    String testFilePath = "../its-core/src/test/resources/source/" + programName;

    File testFile = new File(testFilePath);
    if (testFile.exists() && !testFile.isDirectory()) {
      System.out.println("File exists: " + testFile.getAbsolutePath());

      String jsonFilePath = "../its-core/src/test/resources/model/" + programName + ".json";
      File jsonFile = new File(jsonFilePath);
      if (jsonFile.exists()) {
        System.out.println("\u001B[32m" + "JSON file already exists! ABORTING mwah :3" + "\u001B[0m");
        return;
      }

    } else {
      System.out.println("File does not exist: " + testFile.getAbsolutePath());
    }

    ParserServiceImpl parserService = new ParserServiceImpl();
    Program program = null;
    try {
      program = parserService.parse(testFile);
    } catch (IOException e) {
      System.out.println(e);
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

  static void sigsegv() {
    fail("Causes SIGSEGV");
  }

  public static void generateArrayProgram(String retType, String filename, List<?> values, String type, String operation) {
    System.out.println(values);
    try (PrintWriter writer = new PrintWriter(new FileWriter(SOURCE + filename))) {
      writer.println(retType + " main() {");
      writer.println(String.format("    %s a[" + values.size() + "];", type));
      for (int i = 0; i < values.size(); i++) {
        writer.println("    a[" + i + "] = " + (retType.equals("char") ? "\"" : "") + values.get(i) + (retType.equals("char") ? "\"" : "") + ";");
      }
      writer.println("    return " + operation + ";");
      writer.println("}");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static class CProgramGenerator {
    private final StringBuilder codeBuilder;
    private int arrayLength;

    public CProgramGenerator() {
      this.codeBuilder = new StringBuilder();
    }

    public static void main(String[] args) {
      new CProgramGenerator()
          .initialiseArray("int", "a", 3, 4, 5, 10, 11)
          .addForLoop("a", "120")
          .addReturn("a", 0)
          .generateCProgram("int", "generated_program.c");
      System.out.println("C program generated successfully!");
    }

    public CProgramGenerator initialiseArray(String type, String name, int... values) {
      arrayLength = values.length;
      codeBuilder.append("    ").append(type).append(" ").append(name).append("[] = {");
      for (int i = 0; i < values.length; i++) {
        codeBuilder.append(values[i]);
        if (i < values.length - 1) {
          codeBuilder.append(", ");
        }
      }
      codeBuilder.append("};\n");
      return this;
    }

    public CProgramGenerator addForLoop(String arrayName, String operation) {
      codeBuilder.append("    for(int i=0; i<").append(arrayLength).append("; i++) {\n");
      codeBuilder.append("        ").append(arrayName).append("[i] = ").append(operation).append(";\n");
      codeBuilder.append("    }\n");
      return this;
    }

    public CProgramGenerator addStatement(String statement) {
      codeBuilder.append("    ").append(statement).append(";\n");
      return this;
    }

    public CProgramGenerator addReturn(String arrayName, int index) {
      codeBuilder.append("    return ").append(arrayName).append("[").append(index).append("];\n");
      return this;
    }

    public void generateCProgram(String retType, String filename) {
      try (PrintWriter writer = new PrintWriter(new FileWriter(SOURCE + filename))) {
        writer.println(retType + " main() {");
        writer.println(codeBuilder);
        writer.println("}");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}