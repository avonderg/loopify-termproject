package edu.brown.cs.student.main.replcommands;

import edu.brown.cs.student.main.databaseaccessor.DatabaseAccessor;
import edu.brown.cs.student.main.routefindermaps.RouteFinder;
//import edu.brown.cs.student.main.recommender.RecommenderSystem;

import java.util.List;

/**
 * This command loads a database file (.sqlite3) into the backend.
 */
public class GetRouteCommand implements ICommand {

  //get_route distance [latitude_1a] [longitude_1b]
  @Override
  public void handleCommand(List<String> buffer) {
    if (buffer.size() != 5) {
      System.out.println("ERROR: invalid number of args");
      return;
    }

    RouteFinder routeFinder = new RouteFinder(Double.parseDouble(buffer.get(1)),
        Double.parseDouble(buffer.get(2)), Double.parseDouble(buffer.get(3)));

    System.out.println("Successfully loaded database.");

  }
}
