package edu.brown.cs.student.main.backendHandlers;

import com.google.gson.Gson;
import edu.brown.cs.student.main.databaseaccessor.DatabaseAccessor;
import org.json.JSONException;
import org.json.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.SQLException;

/**
 * Class that handles any post request to the /table endpoint.
 */
public class TableHandler implements Route {
  private static final Gson GSON = new Gson();

  @Override
  public String handle(Request req, Response res) {
    String tableName = "";
    JSONObject vals = null;

    try {
      vals = new JSONObject(req.body());
      tableName = vals.getString("name");
    } catch (JSONException e) {
      e.printStackTrace();
    }

    try {
      return GSON.toJson(DatabaseAccessor.getTable(tableName));
    } catch (IllegalStateException | IllegalArgumentException e) {
      return GSON.toJson(e.getMessage());
    } catch (SQLException e) {
      return GSON.toJson(e.getMessage());
    }
  }
}
