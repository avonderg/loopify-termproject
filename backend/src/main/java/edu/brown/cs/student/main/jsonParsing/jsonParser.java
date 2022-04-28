package edu.brown.cs.student.main.jsonParsing;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.brown.cs.student.main.routefindermaps.Node;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.List;

public class jsonParser {
  List<Node> drawnPathNodes;

  /**
   * Constructor for the jsonParser class
   * @param drawnPathNodes - nodes used for drawing the path on the map
   */
  public jsonParser(List<Node> drawnPathNodes) {
    this.drawnPathNodes = drawnPathNodes;
  }

  /**
   * Extracts the endpoints list parameter from a JSON object and stores it into a
   * generic object.
   *
   * @param endpointsList the list of endpoints
   * @return the list of Object[]
   */
  public Object convertJsonToObject(String endpointsList) { // pass in enpointResponse.body()
    Gson parser = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    return parser.fromJson(endpointsList, Object.class);
  }

  public List<Node> parseDrawnPathNodes(Object json) throws JSONException {
    JSONObject obj = (JSONObject) json;
    JSONArray routes = obj.getJSONArray("routes");
    for (int i = 0; i < routes.length(); i++) { // loop through routes
      JSONObject leg = (JSONObject) routes.get(i);
      JSONArray steps = leg.getJSONArray("steps");
      for (int j = 0; j < steps.length(); j++) {
        JSONObject endLoc = (JSONObject) steps.get(2);
        double latitude = (double) endLoc.get("lat");
        double longitude = (double) endLoc.get("long");
        drawnPathNodes.add(new Node(latitude,longitude));
      }
    }


  }





}
