package sg.edu.nus.se.its.alignment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import sg.edu.nus.se.its.model.Constant;
import sg.edu.nus.se.its.model.Expression;
import sg.edu.nus.se.its.model.Operation;
import sg.edu.nus.se.its.model.Program;
import sg.edu.nus.se.its.model.Variable;
import sg.edu.nus.se.its.util.JsonSerializerWithInheritance;
import sg.edu.nus.se.its.util.RuntimeTypeAdapterFactory;
import sg.edu.nus.se.its.util.ServiceUtils;

/**
 * Helper class to access the current alignment implementation via the ITS services.
 */
public class AlignmentServiceImpl implements StructuralAlignment, VariableAlignment {

  public static final boolean DEBUG = false;

  public static final String URL_ALIGNMENT_STRUCTURAL =
      "https://its.comp.nus.edu.sg/cs3213/alignment_structural";

  public static final String URL_ALIGNMENT_VARIABLE =
      "https://its.comp.nus.edu.sg/cs3213/alignment_variable";

  private static final String ENDPOINT_NOT_FOUND = "{\"detail\":\"Not Found\"}";
  private static final String INTERNAL_SERVER_ERROR = "Internal Server Error";


  @Override
  public StructuralMapping generateStructuralAlignment(Program reference, Program submission)
      throws AlignmentException {

    String jsonPayload = null;
    jsonPayload = constructJsonRequestForStructuralAlignment(submission, reference);

    if (DEBUG) {
      System.out.println("jsonPayload:");
      System.out.println(jsonPayload);
    }

    String response;
    try {
      response = ServiceUtils.post(URL_ALIGNMENT_STRUCTURAL, jsonPayload);
    } catch (IOException e) {
      throw new RuntimeException(
          "Unexpected exception during ITS structural alignment service call!", e);
    }

    if (DEBUG) {
      System.out.println("response:");
      System.out.println(response);
    }

    if (response.equals(ENDPOINT_NOT_FOUND)) {
      throw new RuntimeException("Endpoint not found! URL=" + URL_ALIGNMENT_STRUCTURAL);
    }

    if (response.equals(INTERNAL_SERVER_ERROR)) {
      throw new RuntimeException("Internal Server Error! URL=" + URL_ALIGNMENT_STRUCTURAL);
    }

    StructuralMapping mapping = fromJsonStructuralMapping(response);

    if (DEBUG) {
      System.out.println("mapping:");
      System.out.println(mapping);
    }

    return mapping;
  }

  @Override
  public VariableMapping generateVariableAlignment(Program reference, Program submission,
      StructuralMapping strucAlignment) throws AlignmentException {
    String jsonPayload = null;
    jsonPayload = constructJsonRequestForVariableAlignment(submission, reference, strucAlignment);

    if (DEBUG) {
      System.out.println("jsonPayload:");
      System.out.println(jsonPayload);
    }

    String response;
    try {
      response = ServiceUtils.post(URL_ALIGNMENT_VARIABLE, jsonPayload);
    } catch (IOException e) {
      throw new RuntimeException("Unexpected exception during ITS variable alignment service call!",
          e);
    }

    if (DEBUG) {
      System.out.println("response:");
      System.out.println(response);
    }

    if (response.equals(ENDPOINT_NOT_FOUND)) {
      throw new RuntimeException("Endpoint not found! URL=" + URL_ALIGNMENT_VARIABLE);
    }

    if (response.equals(INTERNAL_SERVER_ERROR)) {
      throw new RuntimeException("Internal Server Error! URL=" + URL_ALIGNMENT_VARIABLE);
    }

    VariableMapping mapping = fromJsonVariableMapping(response);

    if (DEBUG) {
      System.out.println("mapping:");
      System.out.println(mapping);
    }

    return mapping;
  }

  private String constructJsonRequestForStructuralAlignment(Program submission, Program reference) {
    StringBuilder sb = new StringBuilder();
    sb.append("{");

    GsonBuilder builderProgram = new GsonBuilder();
    builderProgram.registerTypeAdapter(Expression.class,
        new JsonSerializerWithInheritance<Expression>());
    builderProgram.setPrettyPrinting();
    Gson gsonProgram = builderProgram.create();

    sb.append("\"reference_solution\": \"");
    sb.append(gsonProgram.toJson(reference).replace("\\\"", "\\\\\"").replace("\"", "\\\"")
        .replace("\n", "\\n"));
    sb.append("\",");

    sb.append("\"student_solution\": \"");
    sb.append(gsonProgram.toJson(submission).replace("\\\"", "\\\\\"").replace("\"", "\\\"")
        .replace("\n", "\\n"));
    sb.append("\"");

    sb.append("}");
    return sb.toString();
  }

  private String constructJsonRequestForVariableAlignment(Program submission, Program reference,
      StructuralMapping strucAlignment) {
    StringBuilder sb = new StringBuilder();
    sb.append("{");

    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(Expression.class, new JsonSerializerWithInheritance<Expression>());
    builder.setPrettyPrinting();
    Gson gson = builder.create();
    sb.append("\"reference_solution\": \"");
    sb.append(gson.toJson(reference).replace("\\\"", "\\\\\"").replace("\"", "\\\"").replace("\n",
        "\\n"));
    sb.append("\",");

    sb.append("\"student_solution\": \"");
    sb.append(gson.toJson(submission).replace("\\\"", "\\\\\"").replace("\"", "\\\"").replace("\n",
        "\\n"));
    sb.append("\",");

    GsonBuilder builderStructuralAlignment = new GsonBuilder();
    builderStructuralAlignment.setPrettyPrinting();
    Gson gsonStructuralAlignment = builderStructuralAlignment.create();

    sb.append("\"structural_alignment\": \"");
    sb.append(gsonStructuralAlignment.toJson(strucAlignment).replace("\\\"", "\\\\\"")
        .replace("\"", "\\\"").replace("\n", "\\n"));
    sb.append("\"");

    sb.append("}");
    return sb.toString();
  }

  private StructuralMapping fromJsonStructuralMapping(String json) {
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    return gson.fromJson(json, StructuralMapping.class);
  }

  private VariableMapping fromJsonVariableMapping(String json) {
    GsonBuilder builder = new GsonBuilder().enableComplexMapKeySerialization();
    RuntimeTypeAdapterFactory<Expression> expressionAdapter = RuntimeTypeAdapterFactory
        .of(Expression.class, "tokentype").registerSubtype(Variable.class, "Variable")
        .registerSubtype(Operation.class, "Operation").registerSubtype(Constant.class, "Constant");
    builder.registerTypeAdapterFactory(expressionAdapter);
    Gson gson = builder.create();
    return gson.fromJson(json, VariableMapping.class);
  }

}
