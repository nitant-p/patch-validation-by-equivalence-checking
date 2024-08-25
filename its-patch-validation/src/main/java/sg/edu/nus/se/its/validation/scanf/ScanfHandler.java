package sg.edu.nus.se.its.validation.scanf;

import com.microsoft.z3.*;
import java.util.*;
import org.javatuples.Pair;
import sg.edu.nus.se.its.validation.utils.VariableType;
import sg.edu.nus.se.its.model.*;

/**
 * Class to handle programs with scanf input.
 */
public class ScanfHandler {
  private Program refProgram;
  private Program fixedProgram;
  private boolean programsHaveScanf;
  private HashMap<Integer, Expr> scanfExpressions;
  private Context ctx;

  /**
   * Constructor for ScanfHandler.
   * @param ctx The Z3 context object.
   */
  public ScanfHandler(Context ctx) {
    this.ctx = ctx;
  }

  /**
   * Initialises the reference program and fixed program.
   * @param refProgram Reference program.
   * @param fixedProgram Fixed program.
   */
  public void initialise(Program refProgram, Program fixedProgram) {
    this.refProgram = refProgram;
    this.fixedProgram = fixedProgram;
    this.scanfExpressions = new HashMap<>();
    this.programsHaveScanf = this.checkProgramHasScanf(refProgram);
    if (programsHaveScanf) {
      this.initialiseScanfExpressions();
    }
  }

  /**
   * Checks if a program has scanf inputs.
   * @param program The program.
   * @return True if the program has scanf inputs, false if not.
   */
  private boolean checkProgramHasScanf(Program program) {
    Map<String, Function> functions = program.getFncs();
    for (Function function : functions.values()) {
      HashMap<Integer, ArrayList<Pair<String, Expression>>> locexprs = function.getLocexprs();
      for (ArrayList<Pair<String, Expression>> locexpr : locexprs.values()) {
        for (Pair<String, Expression> expressionPair : locexpr) {
          String expressionName = expressionPair.getValue0();
          if (expressionName.equals("$in")) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Returns the programsHaveScanf boolean.
   * @return The programsHaveScanf boolean value.
   */
  public boolean doProgramsHaveScanf() {
    return this.programsHaveScanf;
  }

  /**
   * Initialise the scanf expressions of each program.
   */
  private void initialiseScanfExpressions() {
    initialiseSingleProgScanfExpressions(refProgram);
    initialiseSingleProgScanfExpressions(fixedProgram);
  }

  /**
   * Initialise the scanf expressions of a Program.
   */
  private void initialiseSingleProgScanfExpressions(Program program) {
    Map<String, Function> functions = program.getFncs();
    for (Function function : functions.values()) {
      HashMap<Integer, ArrayList<Pair<String, Expression>>> locexprs = function.getLocexprs();
      for (ArrayList<Pair<String, Expression>> locexpr : locexprs.values()) {
        for (Pair<String, Expression> expressionPair : locexpr) {
          Expression expression = expressionPair.getValue1();
          String expressionStr = expression.toString();
          if (expressionStr.startsWith("ListHead(")) {
            int count = 0;
            int index = expressionStr.indexOf("ListTail(");
            while (index != -1) {
              count++;
              index = expressionStr.indexOf("ListTail(", index + 1);
            }
            String type = expressionStr.substring(9, expressionStr.indexOf(","));
            if (type.equals("int")) {
              this.scanfExpressions.put(count, ctx.mkIntConst("input" + Integer.toString(count)));
            }
          }
        }
      }
    }
  }

  /**
   * Returns the scanfExpressions Map.
   * @return The scanfExpression map.
   */
  public HashMap<Integer, Expr> getScanfExpressions() {
    return this.scanfExpressions;
  }
}
