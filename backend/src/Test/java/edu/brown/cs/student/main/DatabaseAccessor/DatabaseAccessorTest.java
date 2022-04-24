package edu.brown.cs.student.main.DatabaseAccessor;

import edu.brown.cs.student.main.databaseaccessor.DatabaseAccessor;
import edu.brown.cs.student.main.databaseaccessor.SQLTable;
import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * This class tests the functionality of the database accessor.
 */
public class DatabaseAccessorTest {

  /**
   * Tests that the getColumns method of the db accessor fetches the correct columns
   * @throws SQLException
   * @throws ClassNotFoundException
   */
  @Test
  public void getColumnsTest() throws SQLException, ClassNotFoundException {
    DatabaseAccessor.load("data/recommendationdata/sql/data.sqlite3");
    ArrayList<String> expectedColList = new ArrayList<>();
    expectedColList.add("id");
    expectedColList.add("name");
    expectedColList.add("email");
    Assert.assertEquals(expectedColList, DatabaseAccessor.getColumns("names"));

  }

  /**
   * Basic functionality test for database proxy using data.sqlite3
   */
  @Test
  public void dataTest() throws SQLException, ClassNotFoundException {
    DatabaseAccessor.load("data/recommendationdata/sql/data.sqlite3");
    ResultSet rs = DatabaseAccessor.executeCommand("SELECT email FROM names");
    //assert that ResultSet is not null
    Assert.assertNotNull(rs);
    int size = 0;
    while (rs.next()) {
      size++;
    }
    //assert that it returned 60 emails
    Assert.assertEquals(size, 60);
  }

  /**
   * Tests that the getTableNames method returns the correct table names.
   * @throws SQLException
   * @throws ClassNotFoundException
   */
  @Test
  public void getTableNamesTest() throws SQLException, ClassNotFoundException {
    DatabaseAccessor.load("data/recommendationdata/sql/data.sqlite3");
    Set<String> tableNames = DatabaseAccessor.getTableNames();

    //checks that 4 tables are returned
    Assert.assertEquals(tableNames.size(), 4);
    //checks that remove is in table names
    assertTrue(tableNames.remove("names"));
  }

  /**
   * Tests the getTable method returns a table of the correct size.
    * @throws SQLException
   * @throws ClassNotFoundException
   */
  @Test
  public void getTableTest() throws SQLException, ClassNotFoundException {
    DatabaseAccessor.load("data/recommendationdata/sql/data.sqlite3");
    SQLTable table = DatabaseAccessor.getTable("names");

    //assert that it returned 60 emails
    Assert.assertEquals(table.getNumRows(), 60);
  }

}

