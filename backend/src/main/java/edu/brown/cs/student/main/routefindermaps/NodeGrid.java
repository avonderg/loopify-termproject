package edu.brown.cs.student.main.routefindermaps;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a graph of nodes.
 *
 * source: https://www.softwaretestinghelp.com/java-graph-tutorial/
 */
public class NodeGrid {
  private GeoApiContext context = new GeoApiContext.Builder()
          .apiKey("AIzaSyAbGfdrfwUDK_1YXGP8b7NQZbNh3AKRH7o")
          .build();
  private double longToMiles;
  private double latToMiles = 69;
  private List<Node> nodes = new ArrayList<>();
  private List<List<Double>> nodeDistances;
  // number of nodes per row/column
  private int nodeNum = 5;

  /**
   * Constructor
   * @param startLat
   * @param startLon
   * @param distance
   */
  public NodeGrid(double startLat, double startLon, double distance, int nodeNum) {
    // For now, we will only deal with 25 nodes
    this.nodeNum = 5;
    this.longToMiles = latToMiles * Math.cos(startLat * Math.PI / 180);
    double latDiameter = distance*(1/latToMiles);
    double lonDiameter = distance*(1/longToMiles);
    double latStep = latDiameter / (nodeNum - 1);
    double lonStep = lonDiameter / (nodeNum - 1);
    double firstLat = startLat - latDiameter/2;
    double firstLon = startLon - lonDiameter/2;
    // Generating the nodes
    for (int i = 0; i < nodeNum; i++){
      for (int j = 0; j < nodeNum; j++){
        nodes.add(new Node(firstLat + j*latStep, firstLon + i*lonStep, i*nodeNum + j));
      }
    }
  }

  /**
   * Gets the starting node
   */
  public Node getStartNode(){
    return nodes.get(nodes.size()/2);
  }

  /**
   * Calculate travel distances
   */
  public void calculateDistances(){
    int totalNum = nodeNum*nodeNum;
    String[] locations = new String[totalNum];
    for (int i = 0; i < totalNum; i++){
      locations[i] = (nodes.get(i).getLocation());
    }
    DistanceMatrixApiRequest distanceMatReq = DistanceMatrixApi.getDistanceMatrix(context, locations, locations);
    // TODO: Get distances from the distanceMatReq

  }

  /**
   * Getter method for the adjacency list of a graph.
   * @return adjList
   */
  public List<Node> getNodes() {
    return nodes;
  }

  public double travelDistance(Node node1, Node node2){
    return nodeDistances.get(node1.getId()).get(node2.getId());
  }
}
