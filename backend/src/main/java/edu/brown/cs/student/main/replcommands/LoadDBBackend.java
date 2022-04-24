package edu.brown.cs.student.main.replcommands;

import edu.brown.cs.student.main.databaseaccessor.DatabaseAccessor;
//import edu.brown.cs.student.main.recommender.RecommenderSystem;

import java.util.List;

/**
 * This command loads a database file (.sqlite3) into the backend.
 */
public class LoadDBBackend implements ICommand {
  @Override
  public void handleCommand(List<String> buffer) {
    if (buffer.size() != 2) {
      System.out.println("ERROR: invalid command to loadDB");
      return;
    }
    try {
      String filepath = buffer.get(1);
      DatabaseAccessor.load(filepath);

    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    System.out.println("Successfully loaded database.");

  }
}
