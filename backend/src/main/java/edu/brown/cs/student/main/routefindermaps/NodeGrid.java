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
    this.nodeNum = nodeNum;
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
    try{
    calculateDistances();} catch (Exception e) {}
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
  public void calculateDistances() throws IOException, InterruptedException, ApiException {
    // TODO: Jose generate 25 start and dest nodes
    int totalNum = nodeNum*nodeNum;
    String[] locations = new String[totalNum];
    for (int i = 0; i < totalNum; i++){
      locations[i] = (nodes.get(i).getLocation());
    }
    // TODO: Jose generate 25 start and dest nodes to be passed into this constructor
    DistanceMatrixGenerator dist = new DistanceMatrixGenerator(locations,locations);
    nodeDistances = dist.generateDistances(); // generates distances
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
    return nodeDistances.get(node1.getId()).get(node2.getId());
  }
}
