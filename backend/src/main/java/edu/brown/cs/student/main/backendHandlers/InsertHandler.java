package edu.brown.cs.student.main.backendHandlers;

import com.google.gson.Gson;
import edu.brown.cs.student.main.databaseaccessor.DatabaseAccessor;
import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * This class, which implements the Route interface, serves as the handler for the /insert endpoint
 * of the backend API.
 */
public class InsertHandler implements Route {
  private static final Gson GSON = new Gson();

  /**
   * Handle method overrriden from the route interface. This handle receives the objects from the
   * request body, which are the table name and a HashMap describing the inserted entry.
   * @param request request object from the caller
   * @param response response object
   * @return JSON string describing the result of the request.
   * @throws Exception
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {
    JSONObject data = new JSONObject(request.body());
    String tableName = data.getString("tableName");
    JSONObject rowData = data.getJSONObject("entryData");
    Map<String, Object>
        rowDataMap = GSON.fromJson(rowData.toString(), HashMap.class);

    try {
      System.out.println(rowDataMap);
      DatabaseAccessor.insertRow(tableName, rowDataMap);
    } catch (SQLException e) {
      return GSON.toJson(e.getMessage());
    }

    return GSON.toJson("Successfully inserted into the table");

  }
}
