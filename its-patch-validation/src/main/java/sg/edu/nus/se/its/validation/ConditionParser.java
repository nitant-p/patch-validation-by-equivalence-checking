package sg.edu.nus.se.its.validation;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.IntExpr;

import java.util.HashMap;
import java.util.regex.Pattern;

public class ConditionParser {


    private final Context ctx;
    private HashMap<String, String> variableMap = new HashMap<>();

    private String leftBranch;
    private String rightBranch;
    private String variableToSet;
    private BoolExpr completeCondition;
    private String fullExprString;
    private String exprWithoutIte;
    private static final String BOOLEAN_LITERAL_FLAG = "TrueOrFalse";

    public ConditionParser(Context ctx, HashMap<String, String> variableMap) {
        this.ctx = ctx;
        this.variableMap = variableMap;
    }

    public String[] extractLeftandRightSubbranches(String input) {
        System.out.println("L+R: "  + input);
        String left = "";
        String right = "";
        String literalBoolIndicator = "";

        // first check for any literal true or false values
        String[] splitUp = input.split(",");
        // right branch here is the very last in the splitUp array
        int rightIndex = splitUp.length - 1;

        if (isNumericString(splitUp[0].trim()) || isNumericString(splitUp[rightIndex].trim())) {
            if (isNumericString(splitUp[0])) {
                left = splitUp[0].trim();
                literalBoolIndicator += "L";
            }
            if (isNumericString(splitUp[rightIndex])) {
                right = splitUp[rightIndex].trim();
                literalBoolIndicator += "R";
            }

            if (literalBoolIndicator.equals("L")) {
                // get right
                String leftRemoved = input.substring(left.length() + 1).trim(); // + 1 to include comma
                right = getFirstPairBracket(leftRemoved);
            } else if (literalBoolIndicator.equals("R")) {
                // just get left
                left = getFirstPairBracket(input);
            }
            System.out.println("LEFT: " + left);
            System.out.println("RIGHT: " + right);
            return new String[]{left.toString(), right, literalBoolIndicator};
        }

        left = getFirstPairBracket(input);

        // up to comma
        right = input.substring(left.length() + 1).trim();

        System.out.println("LEFT: " + left);
        System.out.println("RIGHT: " + right);
        return new String[]{left.toString(), right, literalBoolIndicator};
    }

    // given a string, this method returns the first pair of brackets. e.g.
    private String getFirstPairBracket(String input) {
        int brackCounter = 0;
        boolean balanced = false;
        boolean firstBracketFound = false;
        int charIndex = 0;
        StringBuilder left = new StringBuilder();
        while (!balanced) {
            if (input.charAt(charIndex) == '(') {
                firstBracketFound = true;
                brackCounter++;
            } else if (input.charAt(charIndex) == ')') {
                brackCounter--;
            }
            if (brackCounter == 0 && firstBracketFound) balanced = true;
            left.append(input.charAt(charIndex));
            charIndex++;
        }
        return left.toString();
    }

    private String getFirstOperator(String input) {
        String firstTwo = input.substring(0, 2);
        if (firstTwo.endsWith("(")) { // then dealing with >( or <( so need to adjust
            return String.valueOf(firstTwo.charAt(0));
        }
        return input.substring(0, 2);
    }

    private Expr[] constructVariableorConstantExpr(String expr) {
        String[] leftAndRight = expr.split(",");
        String firstOperand = leftAndRight[0].trim().replace("'", "");
        String secondOperand = leftAndRight[1].trim().replace("'", "");
        Expr firstOperandExpr = firstOperand.matches("-?\\d+") ? ctx.mkInt(Integer.parseInt(firstOperand)) : ctx.mkIntConst(variableMap.get(firstOperand));
        Expr secondOperandExpr = secondOperand.matches("-?\\d+") ? ctx.mkInt(Integer.parseInt(secondOperand)) : ctx.mkIntConst(variableMap.get(secondOperand));

        return new Expr[]{firstOperandExpr, secondOperandExpr};
    }

    private BoolExpr constructNotEqualsExpr(String expr) {
        Expr[] expressions = constructVariableorConstantExpr(expr);
        return ctx.mkNot(ctx.mkEq(expressions[0], expressions[1]));
    }

