package edu.brown.cs.student.main.routefindermaps;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;

import java.io.IOException;
import java.util.List;

/**
 * Class generating nodes to be used to draw the path on the frontend. Given the
 * list of nodes through which our path was created, this class relies on the
 * Google Maps API to generate a bunch of nodes through which a marker on the
 * frontend can draw the path.
 */
public class RoutePointsGenerator {

  /**
   * Constructor for the RoutePointsGenerator class.
   */
  public RoutePointsGenerator() {}

  /**
   * This method uses the Google Maps API to generate latitude and longitude points
   * for every intersection along the route, so that the route can be drawn in
   * the frontend.
   * @param nodeList
   * @return
   */
  public List<LatLng> getRoutePoints(List<Node> nodeList) {
    GeoApiContext context = new GeoApiContext.Builder()
        .apiKey("AIzaSyAbGfdrfwUDK_1YXGP8b7NQZbNh3AKRH7o")
        .build();
    DirectionsResult result = null;
    try {
      result = DirectionsApi.newRequest(context)
          .mode(TravelMode.WALKING)
          .origin(new LatLng(nodeList.get(0).getLatitude(), nodeList.get(0).getLongitude()))
          .waypoints(this.getWaypoints(nodeList))
          .destination(new LatLng(nodeList.get(nodeList.size() -1).getLatitude(),
              nodeList.get(nodeList.size() -1).getLongitude()))
          .await();
    } catch (ApiException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result.routes[0].overviewPolyline.decodePath();

  }


  /**
   * This method, given a list of nodes through which our algorithm has created
   * a path, creates an array of LatLng points that can be passed into the
   * Google Maps distance API to ouptut latitute and longitude coords that we
   * can use to draw the map on the frontend.
   * @param nodeList
   * @return
   */
  public LatLng[] getWaypoints(List<Node> nodeList) {
    LatLng[] waypointsCoordsList = new LatLng[nodeList.size() - 2];
    //for node in nodeList
    for (int i = 0; i < waypointsCoordsList.length; i++) {
      Node currNode = nodeList.get(i + 1); //skip the first node (origin)
      waypointsCoordsList[i] = new LatLng(currNode.getLatitude(), currNode.getLongitude());
    }
    return waypointsCoordsList;
  }
}
