package edu.brown.cs.student.main.backendHandlers;

import com.google.gson.Gson;
import edu.brown.cs.student.main.databaseaccessor.DatabaseAccessor;
import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.SQLException;
import java.util.Map;

import org.json.JSONObject;

/**
 * This class, which implements the Route interface, serves as the handler for the /update endpoint
 * of the backend API.
 */
public class UpdateHandler implements Route {
  private static final Gson GSON = new Gson();

  /**
   * Handle method overrriden from the route interface. This handle receives the objects from the
   * request body, which are the table name, a HashMap describing the row to update, and another
   * HashMap mapping the column names to their updated value(s).
   * @param request request object from the caller
   * @param response response object
   * @return JSON string describing the result of the request.
   * @throws Exception
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {
    JSONObject data = new JSONObject(request.body());
    String tableName = data.getString("tableName");
    JSONObject colNameToOldValJSON = data.getJSONObject("colNameToOldVal");

    //original row data
    Map<String, Object>
        colNameToOldVal = GSON.fromJson(colNameToOldValJSON.toString(), Map.class);

    //row dadta updates
    JSONObject colNameToNewValJSON = data.getJSONObject("colNameToNewVal");
    Map<String, Object>
        colNameToNewVal = GSON.fromJson(colNameToNewValJSON.toString(), Map.class);

    try {
      DatabaseAccessor.updateRow(tableName, colNameToOldVal, colNameToNewVal);
    } catch (SQLException e) {
      return GSON.toJson(e.getMessage());
    }

    return GSON.toJson("Successful Update");
  }

}
