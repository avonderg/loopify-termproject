package edu.brown.cs.student.main.RouteFinder;

import edu.brown.cs.student.main.routefindermaps.Node;
import edu.brown.cs.student.main.routefindermaps.NodeGrid;
import edu.brown.cs.student.main.routefindermaps.RouteFinder;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class RouteFinderTest {
    @Test
    public void routeTest1mile() {
        RouteFinder routeFinder = new RouteFinder(41.82690997456079, -71.40016570863611, 1);
        List<Node> route = routeFinder.findRoute();
        Assert.assertEquals(4, route.size());
    }
    @Test
    public void routeTest5mile() {
        RouteFinder routeFinder = new RouteFinder(41.82690997456079, -71.40016570863611, 5);
        routeFinder.findRoute();
        Assert.assertTrue(4.5 < routeFinder.getPathDistance() && routeFinder.getPathDistance() < 5.5);
    }
    @Test
    public void routeTest20mile(){
        RouteFinder routeFinder = new RouteFinder(41.82690997456079, -71.40016570863611, 22);
        routeFinder.findRoute();
        System.out.println(routeFinder.getPathDistance());
        Assert.assertTrue(20 < routeFinder.getPathDistance() && routeFinder.getPathDistance() < 24);
    }
    @Test
    public void multiple1mileRoutes() {
        RouteFinder routeFinder = new RouteFinder(41.82690997456079, -71.40016570863611, 1);
        List<Node> route1 = routeFinder.findRoute();
        double dist1 = routeFinder.getPathDistance();
        List<Node> route2 = routeFinder.findRoute();
        double dist2 = routeFinder.getPathDistance();
        List<Node> route3 = routeFinder.findRoute();
        double dist3 = routeFinder.getPathDistance();
        List<Node> route4 = routeFinder.findRoute();
        double dist4 = routeFinder.getPathDistance();
        List<Node> route5 = routeFinder.findRoute();
        double dist5 = routeFinder.getPathDistance();

        Assert.assertTrue(route1 != route2);
        System.out.println(dist1);
        System.out.println(dist2);
        System.out.println(dist3);
        System.out.println(dist4);
        System.out.println(dist5);

    }
}
