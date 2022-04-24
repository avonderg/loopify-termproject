package edu.brown.cs.student.main.ApiHandlers;

import edu.brown.cs.student.main.databaseaccessor.ApiClient;
import org.junit.Test;

import java.net.URI;
import java.net.http.HttpRequest;
import java.sql.SQLException;

/**
 * This class sends a request to the /delete endpoint of the Database API. The database tested is
 * zoo.sqlite3 database from the given data for Project1.
 */
public class DeleteHandlerTest {

  /**
   * Tests the delete endpoint on the zoo database given in the data folder for Project1.
   * @throws SQLException
   * @throws ClassNotFoundException
   */
  @Test
  public void testDelete() {
    String url = "http://localhost:4567/delete";
    String tableName = "zoo";
    String id = "20";
    String animal = "sam";
    String age = "30";
    String height = "3";
    String body =
        "{tableName: " + tableName + "" +
            ", deleteConditions: {id: " + id +
            ", animal: " + animal + "" +
            ", age: " + age + "" +
            ", height: " + height + "}}";
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .POST(HttpRequest.BodyPublishers.ofString(body))
        .build();
    System.out.println(body);
    ApiClient client = new ApiClient();
    client.makeRequest(request);
  }
}
