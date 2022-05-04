package edu.brown.cs.student.main.routefindermaps;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.Distance;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixRow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
  private double longToMiles = 69;
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
   * Calculate travel distances between nodes
   */
  public void calculateDistances() throws IOException, InterruptedException, ApiException {
    // TODO: Jose generate 25 start and dest nodes
    int totalNum = nodeNum*nodeNum;
    String[] locations = new String[totalNum];
    for (int i = 0; i < totalNum; i++){
      locations[i] = (nodes.get(i).getLocation());
    }
    // TODO: Jose generate 25 start and dest nodes to be passed into this constructor
    DistanceMatrixGenerator generateDistances = new DistanceMatrixGenerator(locations,locations);
    nodeDistances = generateDistances.generateDistances(); // generates distances
  }


  /**
   * Getter method for the nodes of a node grid.
   * @return nodes
   */
  public List<Node> getNodes() {
    return nodes;
  }

  /**
   * Getter method for the node distances.
   * @return nodeDistances
   */
  public List<List<Double>> getNodeDistances() {
    return nodeDistances;
  }

  /**
   * Calculates the travel distances between two different nodes.
   * @param node1 - node i
   * @param node2- node j
   * @return double value representing node distance
   */
  public double travelDistance(Node node1, Node node2){
    int node1_i = node1.getRow()*nodeNum + node1.getCol();
    int node2_i = node2.getRow()*nodeNum + node2.getCol();
    return nodeDistances.get(node1_i).get(node2_i);
  }
}
