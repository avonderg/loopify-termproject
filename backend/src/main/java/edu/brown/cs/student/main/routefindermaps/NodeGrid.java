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
  // CAUTION: These ratios vary depending on where we are in the earth
  private double longToMiles;
  private double latToMiles = 69;
  private List<Node> nodes = new ArrayList<>();
  private List<List<Double>> nodeDistances;
  // number of nodes per row/column
  private int nodeNum;

  /**
   * Constructor
   * @param startLat
   * @param startLong
   * @param distance
   */
  public NodeGrid(double startLat, double startLong, double distance, int nodeNum) {
    this.nodeNum = nodeNum;
    this.longToMiles = latToMiles * Math.sin(startLat * Math.PI / 180);
    double latDiameter = distance*(1/latToMiles);
    double lonDiameter = distance*(1/longToMiles);
    double latStep = latDiameter / nodeNum;
    double lonStep = latDiameter / nodeNum;
    double lat = startLat - latDiameter/2;
    double lon = startLong - lonDiameter/2;
    // Generating the random nodes in the map
    for (int i = 0; i < nodeNum; i++){
      for (int j = 0; j < nodeNum; j++){
        nodes.add(new Node(lat, lon, i, j));
        lat += latStep;
        lon += lonStep;
      }
    }
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
    int node1_i = node1.getRow()*nodeNum + node1.getCol();
    int node2_i = node2.getRow()*nodeNum + node2.getCol();
    return nodeDistances.get(node1_i).get(node2_i);
  }
}
