package edu.brown.cs.student.main.RouteGeneration;
import com.google.maps.model.GeocodedWaypoint;
import com.google.maps.model.LatLng;
import edu.brown.cs.student.main.routefindermaps.Node;
import edu.brown.cs.student.main.routefindermaps.RoutePointsGenerator;

import edu.brown.cs.student.main.databaseaccessor.DatabaseAccessor;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoutePointsGeneratorTest {

  /**
   * Tests that the getWaypoints method of the RoutePointsGenerator correctly
   * constructs an array of LatLng objects.
   */
  @Test
  public void getWaypointsTest() {
    Node node0 = new Node(41.8240, 71.412);
    Node node1 = new Node(41.830, 71.422);
    Node node2 = new Node(41.837, 71.430);
    Node node3 = new Node(41.825, 71.425);
    List<Node> nodeList = Arrays.asList(node0, node1, node2, node3, node0);

//    LatLng point1 = new LatLng(31.25, 32.50);
//    LatLng point2 = new LatLng(31.19, 32.47);
//    LatLng point3 = new LatLng(31.25, 32.50);

    LatLng[] waypointsArr = new RoutePointsGenerator().getWaypoints(nodeList);

    //1st element in the
    Assert.assertEquals(node1.getLatitude(), waypointsArr[0].lat, 0.0);
    Assert.assertEquals(node1.getLongitude(), waypointsArr[0].lng, 0.0);
    Assert.assertEquals(node2.getLatitude(), waypointsArr[1].lat, 0.0);
  }

  /**
   * Tests that the getWaypoints method of the RoutePointsGenerator correctly
   * constructs an array of LatLng objects.
   */
  @Test
  public void getRoutePoints() {
    Node node0 = new Node(41.8240, 71.412);
    Node node1 = new Node(41.830, 71.422);
    Node node2 = new Node(41.837, 71.430);
    Node node3 = new Node(41.825, 71.425);
    List<Node> nodeList = Arrays.asList(node0, node1, node2, node3, node0);

//    LatLng point1 = new LatLng(31.25, 32.50);
//    LatLng point2 = new LatLng(31.19, 32.47);
//    LatLng point3 = new LatLng(31.25, 32.50);

    GeocodedWaypoint[] waypointsArr = new RoutePointsGenerator().getRoutePoints(nodeList);

    //1st element in the
    assert(waypointsArr.length > 3);
  }
}
