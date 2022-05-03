package edu.brown.cs.student.main.routefindermaps;
import com.google.maps.GeoApiContext;

import java.util.ArrayList;
import java.util.List;


/**
 * Based on a user's current location and desired travel distance, generates a 'loop' route for the
 * user to follow.
 */
public class RouteFinder {
  private final int nodeNum = 5; // number of nodes per row / column
  Node start;
  NodeGrid nodes;
  double distance;

  public RouteFinder(double startLat, double startLong, double distance) {
    this.distance = distance;
    //this.start = new Node(startLat, startLong, nodeNum/2, nodeNum/2);
    nodes = new NodeGrid(startLat, startLong, distance, nodeNum);
  }

  public List<Node> findRoute(){
    List<Node> route = new ArrayList<>();

    return route;
  }

}
