package edu.brown.cs.student.main.ApiHandlers;

import edu.brown.cs.student.main.databaseaccessor.ApiClient;
import org.junit.Test;

import java.net.http.HttpRequest;
import java.sql.SQLException;
import java.net.URI;

/**
 * This class sends a request to the /insert endpoint of the Database API. The database tested is
 * zoo.sqlite3 database from the given data for Project1.
 */
public class InsertHandlerTest {

  /**
   * Tests the insert endpoint on the zoo database given in the data folder for Project1.
   * @throws SQLException
   * @throws ClassNotFoundException
   */
  @Test
  public void testInsert() {
    String url = "http://localhost:4567/insert";
    String tableName = "zoo";
    int id = 10;
    String animal = "ben";
    String age = "20";
    String height = "2";
    String body =
        "{tableName: " + tableName + "" +
            ", entryData: {id: " + id +
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


