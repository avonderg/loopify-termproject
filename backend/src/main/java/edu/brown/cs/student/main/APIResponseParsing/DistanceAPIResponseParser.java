package edu.brown.cs.student.main.APIResponseParsing;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.model.*;
import edu.brown.cs.student.main.routefindermaps.Node;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

public class DistanceAPIResponseParser {

  /**
   * Extracts the endpoints list parameter from a JSON object and stores it into a
   * generic object.
   *
   * @param endpointsList the list of endpoints
   * @return the list of Object[]
   */
  public static Object convertJsonToObject(String endpointsList) { // pass in enpointResponse.body()
    Gson parser = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    return parser.fromJson(endpointsList, Object.class);
  }

  public static List<Node> parseDrawnPathNodes(Object json) throws JSONException {
    //drawnPathNodes - nodes used for drawing the path on the map
    List<Node> drawnPathNodes = new ArrayList<>();
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
    return drawnPathNodes;
  }

  public static List<Node> getAllSteps(DirectionsResult directionAPIresult) {
    List<Node> fullTurnNodeList = new ArrayList<>();
    DirectionsRoute[] routes = directionAPIresult.routes;


    return fullTurnNodeList;
  }





}
