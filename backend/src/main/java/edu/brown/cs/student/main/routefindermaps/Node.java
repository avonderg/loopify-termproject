package edu.brown.cs.student.main.routefindermaps;


/**
 * Class representing a node within a map.
 */
public class Node {

  private double latitude;
  private double longitude;

  /**
   * Constructs a new Node object.
   * @param latitude - latitude
   * @param longitude - longitude
   */
  public Node(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
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
  public Node getDistanceFrom() {
//    OkHttpClient client = new OkHttpClient().newBuilder()
//        .build();
//    Request request = new Request.Builder()
//        .url("https://maps.googleapis.com/maps/api/directions/json?origin=Toronto&destination=Montreal&key=YOUR_API_KEY")
//        .method("GET", null)
//        .build();
//    Response response = client.newCall(request).execute();
//    return null;
  }
}
