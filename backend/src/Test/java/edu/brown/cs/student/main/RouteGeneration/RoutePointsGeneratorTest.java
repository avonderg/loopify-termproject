package edu.brown.cs.student.main.RouteGeneration;
import com.google.maps.model.LatLng;
import edu.brown.cs.student.main.routefindermaps.Node;
import edu.brown.cs.student.main.routefindermaps.RoutePointsGenerator;

import org.junit.Assert;
import org.junit.Test;


import java.util.Arrays;
import java.util.List;

/**
 * Tester class for RoutePointsGenerator.
 */
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

    LatLng[] waypointsArr = new RoutePointsGenerator().getWaypoints(nodeList);

    //1st element in the
    Assert.assertEquals(node1.getLatitude(), waypointsArr[0].lat, 0.0);
    Assert.assertEquals(node1.getLongitude(), waypointsArr[0].lng, 0.0);
    Assert.assertEquals(node2.getLatitude(), waypointsArr[1].lat, 0.0);
  }

  /**
   * Tests that the getRoutePoints method of the RoutePointsGenerator correctly
   * constructs an array of LatLng objects.
   */
  @Test
  public void getRoutePoints() {
    Node node0 = new Node(41.82366448384468, -71.39824717161981);
    Node node1 = new Node(41.828525341624996, -71.40105812663823);
    Node node2 = new Node(41.835112494074544, -71.40206663721735);
    Node node3 = new Node(41.8298364308529, -71.3875183356717);
    List<Node> nodeList = Arrays.asList(node0, node1, node2, node3, node0);

    List<LatLng> decodedPolyline = new RoutePointsGenerator().getRoutePoints(nodeList);

    System.out.println(decodedPolyline);
    //assert that the API has returned a path with a waypoint array bigger than the
    //number of intermediate nodes our algorithm is based on
    assert(decodedPolyline.size() > 3);
  }
}
