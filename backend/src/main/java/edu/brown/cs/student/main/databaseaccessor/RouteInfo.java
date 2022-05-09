package edu.brown.cs.student.main.databaseaccessor;

import com.google.maps.model.LatLng;
import java.util.List;

/**
 * class that represents a table object.
 */
public class RouteInfo {
  private double distance;
  private List<LatLng> coords;

  /**
   * constructor.
   * @param distance of route generated
   * @param coords to draw map on frontend
   */
  public RouteInfo(double distance, List<LatLng> coords) {
    this.distance = distance;
    this.coords = coords;
  }
}
