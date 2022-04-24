package edu.brown.cs.student.main.backendHandlers;

import com.google.gson.Gson;
import edu.brown.cs.student.main.databaseaccessor.DatabaseAccessor;
import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.SQLException;

/**
 * Class that handles any GET request to the /tableNames endpoint.
 * credit: Suraj Anand
 */
public class TableNameHandler implements Route {
  /**
   * Creates a new GSON to create Json String from Object.
   */
  private final Gson gson = new Gson();

  /**
   * Handles a request to spark backend server and this route.
   *
   * @param req spark request (handled in typescript)
   * @param res spark response (handled in typescript)
   * @return String representing JSON object
   */
  @Override
  public String handle(Request req, Response res) {
    try {
      return this.gson.toJson(DatabaseAccessor.getTableNames());
      // returns table names
    } catch (IllegalStateException e) {
      return this.gson.toJson(e.getMessage());
      // returns error
    } catch (SQLException e) {
      return this.gson.toJson(e.getMessage());
      // returns error
    }
  }
}
