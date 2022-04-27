package edu.brown.cs.student.main.routefindermaps;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;

/**
 * Class representing a node within a map.
 */
public class Node {
  private double latitude;
  private double longitude;
  private GeoApiContext context;
  /**
   * Constructs a new Node object.
   * @param latitude - latitude
   * @param longitude - longitude
   */
  public Node(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
    context = new GeoApiContext.Builder()
            .apiKey("AIza...")
            .build();
  }

  /**
   * Getter method for the latitude field of a Node.
   * @return latitude
   */
  public double getLatitude() {
    return latitude;
  }

  /**
   * Getter method for the longitude field of a Node.
   * @return longitude
   */
  public double getLongitude() {
    return longitude;
  }

  /**
   * fill in later
   * @return Node
   */
  public Node getDistanceFrom(Node otherNode) {

  }
}
