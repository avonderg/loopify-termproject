package edu.brown.cs.student.main.ApiHandlers;

import edu.brown.cs.student.main.databaseaccessor.ApiClient;
import org.junit.Test;

import java.net.URI;
import java.net.http.HttpRequest;
import java.sql.SQLException;

/**
 * This class sends a request to the /update endpoint of the Database API. The database tested is
 * the zoo database.
 */
public class UpdateHandlerTest {

  /**
   * Tests the update endpoint on the zoo database given in the data folder for Project1.
   * @throws SQLException
   * @throws ClassNotFoundException
   */
  @Test
  public void testUpdate() {
    String url = "http://localhost:4567/update";
    String tableName = "zoo";
    String id = "10";
    String animal = "ben";
    String age = "20";
    String height = "2";
    String newId = "20";
    String newAnimal = "sam";
    String newAge = "30";
    String newHeight = "3";
    String body =
        "{tableName: " + tableName + "" +
            ", colNameToOldVal: {id: " + id +
            ", animal: " + animal + "" +
            ", age: " + age + "" +
            ", height: " + height + "}" +
            ", colNameToNewVal: {id: " + newId +
            ", animal: " + newAnimal + "" +
            ", age: " + newAge + "" +
            ", height: " + newHeight +
            "}}";
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .POST(HttpRequest.BodyPublishers.ofString(body))
        .build();
    System.out.println(body);
    ApiClient client = new ApiClient();
    client.makeRequest(request);
  }
}
