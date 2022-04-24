package edu.brown.cs.student.main.databaseaccessor;

import org.json.JSONArray;
import org.json.JSONException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class that creates a database proxy that can execute SQL commands for any given SQLite3 database.
 */
public class DatabaseAccessor {
  private static Connection connection = null;
  //boolean that keeps track if there were any errors raised
  private boolean error = false;
  //Map given by input with table permissions
  private Map<String, String> tablePermissions;
  //list of all SQL commands available
  private final List<String> listOfSQLCommands = new ArrayList<>(Arrays.asList(
      "SELECT", "INSERT", "DROP", "UPDATE", "DELETE", "ALTER", "JOIN", "TRUNCATE"
  ));

  public DatabaseAccessor() {
  }

  /**
   * loads a SQLite3 database from a filepath entered by user. if valid, the
   * loaded databse can then be referenced throughout the class
   *
   * @param filename – filepath
   * @throws SQLException
   * @throws ClassNotFoundException
   */
  public static void load(String filename)
      throws SQLException, ClassNotFoundException {
    if (filename == null || filename.length() == 0) {
      System.out.println("ERROR: file name is null or empty");
      return;
    }
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:" + filename;
    try {
      connection = DriverManager.getConnection(urlToDB);
    } catch (SQLException e) {
      System.out.println("ERROR: " + e.getMessage());
    }
    Statement stat = connection.createStatement();
    stat.executeUpdate("PRAGMA foreign_keys=ON;");
  }

  /**
   * Getter method used in unit testing.
   *
   * @return connection
   */
  public Connection getConnection() {
    return this.connection;
  }


  /**
   * executes a SQL command on the loaded databse.
   *
   * @param sqlStatement – specified query statement
   * @return – ResultSet of the command
   * @throws SQLException
   */
  public static ResultSet executeCommand(String sqlStatement) throws SQLException {
    if (connection == null) {
      throw new IllegalStateException("ERROR: Cannot prepare statement before db is loaded.");
    }
    PreparedStatement command = connection.prepareStatement(sqlStatement);
    if (command != null && command.execute()) {
      return command.getResultSet();
    } else {
      return null;
    }
  }


  /**
   * retrieves all the table names from a loaded databse.
   *
   * @return – set of Strings
   * @throws SQLException
   * @throws IllegalStateException
   */
  public static Set<String> getTableNames()
      throws SQLException, IllegalStateException {

    ResultSet dbRes = DatabaseAccessor.executeCommand("SELECT tbl_name FROM sqlite_master;");

    Set<String> tableNames = new HashSet<>();
    while (dbRes.next()) {
      tableNames.add(dbRes.getString(1));
    }

    return tableNames;
  }

  /**
   * creates a SQLTable object given a specified table name in the databse.
   *
   * @param tableName – String representing table name
   * @return – a new SQLTable object created from the loaded databse
   * @throws SQLException
   * @throws IllegalStateException
   * @throws IllegalArgumentException credit: stew2003
   */
  public static SQLTable getTable(String tableName)
      throws SQLException, IllegalStateException, IllegalArgumentException {
    if (tableName == null) {
      throw new IllegalArgumentException("ERROR: Cannot get null table.");
    }

    // Check the requested table exists.
    if (!DatabaseAccessor.getTableNames().contains(tableName)) {
      throw new IllegalArgumentException(
          "ERROR: Table \"" + tableName + "\" does not exist.");
    }

    // Prepare a statement to get everything from the table.
    ResultSet dbRes = DatabaseAccessor.executeCommand("SELECT * FROM " + tableName + ";");
    ResultSetMetaData dbResMeta = dbRes.getMetaData();

    // Get the column headers
    int numCols = dbResMeta.getColumnCount();
    List<String> columnNames = new ArrayList<>();
    for (int i = 1; i <= numCols; i++) {
      columnNames.add(dbResMeta.getColumnName(i));
    }

    // Add each row of the db to a list
    List<Map<String, String>> rows = new ArrayList<>();
    while (dbRes.next()) {
      Map<String, String> curRow = new HashMap<>();
      for (int i = 1; i <= numCols; i++) {
        curRow.put(
            dbResMeta.getColumnName(i),
            dbRes.getString(i)
        );
      }
      rows.add(curRow);
    }
    return new SQLTable(tableName, columnNames, rows);
  }

  /**
   * retrieves all the column/header names from a specified table in the
   * loaded database.
   *
   * @param tableName – specific table we want to retrieve data from
   * @return – List of Strings representing headers
   * @throws SQLException
   * @throws IllegalStateException
   * @throws IllegalArgumentException
   */
  public static List<String> getColumns(String tableName)
      throws SQLException, IllegalStateException, IllegalArgumentException {
    if (tableName == null) {
      throw new IllegalArgumentException("ERROR: Cannot get null table.");
    }


    // Check the requested table exists.
    if (!DatabaseAccessor.getTableNames().contains(tableName)) {
      throw new IllegalArgumentException(
          "ERROR: Table \"" + tableName + "\" does not exist.");
    }

    // Prepare a statement to get everything from the table.
    ResultSet dbRes = DatabaseAccessor.executeCommand("SELECT * FROM " + tableName + ";");
    ResultSetMetaData dbResMeta = null;
    if (dbRes != null) {
      dbResMeta = dbRes.getMetaData();
    } else {
      System.out.println("ERROR: null result set.");
      return null;
    }

    // Get the column headers
    int numCols = dbResMeta.getColumnCount();
    List<String> columnNames = new ArrayList<>();
    for (int i = 1; i <= numCols; i++) {
      columnNames.add(dbResMeta.getColumnName(i));
    }

    return columnNames;
  }



  /**
   * Deletes a given row (in hash map form) from the given table.
   *
   * @param tableName  table to delete from
   * @param conditions the row details
   * @return String describing whether sucessful
   * @throws SQLException
   */
  public static ResultSet deleteRow(String tableName, Map<String, Object>
      conditions) throws SQLException {
    // Iterating HashMap through for loop

    //if exception is thrown, it will be caught and handled in the Route method.
    return executeCommand(SQLQueryGenerator.getDeleteQuery(tableName,
        conditions));

  }

  /**
   * Updates a given row based on the new values given for the row.
   *
   * @param tableName       name of table to update
   * @param colNameToOldVal map of colName to their original values
   *                        (to find the row)
   * @param colNameToNewVal map of the colNames to their updated Values
   * @return String describing whether successful
   * @throws SQLException
   */
  public static ResultSet updateRow(String tableName, Map<String, Object>
      colNameToOldVal, Map<String, Object> colNameToNewVal) throws SQLException {

    return executeCommand(SQLQueryGenerator.getUpdateQuery(tableName,
        colNameToOldVal, colNameToNewVal));
  }


  /**
   * Inserts a given entry into the given SQL table.
   *
   * @param tableName name of the table to insert into
   * @param rowData   map of the column names to their vals for the new entry
   * @return a ResultSet returned by the executeCommand method
   * @throws SQLException
   */
  public static ResultSet insertRow(String tableName,
                                    Map<String, Object> rowData) throws SQLException {
    //sql query to get the column names
    return executeCommand(SQLQueryGenerator.getInsertQuery(tableName, rowData));

  }


  public static List<String> jsonArrToStrArr(JSONArray jsonArray) {
    List<String> rowList = new ArrayList<String>();
    for (int i = 0; i < jsonArray.length(); i++) {
      try {
        rowList.add(jsonArray.getString(i));
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    return rowList;
  }
}
