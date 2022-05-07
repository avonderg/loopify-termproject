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
        System.out.println(route.get(0).getLocation());
        System.out.println(route.get(1).getLocation());
        System.out.println(route.get(2).getLocation());
        System.out.println(route.get(3).getLocation());
        System.out.println(routeFinder.findRoute());
    }
    @Test
    public void routeTest5mile() {
        RouteFinder routeFinder = new RouteFinder(41.82690997456079, -71.40016570863611, 5);
        List<Node> route = routeFinder.findRoute();
        System.out.println(route.get(0).getLocation());
        System.out.println(route.get(1).getLocation());
        System.out.println(route.get(2).getLocation());
        System.out.println(route.get(3).getLocation());
        System.out.println(routeFinder.findRoute());
    }
    @Test
    public void routeTest20mile(){
        RouteFinder routeFinder = new RouteFinder(41.82690997456079, -71.40016570863611, 20);
        List<Node> route = routeFinder.findRoute();
        System.out.println(route.get(0).getLocation());
        System.out.println(route.get(1).getLocation());
        System.out.println(route.get(2).getLocation());
        System.out.println(route.get(3).getLocation());
        System.out.println(routeFinder.findRoute());
    }
}
