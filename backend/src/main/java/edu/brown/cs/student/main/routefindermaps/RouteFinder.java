package edu.brown.cs.student.main.routefindermaps;
import com.google.maps.GeoApiContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Based on a user's current location and desired travel distance, generates a 'loop' route for the
 * user to follow.
 */
public class RouteFinder {
  private final int nodeNum = 5; // number of nodes per row / column
  Node start;
  NodeGrid nodeGrid;
  double distance;
  double pathDistance;
  Set<Integer> seenNodePairs = new HashSet<>(); // a node pair (i,j) will be represented as 25*i + j

  public RouteFinder(double startLat, double startLong, double distance) {
    this.distance = distance;
    //this.start = new Node(startLat, startLong, nodeNum/2, nodeNum/2);
    this.nodeGrid = new NodeGrid(startLat, startLong, distance, nodeNum);
    this.start = nodeGrid.getStartNode();
  }
  public double getPathDistance(){
    return this.pathDistance;
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
      int id1 = node1.getId();
      for (int j = 0; j < nodes.size(); j++){
        node2 = nodes.get(j);
        int id2 = node2.getId();
        if (i == j || id1 == start.getId() || id2 == start.getId() || seenNodePairs.contains(25*id1 + id2)){
          continue;
        }
        double dist = nodeGrid.travelDistance(start, node1)
            + nodeGrid.travelDistance(node1, node2)
            + nodeGrid.travelDistance(node2, start);
        if (closestDist == 0 || Math.abs(distance - closestDist) >= Math.abs(distance - dist)){
          closestDist = dist;
          pathNode1 = node1;
          pathNode2 = node2;
          pathDistance = dist;
        }
      }
    }
    seenNodePairs.add(25*pathNode1.getId() + pathNode2.getId());
    route.add(start);
    route.add(pathNode1);
    route.add(pathNode2);
    route.add(start);
    return route;
  }
}