package edu.brown.cs.student.main.DatabaseAccessor;

import edu.brown.cs.student.main.databaseaccessor.SQLQueryGenerator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the functionality of the database accessor.
 */
public class QueryGeneratorTest {

  /**
   * Tests that an update query is successfully generated given the old row data, new row data, a
   * and the table name
   */
  @Test
  public void getUpdateQueryTest() {
    Map<String, Object> colNameToOldVal = new HashMap<String,Object>() {
      {
        put("name", "Ben");
        put("id", "13");
      }
    };
    Map<String, Object> colNameToNewVal = new HashMap<String,Object>() {
      {
        put("name", "Dan");
        put("id", "10");
      }
    };
    String generatedQuery = SQLQueryGenerator.getUpdateQuery("Tester", colNameToOldVal,
        colNameToNewVal);
    String expectedQuery = "UPDATE Tester SET name='Dan', id='10' WHERE name='Ben' AND id='13';";

    assertEquals(expectedQuery, generatedQuery);
  }

  /**
   * Tests that an update query is successfully generated given the old row data, new row data, a
   * and the table name
   */
  @Test
  public void getInsertQueryTest() {
    Map<String, Object> rowData = new HashMap<String,Object>() {
      {
        put("id", "10");
        put("animal", "ben");
        put("age", "20");
        put("height", "2");
      }
    };

    String generatedQuery = SQLQueryGenerator.getInsertQuery("zoo", rowData);

    String expectedQuery = "INSERT INTO zoo ('animal', 'id', 'age', 'height') VALUES " +
        "('ben', '10', '20', '2');";
    System.out.println(generatedQuery);
    System.out.println(expectedQuery);
    assertEquals(expectedQuery, generatedQuery);
  }



  /**
   * Tests that an update query is successfully generated given the old row data, new row data, a
   * and the table name
   */
  @Test
  public void getDeleteQueryTest() {
    Map<String, Object> conditions = new HashMap<String,Object>() {
      {
        put("name", "Ben");
        put("id", "13");
      }
    };

    String generatedQuery = SQLQueryGenerator.getDeleteQuery("Tester", conditions);
    String expectedQuery = "DELETE FROM Tester WHERE name='Ben' AND id='13';";
//    System.out.println(generatedQuery);
    assertEquals(expectedQuery, generatedQuery);
  }
}

