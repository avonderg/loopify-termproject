package edu.brown.cs.student.main.databaseaccessor;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Map;

/**
 * class that represents a table object.
 */
public class SQLTable {
  private String name;
  private List<String> headers;
  private List<Map<String, String>> rows;

  /**
   * constructor.
   * @param name – table name
   * @param headers – headers for columns
   * @param rows – contents of table
   */
  public SQLTable(String name, List<String> headers, List<Map<String, String>> rows) {
    this.name = name;
    this.headers = headers;
    this.rows = rows;
  }

  /**
   * retrieves all the column names.
   * @return List of Strings
   */
  public List<String> getheaders() {
    return ImmutableList.copyOf(this.headers);
  }

  /**
   * retrieves the number of rows in a specified table.
   * @return int
   */
  public int getNumRows() {
    return this.rows.size();
  }
}
