package edu.brown.cs.student.main.routefindermaps;
import com.google.maps.GeoApiContext;

import java.util.ArrayList;
import java.util.List;


/**
 * Based on a user's current location and desired travel distance, generates a 'loop' route for the
 * user to follow.
 */
public class RouteFinder {
  private final int nodeNum = 3; // number of nodes per row / column
  Node start;
  NodeGrid nodeGrid;
  double distance;
  public RouteFinder(double startLat, double startLong, double distance) {
    this.distance = distance;
    //this.start = new Node(startLat, startLong, nodeNum/2, nodeNum/2);
    this.nodeGrid = new NodeGrid(startLat, startLong, distance, nodeNum);
    this.start = nodeGrid.getStartNode();
  }
  public List<Node> findRoute(){
    List<Node> route = new ArrayList<>();
    List<Node> nodes = nodeGrid.getNodes();
    double closestDist = 0;
    Node node1;
    Node node2;
    Node pathNode1 = start;
    Node pathNode2 = start;
    for (int i = 0; i < nodes.size(); i++){
      node1 = nodes.get(i);
      for (int j = 0; j < nodes.size(); j++){
        node2 = nodes.get(j);
        if (i == j || i == start.getId() || j == start.getId()) continue;
        double dist = nodeGrid.travelDistance(start, node1)
                + nodeGrid.travelDistance(node1, node2)
                + nodeGrid.travelDistance(node2, start);
        if (closestDist == 0 || Math.abs(distance - closestDist) >= Math.abs(distance - dist)){
          closestDist = dist;
          pathNode1 = node1;
          pathNode2 = node2;
        }
      }
    }
    route.add(start);
    route.add(pathNode1);
    route.add(pathNode2);
    route.add(start);
    return route;
  }
}
