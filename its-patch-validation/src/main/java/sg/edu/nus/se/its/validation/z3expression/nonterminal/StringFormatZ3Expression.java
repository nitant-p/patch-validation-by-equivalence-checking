package sg.edu.nus.se.its.validation.z3expression.nonterminal;

import java.util.ArrayList;

import com.microsoft.z3.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;
import sg.edu.nus.se.its.validation.utils.VariableType;
import sg.edu.nus.se.its.validation.z3expression.Z3Expression;
import sg.edu.nus.se.its.validation.z3expression.Z3ExpressionVariable;
import sg.edu.nus.se.its.validation.z3expression.terminal.StringZ3Expression;

public class StringFormatZ3Expression implements Z3Expression {

    private StringZ3Expression format;
    private ArrayList<Z3Expression> variables;

    public StringFormatZ3Expression(Z3Expression format, ArrayList<Z3Expression> variables) {
        if (!(format instanceof StringZ3Expression)) {
            throw new IllegalArgumentException("Format string must be of type StringZ3Expression");
        }
        this.format = (StringZ3Expression) format;
        this.variables = variables;
    }    
    @Override
    public Expr interpret(TranslationContext translationContext, Context ctx) {
        String[] parts = this.format.getValue().split("%");
        ArrayList<Character> formatSpecifiers = new ArrayList<>();
        Expr result = ctx.mkString(parts[0]);

        System.out.println("variable: " + variables.toString());

        if (variables.isEmpty()) {
            System.out.println("vars are empty");
            return result;
        }

        for (int i = 1; i < parts.length; i++) {
            String part = parts[i];
            char formatSpecifier = part.charAt(0);
            formatSpecifiers.add(formatSpecifier);
            String argument = part.substring(1);

            if (i - 1 < variables.size()) {
                Z3Expression variable = variables.get(i - 1);
                Z3Expression value = translationContext.getValue(variable.toString());
                System.out.println("Variable" + variable.toString());
                System.out.println("Value" + value.toString());
                Expr variableExpr = variable.interpret(translationContext, ctx);

                String variableName = ((Z3ExpressionVariable) variable).getName();
                VariableType variableType = translationContext.getVariableType(variableExpr.toString());

                // Type checking
                if (!checkType(variableType, formatSpecifier)) {
                    throw new RuntimeException("Type mismatch error");
                }

                System.out.println("VARIABLE MAP" + translationContext.getVariableMap().toString());
                System.out.println("VALUE MAP" + translationContext.getValueMap().toString());
                System.out.println("TYPE MAP" + translationContext.getTypeMap().toString());
                
                result = ctx.mkConcat(result, ctx.mkString(value.toString()), ctx.mkString(argument));
            } else {
                result = ctx.mkConcat(result, ctx.mkString(argument));
            }
        }
        return result;
    }

    private boolean checkType(VariableType variableType, char formatSpecifier) {
        switch (formatSpecifier) {
            case 'd':
            case 'i':
                return variableType == VariableType.INTEGER;
            case 'f':
                return variableType == VariableType.FLOAT;
            case 's':
                return variableType == VariableType.CHAR_ARRAY;

            // Add more cases for other format specifiers if needed
            default:
                return false;
        }
    }

    // @Override
    // public String toString() {
    //     return String.format("StrFormat(%s)", this.format);
    // }

    @Override
public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("StrFormat(").append(this.format);

    if (!this.variables.isEmpty()) {
        builder.append(", ").append(this.variables.toString());
    }

    return builder.append(")").toString();
}

}
