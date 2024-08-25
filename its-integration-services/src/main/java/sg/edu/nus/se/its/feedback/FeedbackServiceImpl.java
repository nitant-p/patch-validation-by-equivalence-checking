package sg.edu.nus.se.its.feedback;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.List;
import sg.edu.nus.se.its.model.Expression;
import sg.edu.nus.se.its.model.Input;
import sg.edu.nus.se.its.model.Program;
import sg.edu.nus.se.its.util.JsonSerializerWithInheritance;
import sg.edu.nus.se.its.util.ServiceUtils;

/**
 * Helper class to access the current feedback implementation via the ITS services.
 */
public class FeedbackServiceImpl {

  public static final boolean DEBUG = false;

  public static final String URL_FEEDBACK_ERROR =
      "https://its.comp.nus.edu.sg/cs3213/feedback_error";

  public static final String URL_FEEDBACK_FIX = "https://its.comp.nus.edu.sg/cs3213/feedback_fix";

  private static final String ENDPOINT_NOT_FOUND = "{\"detail\":\"Not Found\"}";
  private static final String INTERNAL_SERVER_ERROR = "Internal Server Error";

  String languageIdentifier;
  String entryFunctionName;

  /**
   * Initializes the feedback service. It needs the language identified by the file extension of the
   * source file and the entry function for the analysis.
   *
   * @param fileExtension - String, "c" or "py"
   * @param entryFunctionName - String, entry function name for the analysis
   */
  public FeedbackServiceImpl(String fileExtension, String entryFunctionName) {
    if (fileExtension.equals("c")) {
      this.languageIdentifier = "c";
    } else if (fileExtension.equals("py")) {
      this.languageIdentifier = "py";
    } else {
      throw new RuntimeException("Unsupported source file language: " + fileExtension);
    }

    this.entryFunctionName = entryFunctionName;
  }

  public String getFeedbackOnError(Program submittedProgram, Program referenceProgram,
      List<Input> inputs) {
    return getFeedback(URL_FEEDBACK_ERROR, submittedProgram, referenceProgram, inputs);
  }

  public String getFeedbackOnFix(Program submittedProgram, Program referenceProgram,
      List<Input> inputs) {
    return getFeedback(URL_FEEDBACK_FIX, submittedProgram, referenceProgram, inputs);
  }

  private String getFeedback(String serviceUrl, Program submittedProgram, Program referenceProgram,
      List<Input> inputs) {

    String jsonPayload = null;
    try {
      jsonPayload = constructJsonRequest(submittedProgram, referenceProgram, inputs);
    } catch (IOException e) {
      throw new RuntimeException("Unexpected exception during json payload construction!", e);
    }

    if (DEBUG) {
      System.out.println("jsonPayload:");
      System.out.println(jsonPayload);
    }

    String response;
    try {
      response = ServiceUtils.post(serviceUrl, jsonPayload);
    } catch (IOException e) {
      throw new RuntimeException("Unexpected exception during ITS feedback service call!", e);
    }

    if (DEBUG) {
      System.out.println("response:");
      System.out.println(response);
    }

    if (response.equals(ENDPOINT_NOT_FOUND)) {
      throw new RuntimeException("Endpoint not found! URL=" + serviceUrl);
    }

    if (response.equals(INTERNAL_SERVER_ERROR)) {
      throw new RuntimeException("Internal Server Error! URL=" + serviceUrl);
    }

    return response;
  }

  private String constructJsonRequest(Program submittedProgram, Program referenceProgram,
      List<Input> listOfInputs) throws IOException {
    StringBuilder sb = new StringBuilder();
    sb.append("{");

    sb.append("\"language\": \"");
    sb.append(this.languageIdentifier);
    sb.append("\",");

    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(Expression.class, new JsonSerializerWithInheritance<Expression>());
    builder.setPrettyPrinting();
    Gson gson = builder.create();
    sb.append("\"reference_solution\": \"");
    sb.append(gson.toJson(referenceProgram).replace("\\\"", "\\\\\"").replace("\"", "\\\"")
        .replace("\n", "\\n"));
    sb.append("\",");

    sb.append("\"student_solution\": \"");
    sb.append(gson.toJson(submittedProgram).replace("\\\"", "\\\\\"").replace("\"", "\\\"")
        .replace("\n", "\\n"));
    sb.append("\",");

    sb.append("\"function\": \"");
    sb.append(this.entryFunctionName);
    sb.append("\",");

    if (listOfInputs != null) {

      StringBuilder sbInputs = new StringBuilder();
      StringBuilder sbArgs = new StringBuilder();

      for (int j = 0; j < listOfInputs.size(); j++) {

        Input input = listOfInputs.get(j);
        if (input != null) {
          String[] inputs = input.getInputs();

          if (inputs != null && inputs.length > 0) {
            sbInputs.append("[");
            for (int i = 0; i < inputs.length; i++) {
              sbInputs.append(inputs[i]);
              if (i < inputs.length - 1) {
                sbInputs.append(",");
              }
            }
            sbInputs.append("]");
            if (j < listOfInputs.size() - 1) {
              sbInputs.append(",");
            }
          }

          String[] args = input.getArgs();
          if (args != null && args.length > 0) {
            sbArgs.append("[");
            for (int i = 0; i < args.length; i++) {
              sbArgs.append(args[i]);
              if (i < args.length - 1) {
                sbArgs.append(",");
              }
            }
            sbArgs.append("]");
            if (j < listOfInputs.size() - 1) {
              sbArgs.append(",");
            }
          }

        }
      }

      String inputString = sbInputs.toString();
      if (inputString.length() > 0) {
        sb.append("\"inputs\": \"[");
        sb.append(inputString);
        sb.append("]\",");
      } else {
        sb.append("\"inputs\": \"\",");
      }

      String argsString = sbArgs.toString();
      if (argsString.length() > 0) {
        sb.append("\"args\": \"[");
        sb.append(argsString);
        sb.append("]\"");
      } else {
        sb.append("\"args\": \"\"");
      }

    } else {
      sb.append("\"inputs\": \"\",");
      sb.append("\"args\": \"\"");
    }

    sb.append("}");
    return sb.toString();
  }

}
