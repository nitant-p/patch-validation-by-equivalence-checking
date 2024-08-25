package sg.edu.nus.se.its.validation.translator;

import com.microsoft.z3.*;
import java.util.*;
import java.lang.RuntimeException;

import com.microsoft.z3.Expr;
import org.javatuples.Pair;
import sg.edu.nus.se.its.alignment.VariableMapping;
import sg.edu.nus.se.its.model.Expression;
import sg.edu.nus.se.its.validation.utils.VariableType;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.nonterminal.ArrayVariableZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.BoolVariableZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.FloatVariableZ3Expression;
import sg.edu.nus.se.its.validation.z3expression.terminal.IntVariableZ3Expression;

public class TranslationContext {
  private HashMap<String, Z3Expression> variableMap;
  private HashMap<String, VariableType> variableTypeMap;
  private HashMap<String, ArrayList<Z3Expression>> whileLoopBodies;
  private HashMap<String, ArrayList<Z3Expression>> forLoopBodies;
  private HashMap<String, Z3Expression> forLoopUpdates;
  private HashMap<String, ArrayList<Z3Expression>> whileLoopConditions;
  private HashMap<String, ArrayList<Z3Expression>> forLoopConditions;
  private HashMap<String, Expr[]> functionArgMap;
  private HashMap<String, ArrayList<String>> functionParamMap;
  private HashMap<String, ArrayList<Expr>> translatedExpressions;
  private ArrayList<String> functionNames;
  private final HashMap<String, Z3Expression> variableValue;
  private HashMap<Integer, Expr> scanfExpressions;
  private boolean isReference;
  private Expr returnConst;
  private HashMap<String, Integer> variableInit;
  private boolean hasScanf;

  public TranslationContext(HashMap<Integer, Expr> scanfExpressions, boolean isReference, boolean hasScanf) {
    this.variableMap = new HashMap<String, Z3Expression>();
    this.variableValue = new HashMap<String, Z3Expression>();
    this.variableTypeMap = new HashMap<String, VariableType>();
    this.whileLoopBodies = new HashMap<String, ArrayList<Z3Expression>>();
    this.forLoopBodies = new HashMap<String, ArrayList<Z3Expression>>();
    this.whileLoopConditions = new HashMap<String, ArrayList<Z3Expression>>();
    this.forLoopConditions = new HashMap<String, ArrayList<Z3Expression>>();
    this.forLoopUpdates = new HashMap<String, Z3Expression>();
    this.functionArgMap = new HashMap<String, Expr[]>();
    this.functionParamMap = new HashMap<String, ArrayList<String>>();
    this.translatedExpressions = new HashMap<String, ArrayList<Expr>>();
    this.functionNames = new ArrayList<String>();
    this.scanfExpressions = scanfExpressions;
    this.isReference = isReference;
    this.variableInit = new HashMap<>();
    this.hasScanf = hasScanf;
  }

  public HashMap<String, Z3Expression> getVariableMap() {
      return this.variableMap;
  }

  public void storeVariable(String variableName, Z3Expression z3Expression) {
    // variableName = isReference ? "REF_" + variableName : "SUB_" + variableName;
    if (!variableMap.containsKey(variableName)) {
      this.variableMap.put(variableName, z3Expression);
    }
  }

  public void storeFunction(String functionName, Expr[] argList) {
    if (!functionArgMap.containsKey(functionName)) {
      this.functionArgMap.put(functionName, argList);
    } else {
      // replace old one with new one
      this.functionArgMap.replace(functionName, argList);
    }
  }

  public Expr[] getArgList(String functionName) {
    return this.functionArgMap.get(functionName);
  }

  public Z3Expression getVariableZ3Expression(String variableName) {
    return this.variableMap.get(variableName);
  }

  public void addAssignment(String name, Z3Expression value){
    this.variableValue.put(name, value);
    System.out.println("NAME: "+ name + ", VALUE: " + value);
  }

  public Z3Expression getValue(String name){
    return this.variableValue.get(name);
  }

  public HashMap<String, Z3Expression> getValueMap(){
    return this.variableValue;
  }

  public VariableType getVariableType(String variableName) {
    return this.variableTypeMap.get(variableName);
  }

  public HashMap<String, VariableType> getTypeMap(){
    return this.variableTypeMap;
  }

