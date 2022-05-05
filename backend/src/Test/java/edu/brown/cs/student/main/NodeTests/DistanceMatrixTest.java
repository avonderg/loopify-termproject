package edu.brown.cs.student.main.NodeTests;

import com.google.maps.errors.ApiException;
import edu.brown.cs.student.main.routefindermaps.DistanceMatrixGenerator;
import edu.brown.cs.student.main.routefindermaps.NodeGrid;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class DistanceMatrixTest {

  @Test
  public void testCalculateDistances() throws IOException, InterruptedException, ApiException {
    String[] origins = {"120 W Randolph St, Chicago, IL 60602, USA", "204 S Clark St, Chicago, IL 60604, USA", "629 N Desplaines St, Chicago, IL 60661, USA"};
    String[] dests = {"233 S Wacker Dr, Chicago, IL 60606, USA", "101 S Wacker Dr, Chicago, IL 60606, USA", "25 S Wacker Dr, Chicago, IL 60606, USA\""};

    DistanceMatrixGenerator generator = new DistanceMatrixGenerator(origins,dests);

    List<List<Double>> testDistances = generator.generateDistances();

    double dist1 = testDistances.get(0).get(0);
    System.out.println(dist1);
    double dist2 = testDistances.get(0).get(1);
    System.out.println(dist2);

    // makes sure the distances between locations can be found, and are correctly placed within the list of lists
    Assert.assertEquals(dist1, 0.7263829237254434,0.01);
    Assert.assertEquals(dist2, 0.6362841008510299,0.01);

  }
}
