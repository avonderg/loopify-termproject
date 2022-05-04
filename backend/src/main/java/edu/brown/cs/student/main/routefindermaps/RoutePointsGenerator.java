package edu.brown.cs.student.main.routefindermaps;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.GeocodedWaypoint;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

import java.io.IOException;
import java.util.List;

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
  public GeocodedWaypoint[] getRoutePoints(List<Node> nodeList) {
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
    return result.geocodedWaypoints;

  }


  /**
   * This method, given a list of nodes through which our algorithm has created
   * a path, creates an array of LatLng points that can be passed into the
   * Google Maps distance API
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
