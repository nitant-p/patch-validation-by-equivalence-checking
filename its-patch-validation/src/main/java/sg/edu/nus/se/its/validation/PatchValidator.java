package sg.edu.nus.se.its.validation;

import com.microsoft.z3.*;
import org.javatuples.Pair;
import sg.edu.nus.se.its.model.Function;
import sg.edu.nus.se.its.model.Program;
import sg.edu.nus.se.its.validation.scanf.ScanfHandler;
import sg.edu.nus.se.its.validation.translator.TranslationHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Verification module based on program equivalence checking.
 */
public class PatchValidator {
  private static Logger LOGGER = null;
  private final Context ctx;
  private final ScanfHandler scanfHandler;
  private Expr referenceReturn = null;
  private Expr subReturn = null;

  static {
    InputStream stream = PatchValidator.class.getClassLoader().
            getResourceAsStream("logging.properties");
    try {
      LogManager.getLogManager().readConfiguration(stream);
      LOGGER=Logger.getLogger(PatchValidator.class.getName());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Constructor for PatchValidator.
   */
  public PatchValidator() {
    this.ctx = new Context();
    this.scanfHandler = new ScanfHandler(ctx);
  }

  /**
   * Validates if a fixed program differs from a reference program using Z3 solver.
   *
   * @param referenceProgram The reference program to compare against.
   * @param fixedProgram The fixed program to be validated.
   * @return {@code true} if the fixed program is proven to be different from the reference program,
   *         {@code false} if either the programs are aligned or if a counterexample is found.
   */
  public boolean patchValidation(Program referenceProgram, Program fixedProgram) {
    try {
      boolean functionsAreEquivalent = this.validateFunctions(referenceProgram, fixedProgram);
      return functionsAreEquivalent;
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
      return false;
    }
  }

  /**
   * Translate the program to Z3 constraints.
   * @param program The program to be translated.
   * @return The Z3 expression of the program.
   */
  public static Expr<?> z3Translate(Program program) {
    PatchValidator pV = new PatchValidator();
    return pV.getReturnValue(pV.getFunctionExprsType(program, true));
  }

  /**
   * Get the function expressions and return type of the caller function in the program.
   * @param program
   * @return Pair of list of expressions and return type of the caller function
   */
  private Pair<List<Expr>, String> getFunctionExprsType(Program program, boolean isReference) {
    Map<String, Function> functions = program.getFncs();
    ArrayList<Function> programFunctions = new ArrayList<>();
    ArrayList<String> functionNames = new ArrayList<>(); // this is for detecting function calls in the expressions
    for (String key : functions.keySet()) {
      programFunctions.add(functions.get(key));
      functionNames.add(key);
    }

    TranslationHandler referenceTranslationHandler = new TranslationHandler(ctx, scanfHandler.getScanfExpressions(), isReference, scanfHandler.doProgramsHaveScanf());
    referenceTranslationHandler.storeFunctionNames(functionNames);

    if (programFunctions.size() > 1) {
      return processMultipleFunctions(programFunctions, referenceTranslationHandler, ctx, program);
    }

    Pair<List<Expr>, String> p = new Pair<>(referenceTranslationHandler.translateFunctionToZ3Constraints(programFunctions.get(0)), programFunctions.get(0).getRettype());
    Expr returnConst = referenceTranslationHandler.getReturn();
    if (isReference) {
      this.referenceReturn = returnConst;
    } else {
      this.subReturn = returnConst;
    }
    return p;
  }

  /**
   * Process multiple functions in the program.
   * @param programFunctions
   * @param referenceTranslationHandler
   * @param ctx
   * @param program
   * @return
   */
  private Pair<List<Expr>, String> processMultipleFunctions(List<Function> programFunctions, TranslationHandler referenceTranslationHandler, Context ctx, Program program) {

    // has map of functionName to list of translated expressions
    HashMap<String, ArrayList<Expr>> translatedExpressions = new HashMap<>();


    for (Function function : programFunctions) {
      translatedExpressions.put(function.getName(), referenceTranslationHandler.translateFunctionToZ3Constraints(function)); // regex issues: func(10) is read as FunctionToken BUT func(i) IS NOT
    }

    System.out.println(translatedExpressions + " ____ this is the translatedExpression");

    // caller
    ArrayList<Expr> mainExpr = translatedExpressions.get("main");
    System.out.println("Main expression: " + mainExpr);
    /**
     * I need to have multiple of this
     */
    ArrayList<Expr> combinedMainExpr = new ArrayList<>();
    combinedMainExpr.addAll(mainExpr);

    // for each callee, i will get its return value
    for (Expr expr : mainExpr) {
      ArrayList<Expr> calleeExpr = new ArrayList<>();
      /**
       * Purpose: currently i am assuming that one Expr only has one function call
       * The thing is it is possible to have things like func(3) + func(5)
       *
       */
      String exprStr = expr.toString();
      String[] exprStrSplit = exprStr.split(" ");
      for (String key : translatedExpressions.keySet()) {
        String functionName = exprStrSplit[2];
        if (exprStr.contains(key) && exprStrSplit[2].replace(")", "").equals(key)) {
          System.out.println("Expression '" + expr + "' contains a key " + key + " from translatedExpressions.");
//          ArrayList<Token> tokens = Tokenizer.tokenize(expr.toString());
//          for (int i = 0; i < tokens.size() -1; i++) {
//            Token t = tokens.get(i);
//            if (t instanceof VariableToken && t.getValue().equals(key)) {
//              // this is a function
//              System.out.println("Token: " + t.getValue());
//              Token arg = tokens.get(i + 1);
//              functionCalls.add(new Pair<>(key, arg));
//            }
//          }
          String[] exprParts = exprStr.split(" ");
          String caller = exprParts[1]; // TODO: This is a hacky way to get the caller
          System.out.println("Caller: " + caller);

          ArrayList<Expr> functionExprs = translatedExpressions.get(key);
          combinedMainExpr.remove(expr);
          calleeExpr.addAll(functionExprs);

          ArrayList<Pair<String, String>> params = program.getFunctionForName(key).getParams();

          /**
           * argList - consider type, size
           */
          String argString = referenceTranslationHandler.getFunctionArg(key).get(0).replace("'", "");
          Expr paramExpr = ctx.mkIntConst(params.get(0).getValue0());
          System.out.println("ParamExpr: " + paramExpr);
          Expr argExpr;
          if (argString.matches("-?\\d+")) {
            // if literal integer value
            argExpr = ctx.mkInt(Integer.parseInt(argString));
            calleeExpr.add(ctx.mkEq(paramExpr, argExpr));

          } else {
            // if variable
            argExpr = ctx.mkIntConst(argString);
            System.out.println("ArgExpr: " + argExpr);
            calleeExpr.add(ctx.mkEq(paramExpr, argExpr));
            // find the assignment of the value in the main function
            // and add it to the calleeExpr
            for (Expr mainExprs : combinedMainExpr) {
              if (mainExprs.toString().contains(argString)) {
                calleeExpr.add(mainExprs);
              }
            }

          }

          Solver solver = ctx.mkSolver();
          for (Expr callee : calleeExpr) {
            if (callee.getSort() instanceof BoolSort) {
              Expr<BoolSort> boolExpr = (Expr<BoolSort>) callee;
              solver.add(boolExpr);
            } else {
              LOGGER.finer("non boolsort");
              LOGGER.finer("usecase: return value is an integer");
              LOGGER.finer("expr: " + callee);
              // throw new RuntimeException("non-boolsort Expr is being evaluated");
            }
          }
          Status result = solver.check();
          if (result == Status.SATISFIABLE) {
            Model model = solver.getModel();
            Expr<IntSort> ret = ctx.mkIntConst("$ret");
            Expr<IntSort> bValue = model.evaluate(ret, false);
            System.out.println("Value of $ret: " + bValue);
            combinedMainExpr.add(ctx.mkEq(ctx.mkIntConst(caller), bValue));
            System.out.println("Combined main expression: " + combinedMainExpr);
          } else {
            throw new RuntimeException("PatchValidator::getReturnValue - Single exprList model unsatisfiable");
          }
          break; // TODO: this assumes one expr only has one function call
        }
      }
    }

    return new Pair<>(combinedMainExpr, programFunctions.get(programFunctions.size() - 1).getRettype());
  }

  /**
   * Validate the functions of the reference and fixed programs.
   * Generate functionExprs for each program and generate a solver to check if the return values are equivalent.
   * @param referenceProgram
   * @param fixedProgram
   * @return
   */
  private boolean validateFunctions(Program referenceProgram, Program fixedProgram) {
      scanfHandler.initialise(referenceProgram, fixedProgram);
      HashMap<Integer, Expr> scanfExpressions = scanfHandler.getScanfExpressions();
      Pair<List<Expr>, String> referenceExprs = this.getFunctionExprsType(referenceProgram, true);
      Pair<List<Expr>, String> fixedExprs = this.getFunctionExprsType(fixedProgram, false);

      Solver solver = this.generateSolver(referenceExprs, fixedExprs);
      Solver solverOutput = this.generateOutputSolver(referenceExprs, fixedExprs);

      if (scanfHandler.doProgramsHaveScanf()) {
          return scanfEvaluate(referenceExprs, fixedExprs);
      }

      if (solver == null && solverOutput == null) {
      // there is no output and return value
      LOGGER.info("There is no output and return value found");
      return false;
    } else if (solver == null) {
      if (solverOutput.check() == Status.UNSATISFIABLE) {
        // output values are equivalent
        LOGGER.info("output values are equivalent");
        return true;
      } else {
        LOGGER.info("output values are not equivalent");
        return false;
      }
    } else {
      if (solver.check() == Status.UNSATISFIABLE) {
        LOGGER.info("return values are Semantically equivalent");
        return true;
      } else {
        LOGGER.info("Return value Not semantically equivalent");
        return false;
      }
    }
  }

  /**
   * Get the return value of the function.
   * @param exprReturn Pair of list of expressions and return type of the function.
   * @return Expr of return value.
   * @throws RuntimeException When return type is unsupported OR single exprList model unsatisfiable.
   */
  private Expr getReturnValue(Pair<List<Expr>, String> exprReturn) throws RuntimeException {
    Solver solver = ctx.mkSolver();
    List<Expr> exprList = exprReturn.getValue0();
    Set<String> visited = new HashSet<>();
    String returnType = exprReturn.getValue1();
//    System.out.println(exprList);
    for (Expr expr : exprList) {
      if (expr.getSort() instanceof BoolSort && !visited.contains(((Expr<BoolSort>)expr).toString())) {
        Expr<BoolSort> boolExpr = (Expr<BoolSort>) expr;
        System.out.println(boolExpr);
        solver.add(boolExpr);
        visited.add(boolExpr.toString());
//        System.out.println(visited);
      }
//      } else {
//        LOGGER.finer("non boolsort");
//        LOGGER.finer("usecase: return value is an integer");
//        LOGGER.finer("expr: " + expr);
//        return expr;
//      }
    }
    Status result = solver.check();
    if (result == Status.UNSATISFIABLE) {
      Model model = solver.getModel();
    switch (returnType) {
      case "int": {
        Expr<IntSort> ret = ctx.mkIntConst("$ret");
        Expr<IntSort> bValue = model.evaluate(ret, false);
        return bValue;
      }
      case "bool": {
        Expr<BoolSort> ret = ctx.mkBoolConst("$ret");
        Expr<BoolSort> bValue = model.evaluate(ret, false);
        return bValue;
      }
      case "float": {
        Expr<RealSort> ret = ctx.mkRealConst("$ret");
        Expr<RealSort> bValue = model.evaluate(ret, false);
        return bValue;
      }
      default:
        throw new RuntimeException(
                "PatchValidator::getReturnValue - The return type (" + returnType + ") is unsupported.");
    }
  }

    if (result == Status.SATISFIABLE) {
      Model model = solver.getModel();
      switch (returnType) {
      case "int": {
        Expr<IntSort> ret = ctx.mkIntConst("$ret");
        Expr<IntSort> bValue = model.evaluate(ret, false);
        return bValue;
      }
      case "bool": {
        Expr<BoolSort> ret = ctx.mkBoolConst("$ret");
        Expr<BoolSort> bValue = model.evaluate(ret, false);
        return bValue;
      }
      case "float": {
        Expr<RealSort> ret = ctx.mkRealConst("$ret");
        Expr<RealSort> bValue = model.evaluate(ret, false);
        return bValue;
      }
      case "char": {
        Expr<SeqSort<CharSort>> ret = ctx.mkConst("$ret", ctx.getStringSort());
        Expr<SeqSort<CharSort>> bValue = model.evaluate(ret, false);
        return bValue;
      }
      default:
        throw new RuntimeException(
                "PatchValidator::getReturnValue - The return type (" + returnType + ") is unsupported.");
    }
  } else {
    throw new RuntimeException("PatchValidator::getReturnValue - Single exprList model unsatisfiable");
  }
}


/**
 * Generate a solver for the two exprlists.
 * @param ref reference ExprList
 * @param fix fixed ExprList
 * @return Solver
 */
private Solver generateSolver(Pair<List<Expr>, String> ref, Pair<List<Expr>, String> fix) {
  LOGGER.info("exprList1 to solve: " + ref.getValue0());
  LOGGER.info("exprList2 to solve: " + fix.getValue0());
  Solver solver = ctx.mkSolver();
  Expr value1 = this.getReturnValue(ref);
  LOGGER.info("Return value of exprList1: " + value1);
  LOGGER.info("Return class of exprList1: " + value1.getClass());
  Expr value2 = this.getReturnValue(fix);
  LOGGER.info("Return value of exprList2: " + value2);
  LOGGER.info("Return class of exprList2: " + value2.getClass());

  Expr<IntSort> ret = ctx.mkIntConst("$ret");
  if (value1.equals(ret) && value2.equals(ret)){
    System.out.println("ret is null");
    return null;
  } else {
    solver.add(ctx.mkNot(ctx.mkEq(value1, value2)));
  }

  LOGGER.info("Overall out solver for both exprlists: " +  solver);
  return solver;
}

/**
 * Generate a solver for the two exprlists.
 * @param ref reference ExprList
 * @param fix fixed ExprList
 * @return Solver
 */
private Solver generateOutputSolver(Pair<List<Expr>, String> ref, Pair<List<Expr>, String> fix) {
  LOGGER.info("exprList1 to solve: " + ref.getValue0());
  LOGGER.info("exprList2 to solve: " + fix.getValue0());
  Solver solver = ctx.mkSolver();

  //Output value
  Expr output1 = this.getOutputValue(ref);
  Expr output2 = this.getOutputValue(fix);

  BoolExpr constraint;
  if (output1 == null || output2 == null){
    return null;
  } else {
    solver.add(ctx.mkNot(ctx.mkEq(output1, output2)));
  }

  //outputSolver.add(ctx.mkNot(ctx.mkEq(output1, output2)));
  LOGGER.info("Overall out solver for both exprlists: " +    solver);

  return solver;
}

    /**
     * Evaluates two exprlists that contain scanf operations.
     * @param referenceExprs Reference expressions.
     * @param fixedExprs Fixed expressions.
     * @return result of the evaluation.
     */
    private boolean scanfEvaluate(Pair<List<Expr>, String> referenceExprs, Pair<List<Expr>, String> fixedExprs) {
        Solver solver = ctx.mkSolver();
        List<Expr> referenceExprList = referenceExprs.getValue0();
        List<Expr> fixedExprList = fixedExprs.getValue0();
        int size = referenceExprList.size();
        for (int i = 0; i < size; i++) {
            solver.add(referenceExprList.get(i));
            solver.add(fixedExprList.get(i));
        }
        solver.add(ctx.mkNot(ctx.mkEq(referenceReturn, subReturn)));
        Status status = solver.check();
        if (status == Status.UNSATISFIABLE) {
            return true;
        }
        return false;
    }    

private Expr getOutputValue(Pair<List<Expr>, String> exprReturn) throws RuntimeException {
  // Extract $out from the list of expressions
  List<Expr> exprList = exprReturn.getValue0();
  for (Expr expr : exprList) {
    if (expr.toString().contains("$out")) {
      System.out.println("Expression: " + expr.toString());
      return expr;
    }
  }
  return null;
  //throw new RuntimeException("$out not found in the list of expressions");
}
}