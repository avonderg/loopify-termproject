package edu.brown.cs.student.main.JsonParsing;

import edu.brown.cs.student.main.APIResponseParsing.DistanceAPIResponseParser;
import edu.brown.cs.student.main.APIResponseParsing.jsonParser;
import edu.brown.cs.student.main.routefindermaps.Node;
import org.json.JSONException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JsonParserTest {

  /**
   * Tests that the JsonParser class functions correctly.
   */
  @Test
  public void testJsonParser() {
    String jsonToTest = JsonConstant.json;
    Object jsonObj = DistanceAPIResponseParser.convertJsonToObject(jsonToTest.trim());
    List<Node> nodeList = new ArrayList<>();


    try {
      nodeList = DistanceAPIResponseParser.parseDrawnPathNodes(jsonObj);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    System.out.println(nodeList.get(0));

  }

  @Test
  public void testAPIResponseParser() {
  }

}