  public void storeForLoopBody(String loopName, ArrayList<Z3Expression> loopBody) {
    if (!this.forLoopBodies.containsKey(loopName)) {
      this.forLoopBodies.put(loopName, loopBody);
    } else {
      this.forLoopBodies.get(loopName).addAll(loopBody);
    }
  }

  public void storeForLoopCondition(String loopName, List<Z3Expression> condition) {
    this.forLoopConditions.put(loopName, (ArrayList<Z3Expression>) condition);
  }

  public ArrayList<Z3Expression> getForLoopCondition(String loopName) {
    return this.forLoopConditions.get(loopName);
  }

  public ArrayList<Z3Expression> getForLoopBody(String loopName) {
    return this.forLoopBodies.get(loopName);
  }

  public void storeWhileLoopBody(String loopName, ArrayList<Z3Expression> loopBody) {
    if (!this.whileLoopBodies.containsKey(loopName)) {
      this.whileLoopBodies.put(loopName, loopBody);
    } else {
      this.whileLoopBodies.get(loopName).addAll(loopBody);
    }
  }

  public void storeWhileLoopCondition(String loopName, ArrayList<Z3Expression> condition) {
    this.whileLoopConditions.put(loopName, condition);
  }

  public ArrayList<Z3Expression> getWhileLoopCondition(String loopName) {
    return this.whileLoopConditions.get(loopName);
  }

  public ArrayList<Z3Expression> getWhileLoopBody(String loopName) {
    return this.whileLoopBodies.get(loopName);
  }

  public void storeVariableType(String variableName, String variableType) {
    // variableName = isReference ? "REF_" + variableName : "SUB_" + variableName;
    VariableType variableTypeEnum;
    System.out.println(variableName + " " + variableType);
    if (variableType.equals("int")) {
      variableTypeEnum = VariableType.INTEGER;
    } else if (variableType.equals("float")) {
      variableTypeEnum = VariableType.FLOAT;
    } else if (variableType.equals("bool")) {
      variableTypeEnum = VariableType.BOOL;
    } else if (variableType.equals("String") || variableType.equals("char")) {
      variableTypeEnum = VariableType.STRING;
    } else if (variableType.matches("whileLoop\\d+")) {
        variableTypeEnum = VariableType.BOOL;
    } else if (variableType.equals("int[]")) {
        variableTypeEnum = VariableType.INT_ARRAY;
    } else if (variableType.matches("forLoop\\d+")) {
      variableTypeEnum = VariableType.BOOL;
    } else if (variableType.matches(".+\\[]")) {
      String baseType = variableType.substring(0, variableType.length() - 2); // Remove []
      switch (baseType) {
      case "int":
        variableTypeEnum = VariableType.INT_ARRAY;
        break;
      case "char":
        variableTypeEnum = VariableType.CHAR_ARRAY;
        break;
      case "float":
        variableTypeEnum = VariableType.FLOAT_ARRAY;
        break;
      case "double":
        variableTypeEnum = VariableType.DOUBLE_ARRAY;
        break;
      case "short":
        variableTypeEnum = VariableType.SHORT_ARRAY;
        break;
      case "long":
        variableTypeEnum = VariableType.LONG_ARRAY;
        break;
      case "boolean":
        variableTypeEnum = VariableType.BOOLEAN_ARRAY;
        break;
      case "byte":
        variableTypeEnum = VariableType.BYTE_ARRAY;
        break;
      default:
        throw new RuntimeException(
                "TranslationContext::storeVariableType - variable type (" +
                        variableType +
                        ") is unknown");
      }
    } else {
      variableTypeEnum = null;
      throw new RuntimeException(
          "TranslationContext::storeVariableType - variable type (" + 
          variableType +
          ") is unknown");
    }
    // } else if (variableType == "string")
    // } else if (variableType == "bool")

    this.variableTypeMap.put(variableName, variableTypeEnum);
  }

  public void storeFunctionParams(String name, ArrayList<String> argList) {
    if (!functionParamMap.containsKey(name)) {
      this.functionParamMap.put(name, argList);
    }
  }

  public void storeFunctionExpressions(HashMap<String, ArrayList<Expr>> translatedExpressions) {
    this.translatedExpressions = translatedExpressions;
  }

  public HashMap<String, ArrayList<Expr>> getTranslatedExpressions() {
    return this.translatedExpressions;
  }

  public void storeFunctionNames(ArrayList<String> functionNames) {
    this.functionNames = functionNames;
  }

  public ArrayList<String> getFunctionNames() {
    return this.functionNames;
  }

