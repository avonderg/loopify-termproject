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
   * Calculate travel distances
   */
  public void calculateDistances() throws IOException, InterruptedException, ApiException {
    int totalNum = nodeNum*nodeNum;
    String[] locations = new String[totalNum];
    for (int i = 0; i < totalNum; i++){
      locations[i] = (nodes.get(i).getLocation());
    }
    // assuming I have the origins and locations send over by Jose
    DistanceMatrixApiRequest distanceMatReq = DistanceMatrixApi.getDistanceMatrix(context, locations, locations);
    // TODO: Get distances from the distanceMatReq
    // if i pass in 25 origins and 25 destinations, API will return 625 distances
    DistanceMatrix matrix = distanceMatReq.await();
    DistanceMatrixRow[] rows = matrix.rows; // gets rows from distance matrix
    Double[][] distMat = new Double[25][25]; // 25 by 25 matrix

      for (int i = 0; i < rows.length; i++) { // loops through the rows
        DistanceMatrixElement[] elements = rows[i].elements; // gets elements array
        for (int j = 0; j < elements.length; j++) { // loops through the elements
          if (elements[j].status.equals("OK")) { // Error checking to prevent program from crashing
              distMat[i][j] = Double.valueOf(elements[j].distance.inMeters); // gets distance in meters
          }
          else { // if an element was unable to be grabbed -- given distance of -1
              distMat[i][j] = -1.0;
          }
        }
      }

      // converts array to list of lists
      nodeDistances = Arrays.stream(distMat)
              .map(Arrays::asList)
              .collect(Collectors.toList());
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
