package edu.brown.cs.student.main.routefindermaps;
import java.util.ArrayList;
import java.util.List;


/**
 * Based on a user's current location and desired travel distance, generates a 'loop' route for the
 * user to follow.
 */
public class RouteFinder {
  int nodeNum = 100; // number of nodes per row / column
  int earthRadius = 3963;
  double longToMiles = 54.6;
  double latToMiles = 69; 
  Node start;
  List<Node> nodes;
  double distance;

  public RouteFinder(double startLat, double startLong, double distance) {
    this.distance = distance;
    this.start = new Node(startLat, startLong);
    double latDiameter = distance*(1/latToMiles);
    double lonDiameter = distance*(1/longToMiles);
    double latStep = latDiameter / nodeNum;
    double lonStep = latDiameter / nodeNum;
    for (double lat = start.getLatitude() - latDiameter/2; lat <= start.getLatitude() + latDiameter/2; lat += latStep){
      for (double lon = start.getLongitude() - lonDiameter/2; lon <= start.getLongitude() + lonDiameter/2; lat += lonStep){
        nodes.add(new Node(lat, lon));
      }
    }
  }

  public List<Node> findRoute(){
    List<Node> route = new ArrayList<>();

    return route;
  }

}