  /**
   * USED WHEN I AM INSIDE THE EXPRESSION - SINCE MY REGEX IS SO WONKY I MANUALLY SAVE THE ARGUMENTS OF THE FUNCTION
   * @param functionName
   * @param argList
   */
  public void storeFunctionArg(String functionName, ArrayList<String> argList) {
    if (!functionParamMap.containsKey(functionName)) {
      this.functionParamMap.put(functionName, argList);
      System.out.println("Function " + functionName + " has arguments: " + argList + " stored in TranslationContext");
    }
  }

  public ArrayList<String> getFunctionArg(String functionName) {
    return this.functionParamMap.get(functionName);
  }

  private ArrayList<String> extractArguments(String expression) {
    String[] argumentsArray = expression.split("\\(")[1].split("\\)")[0].split(",\\s*");
    ArrayList<String> argList = new ArrayList<>();

    for (String argument : argumentsArray) {
      if (argument.startsWith("\"") && argument.endsWith("\"")) {
        argument = argument.substring(1, argument.length() - 1);
      }
      argList.add(argument);
    }

    return argList;
  }

  public void processExpression(String expression) {
   ArrayList<String> functionNames = this.getFunctionNames();
    for (String functionName : functionNames) {
      // Check if the expression string contains the function name
      String expressionStr = expression.toString();
      String[] exprStrSplit = expressionStr.split("\\(");
      System.out.println("Expression string: " + exprStrSplit[0]);
      if (expressionStr.contains(functionName) && exprStrSplit[0].replace(")", "").equals(functionName)) {

        System.out.println("Function name: " + functionName + " found in expression: " + expressionStr);
        // i want to put into the translation context manually what is the arguments

        // get the things in the brackets
        String[] argumentsArray = expressionStr.split("\\(")[1].split("\\)")[0].split(",\\s*");
        ArrayList<String> argList = new ArrayList<>();
        for (String argument : argumentsArray) {
          // Remove quotes if argument is a string literal
          if (argument.startsWith("\"") && argument.endsWith("\"")) {
            argument = argument.substring(1, argument.length() - 1);
          }
          argList.add(argument);
        }
        System.out.println("Function name: " + functionName + " argList: " + argList);
        this.storeFunctionArg(functionName, argList);
      }
    }
  }

  public void trackVariables(Pair<String, Expression> expressionPair) {
    String variableName = expressionPair.getValue0();
    // variableName = isReference ? "REF_" + variableName : "SUB_" + variableName;
    VariableType variableType = this.getVariableType(variableName);
    Z3Expression variableZ3Expression = variableType.getVariableZ3Expression(variableName);
    this.storeVariable(variableName, variableZ3Expression);
  }

  public void storeForLoopUpdate(String loopName, ArrayList<Z3Expression> expressionTree) {
    this.forLoopUpdates.put(loopName, expressionTree.get(0));
  }

  public Z3Expression getForLoopUpdate(String loopName) {
    return this.forLoopUpdates.get(loopName);
  }

    public void addVariableInit(String expressionName, Z3Expression val) {
    try {
      variableInit.putIfAbsent(expressionName, Integer.parseInt(val.interpret(this, new Context()).toString()));
    } catch (NumberFormatException e) {
      System.out.println(e);
    }
    }

    public int getVariableInit(String expressionName) {
    return variableInit.get(expressionName);
    }
  public HashMap<String, Expr[]> getFunctionArgMap() {
    return this.functionArgMap;
  }

  /**
   * Returns the scanf Expr corresponding to the input number.
   * @param inputNum The index of the scanf Expr.
   * @return The scanf Expr.
   */
  public Expr getScanfInputExpr(Integer inputNum) {
    return this.scanfExpressions.get(inputNum);
  }

  /**
   * Returns the boolean value for if the program is reference or fixed/sub program.
   * @return True if program is reference, false if not.
   */
  public boolean getIsReference() {
    return this.isReference;
  }

  /**
   * Setter for the Expr corresponding to the program's return expression.
   * @param expr The Expr object.
   */
  public void setReturn(Expr expr) {
    this.returnConst = expr;
  }

  /**
   * Getter for the Expr corresponding to the program's return expression.
   * @return The Expr object.
   */
  public Expr getReturn() {
    return this.returnConst;
  }

  /**
   * Returns the boolean value for if the program has scanf inputs or not.
   * @return True if the program has scanf inputs, false if not.
   */
  public boolean getHasScanf() {
    return this.hasScanf;
  }

}