    private BoolExpr constructEqualsExpr(String expr) {
        Expr[] expressions = constructVariableorConstantExpr(expr);
        return ctx.mkEq(expressions[0], expressions[1]);
    }

    private BoolExpr constructGreaterThanExpr(String expr) {
        Expr[] expressions = constructVariableorConstantExpr(expr);
        return ctx.mkGt(expressions[0], expressions[1]);
    }

    private BoolExpr constructLessThanExpr(String expr) {
        Expr[] expressions = constructVariableorConstantExpr(expr);
        return ctx.mkLt(expressions[0], expressions[1]);
    }

    private BoolExpr constructGreaterThanOrEqualExpr(String expr) {
        Expr[] expressions = constructVariableorConstantExpr(expr);
        return ctx.mkGe(expressions[0], expressions[1]);
    }

    private BoolExpr constructLessThanOrEqualExpr(String expr) {
        Expr[] expressions = constructVariableorConstantExpr(expr);
        return ctx.mkLe(expressions[0], expressions[1]);
    }

    private String removeOpAndBracket(String input) {
        String[] splitUp = input.split("\\(");
        return splitUp[1].substring(0, splitUp[1].length() - 1);
    }

    private BoolExpr createBoolExpr(String input) {
         // Remove "ite(" and the closing ")"
        System.out.println("Input: " + input);
        Pattern pattern = Pattern.compile("(&&|\\|\\||!|==|!=|<=|>=|<|>)");

        String whole = input;
        String[] leftAndRight = new String[2];

        String operation = getFirstOperator(whole);
        System.out.println("op is: " + operation);

        if (operation.equals("&&") || operation.equals("||")) {
            String withoutCond = whole.substring(3, whole.length() - 1);
            System.out.println(withoutCond);
            leftAndRight = extractLeftandRightSubbranches(withoutCond);
        }

        String removeOpAndBracket = removeOpAndBracket(input);
        String boolLiteralIndicator = "";
        switch (operation) {
            case "==":
                return constructEqualsExpr(removeOpAndBracket);
            case "!=":
                return constructNotEqualsExpr(removeOpAndBracket);
            case "<":
                return constructLessThanExpr(removeOpAndBracket);
            case "<=":
                return constructLessThanOrEqualExpr(removeOpAndBracket);
            case ">":
                return constructGreaterThanExpr(removeOpAndBracket);
            case ">=":
                return constructGreaterThanOrEqualExpr(removeOpAndBracket);
            case "&&":
                boolLiteralIndicator = leftAndRight[2];
                if (!boolLiteralIndicator.isEmpty()) { // check for any literals
                    if (boolLiteralIndicator.equals("L")) {
                        return ctx.mkAnd(getBooleanLiteralExpr(leftAndRight[0]), createBoolExpr(leftAndRight[1]));
                    } else if (boolLiteralIndicator.equals("R")) {
                        return ctx.mkAnd(createBoolExpr(leftAndRight[0]), getBooleanLiteralExpr(leftAndRight[1]));
                    } else if (boolLiteralIndicator.equals("LR")) {
                        return ctx.mkAnd(getBooleanLiteralExpr(leftAndRight[0]), getBooleanLiteralExpr(leftAndRight[1]));
                    }
                }
                return ctx.mkAnd(createBoolExpr(leftAndRight[0]), createBoolExpr(leftAndRight[1]));
            case "||":
                boolLiteralIndicator = leftAndRight[2];
                if (!boolLiteralIndicator.isEmpty()) { // check for any literals
                    if (boolLiteralIndicator.equals("L")) {
                        return ctx.mkOr(getBooleanLiteralExpr(leftAndRight[0]), createBoolExpr(leftAndRight[1]));
                    } else if (boolLiteralIndicator.equals("R")) {
                        return ctx.mkOr(createBoolExpr(leftAndRight[0]), getBooleanLiteralExpr(leftAndRight[1]));
                    } else if (boolLiteralIndicator.equals("LR")) {
                        return ctx.mkOr(getBooleanLiteralExpr(leftAndRight[0]), getBooleanLiteralExpr(leftAndRight[1]));
                    }
                }
                return ctx.mkOr(createBoolExpr(leftAndRight[0]), createBoolExpr(leftAndRight[1]));
            default:
                throw new IllegalArgumentException("Unsupported operation: " + operation);
        }

    }

