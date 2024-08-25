package sg.edu.nus.se.its.integration;

import java.io.IOException;
import sg.edu.nus.se.its.util.ServiceUtils;
import sg.edu.nus.se.its.util.TestUtils;

/**
 * Example to interact with the repair module in online ITS service.
 */
public class RepairExample {

  /**
   * Execute this function to send the example post request and to observe the response.
   *
   * @param args - Java standard arguments
   * @throws Exception - thrown when the sample JSON payload file is not available or the web
   *         service is not available
   */
  public static void main(String[] args) throws IOException {

    String url = "https://its.comp.nus.edu.sg/test/repair";
    String jsFile = System.getProperty("user.dir") + "/src/test/resources/example.json";
    try {
      String jsRequest = TestUtils.readFileAsString(jsFile);
      String response = ServiceUtils.post(url, jsRequest);

      System.out.println(response);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

}
