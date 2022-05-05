package edu.brown.cs.student.main.routefindermaps;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;

/**
 * Class representing a node within a map.
 */
public class Node {
  private int id;

  private double latitude;
  private double longitude;
  private GeoApiContext context;

  /**
   * Constructs a new Node object.
   * @param latitude - latitude
   * @param longitude - longitude
   */
  public Node(double latitude, double longitude, int id) {
    this.id = id;
    this.latitude = latitude;
    this.longitude = longitude;
    context = new GeoApiContext.Builder()
            .apiKey("AIzaSyAbGfdrfwUDK_1YXGP8b7NQZbNh3AKRH7o")
            .build();

  }

  /**
   * Constructs a new Node object that's not in our original node grid: used for the small node objects that we get using
   * original nodes as waypoints in the distance API. We send these nodes to the frontend to draw the map.
   * @param latitude - latitude
   * @param longitude - longitude
   */
  public Node(double latitude, double longitude) {
    this.row = row;
    this.col = col;
    this.latitude = latitude;
    this.longitude = longitude;
    context = new GeoApiContext.Builder()
            .apiKey("AIzaSyAbGfdrfwUDK_1YXGP8b7NQZbNh3AKRH7o")
            .build();
  }

  /**
   * Gets the id
   * @return id
   */
  public int getId(){
    return id;
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
   * Gets the Location of the node as a string
   * @return location string
   */
  public String getLocation(){
    return String.valueOf(longitude) + ", " + String.valueOf(latitude);
  }
}