    private boolean isNumericString(String str) {
        return str.matches("^\\d+$|^\\-\\(\\d+\\)$");
    }

    private String getConditionOnly() {
        // edge case where condition is simply true or false e.g. if (1)
        String[] splitByCommas = this.exprWithoutIte.split(",");
        if (isNumericString(splitByCommas[0])) {
            return ConditionParser.BOOLEAN_LITERAL_FLAG;
        }
        int brackCounter = 0;
        boolean balanced = false;
        boolean firstBracketFound = false;
        int charIndex = 0;
        StringBuilder conditionOnly = new StringBuilder();
        String branches;
        while (!balanced) {
            if (this.exprWithoutIte.charAt(charIndex) == '(') {
                firstBracketFound = true;
                brackCounter++;
            } else if (this.exprWithoutIte.charAt(charIndex) == ')') {
                brackCounter--;
            }
            if (brackCounter == 0 && firstBracketFound) balanced = true;
            conditionOnly.append(this.exprWithoutIte.charAt(charIndex));
            charIndex++;
        }

        System.out.println("Just Cond: " + conditionOnly);
//        System.out.println("left branch: " + leftBranch);
//        System.out.println("right branch: " + rightBranch);
        return conditionOnly.toString();

    }

    private void setBranches(String input) {
        String[] splitUp = input.split(",");
        int splits = splitUp.length;
        String leftBranch = splitUp[splits - 2];
        String rightBranch = splitUp[splits - 1];
        this.leftBranch = leftBranch.trim();
        this.rightBranch = rightBranch.trim().substring(0, rightBranch.length() - 2);
    }

    public BoolExpr parseIte(String condExpr, String variableToSet) {
        this.fullExprString = condExpr;

        // remove ite and last )
        this.exprWithoutIte = this.fullExprString.substring(4, this.fullExprString.length() - 1);
        // get
        String conditionOnly = getConditionOnly();
        setBranches(condExpr);
        this.variableToSet = variableToSet;

        BoolExpr incompleteConditionExpr;

        // dealing with only true or false
        if (conditionOnly.equals(ConditionParser.BOOLEAN_LITERAL_FLAG)) {
           incompleteConditionExpr = getBooleanLiteralExpr(this.exprWithoutIte.split(",")[0]);
        } else {
            incompleteConditionExpr =  createBoolExpr(conditionOnly);
        }

        int ifTrueValue = Integer.parseInt(this.leftBranch);
        int ifFalseValue = Integer.parseInt(this.rightBranch);

        IntExpr variable = this.variableToSet.equals("$ret")
                ? ctx.mkIntConst("$ret")
                : ctx.mkIntConst(variableMap.get(this.variableToSet));

        // make assignment for variable if cond is true
        IntExpr ifTrueValueExpr = ctx.mkInt(ifTrueValue);
        BoolExpr trueBranchExpr = ctx.mkEq(variable, ifTrueValueExpr);

        // same for false
        IntExpr ifFalseValueExpr = ctx.mkInt(ifFalseValue);
        BoolExpr falseBranchExpr = ctx.mkEq(variable, ifFalseValueExpr);

        this.completeCondition = (BoolExpr) ctx.mkITE(incompleteConditionExpr, trueBranchExpr, falseBranchExpr);
        return completeCondition;
    }

    private BoolExpr getBooleanLiteralExpr(String stringNum) {
        if (stringNum.contains("-")) {
            stringNum = "-" + stringNum.replaceAll("^-|[^\\d]", "");
        }
        int number = Integer.parseInt(stringNum);
        if (number != 0) { // true
            return ctx.mkTrue();
        } else {
            return ctx.mkFalse();
        }
    }


    public static void main(String[] args) {
        String example = "ite(||(==(a', 1), ==(b', 0)), 999, 0)";
        String example2 = "ite(||(&&(==(a', 1), ==(0, 2)), ==(b', 2)), 999, 0)";
        String example3 = "ite(==(a', 1), 999, 0)";
        String example4 = "ite(||(1, ==(a', 1)), 999, 0)";

//        System.out.println(new ConditionExtractor().parseIte("c", example2));

    }
//    }

}
