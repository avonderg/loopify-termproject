package edu.brown.cs.student.main.RouteFinder;

import edu.brown.cs.student.main.routefindermaps.NodeGrid;
import edu.brown.cs.student.main.routefindermaps.Node;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Tester class for the node grid class.
 */
public class NodeGridTest {
    /**
     * Tests that the node grid size is set correctly.
     */
    @Test
    public void nodeGridSizeTest() {
        NodeGrid nodeGrid = new NodeGrid(0, 0, 20, 5);
        Assert.assertEquals(25, nodeGrid.getNodes().size());
    }

    /**
     * Tests that the longitude and latitude coordinates of the start node are set correctly.
     */
    @Test
    public void startNodeTest() {
        NodeGrid nodeGrid = new NodeGrid(23.43, 55.33, 20, 5);
        Node startNode = nodeGrid.getStartNode();
        Assert.assertEquals(55.33, startNode.getLongitude(), 1e-6);
        Assert.assertEquals(23.43, startNode.getLatitude(), 1e-6);
    }

    /**
     * Tests that the node grid has the correct shape by checking the first and last nodes.
     */
    @Test
    public void shapeTest() {
        double startLat = 23.43;
        double startLon = 55.33;
        NodeGrid nodeGrid = new NodeGrid(startLat, startLon, 20, 5);
        List<Node> nodes = nodeGrid.getNodes();
        Node firstNode = nodes.get(0);
        Node lastNode = nodes.get(nodes.size() - 1);
        Assert.assertTrue(firstNode.getLatitude() < startLat);
        Assert.assertTrue(firstNode.getLongitude() < startLon);
        Assert.assertTrue(lastNode.getLatitude() > startLat);
        Assert.assertTrue(lastNode.getLongitude() > startLon);
    }

    /**
     * Checks that node ids are set correctly.
     */
    @Test
    public void idsTest() {
        double startLat = 23.43;
        double startLon = 55.33;
        NodeGrid nodeGrid = new NodeGrid(startLat, startLon, 20, 5);
        List<Node> nodes = nodeGrid.getNodes();
        for (int i = 0; i < nodes.size(); i++){
            Assert.assertEquals(i, nodes.get(i).getId());
        }
    }

    /**
     * Tests that the distance is set correctly
     */
    @Test
    public void distancesTest() {
        double startLat = 23.43;
        double startLon = 55.33;
        NodeGrid nodeGrid = new NodeGrid(startLat, startLon, 20, 5);
        List<Node> nodes = nodeGrid.getNodes();
        Node firstNode = nodes.get(0);
        Node lastNode = nodes.get(nodes.size() - 1);
        double firstLat = firstNode.getLatitude();
        double firstLon = firstNode.getLongitude();
        double lastLat = lastNode.getLatitude();
        double lastLon = lastNode.getLongitude();
        Assert.assertEquals(20, 69*(lastLat - firstLat), 1e-6);
        Assert.assertEquals(20, 69*Math.cos(startLat*Math.PI/180)*(lastLon - firstLon), 1e-6);
    }
}
