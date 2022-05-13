package edu.brown.cs.student.main.backendHandlers;

import com.google.gson.Gson;
import edu.brown.cs.student.main.routefindermaps.Node;
import edu.brown.cs.student.main.routefindermaps.RouteFinder;
import edu.brown.cs.student.main.routefindermaps.RoutePointsGenerator;
import org.json.JSONArray;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.List;


/**
 * This class handles accepting the user request for a route of a certain distance
 * from a user's current location, calls on appropriate classes and methods
 * throughout the backend, and then sends the list of Lat/Lng coordinates that
 * can be used to draw the path to the frontend.
 */
public class GetRouteHandler implements Route {
  private static final Gson GSON = new Gson();
  private static RouteFinder routeFinder = new RouteFinder(0, 0, 0);

  /**
   * Handle method overriden from the route interface. This handle receives the
   * run data for the user from the frontend and creates a RouteFinder instance
   * in order to
   * @param request request object from the caller
   * @param response response object
   * @return JSON string describing the result of the request.
   * @throws Exception
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {
    JSONArray data = new JSONArray(request.body());
//    JSONArray runDataJSON = data.getJSONArray("userRouteRequest");
    List<Double>
        userRunData = GSON.fromJson(String.valueOf(data), ArrayList.class);
    System.out.println(userRunData);

    //userRunData:
    //index 0: start location latitude
    //index 1: start location longitude
    //index 2: distance to run
    double startLat = userRunData.get(0);
    double startLon = userRunData.get(1);
    double distance = userRunData.get(2);
    if (routeFinder.getDistance() != distance ||
            routeFinder.getStart().getLatitude() != startLat ||
            routeFinder.getStart().getLongitude() != startLon){
      routeFinder = new RouteFinder(userRunData.get(0),
              userRunData.get(1), userRunData.get(2));
    }
    List<Node> route = routeFinder.findRoute();
    RouteInfo routeInfo = new RouteInfo(routeFinder.getPathDistance(), new RoutePointsGenerator().getRoutePoints(route));

    return this.GSON.toJson(routeInfo);
  }
}