package edu.brown.cs.student.main.routefindermaps;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.GeocodedWaypoint;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

import java.util.List;

public class RoutePointsGenerator {

  public RoutePointsGenerator() {}

  public GeocodedWaypoint[] getRoutePoints(List<Node> nodeList) {
    GeoApiContext context = new GeoApiContext.Builder()
        .apiKey("AIzaSyAbGfdrfwUDK_1YXGP8b7NQZbNh3AKRH7o")
        .build();
    DirectionsResult result = DirectionsApi.newRequest(context)
        .mode(TravelMode.WALKING)
        .origin(new LatLng(nodeList.get(0).getLatitude(), nodeList.get(0).getLongitude()))
        .waypoints(this.getWaypoints(nodeList))
//        .optimizeWaypoints(true)
        .destination(new LatLng(nodeList.get(nodeList.size() -1).getLatitude(),
            nodeList.get(nodeList.size() -1).getLongitude()))
        .awaitIgnoreError();
    return result.geocodedWaypoints;

  }


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
