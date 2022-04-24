package edu.brown.cs.student.main.databaseaccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class generates SQL queries for the different types of database modifications used in the
 * backend API: UPDATE, INSERT INTO, and DELETE.
 */
public class SQLQueryGenerator {
  /**
   * Generates a SQL update query based on inputted data.
   * @param tableName name of table to update
   * @param colNameToOldVal map from colName to original values
   * @param colNameToNewVal map from colName to new values
   * @return the String of the SQL query
   */
  public static String getUpdateQuery(String tableName, Map<String, Object> colNameToOldVal,
                                      Map<String, Object> colNameToNewVal) {

    StringBuilder updateQuery = new StringBuilder("UPDATE " + tableName + " SET ");
    // Iterating HashMap through for loop

    List<String> keyList = new ArrayList<String>(colNameToNewVal.keySet());
    for (int i = 0; i < keyList.size(); i++) {
      String currCol = keyList.get(i);
      updateQuery.append(currCol).append("=").append("'").append(colNameToNewVal.get(currCol))
          .append("'");
      if (i != keyList.size() - 1) {
        updateQuery.append(", ");
      }
    }
    updateQuery.append(" WHERE ");

    for (int i = 0; i < keyList.size(); i++) {
      String currCol = keyList.get(i);
      updateQuery.append(currCol).append("=").append("'").append(colNameToOldVal.get(currCol))
          .append("'");
      if (i != keyList.size() - 1) {
        updateQuery.append(" AND ");
      }
    }
    updateQuery.append(";");

    return updateQuery.toString();

  }

  /**
   * Creates a SQL insertion query based on inputs.
   * @param tableName name of the table to add a row to
   * @param colNameToColData map of colNames to their values for the new entry
   * @return the String of the SQL query
   */
  public static String getInsertQuery(String tableName, Map<String, Object> colNameToColData) {
    StringBuilder insertQuery = new StringBuilder("INSERT INTO " + tableName + " (");

    List<String> colNames = new ArrayList<String>(colNameToColData.keySet());

    StringBuilder colNamesQuery = new StringBuilder("");
    StringBuilder colValuesQuery = new StringBuilder("");
    for (int i = 0; i < colNames.size(); i++) {
      String currCol = colNames.get(i);
      colNamesQuery.append("'").append(currCol).append("'");
      colValuesQuery.append("'").append(colNameToColData.get(currCol)).append("'");
      if (i == colNames.size() - 1) {
        break;
      }
      colNamesQuery.append(", ");
      colValuesQuery.append(", ");
    }

    insertQuery.append(colNamesQuery).append(") VALUES (").append(colValuesQuery).append(");");

    return insertQuery.toString();
  }

  /**
   * Generates a SQL DELETE query based on inputs.
   * @param tableName the name of the table
   * @param conditions a map of the column names to their values in the row to be deleted
   * @return the String of the SQL query
   */
  public static String getDeleteQuery(String tableName, Map<String, Object> conditions) {
    StringBuilder deleteQuery = new StringBuilder("DELETE FROM " + tableName + " WHERE ");

    List<String> colsToUpdate = new ArrayList<String>(conditions.keySet());

    for (int i = 0; i < colsToUpdate.size(); i++) {
      String currCol = colsToUpdate.get(i);
      deleteQuery.append(currCol).append("=").append("'").append(conditions.get(currCol))
          .append("'");
      if (i != colsToUpdate.size() - 1) {
        deleteQuery.append(" AND ");
      }
    }
    deleteQuery.append(";");

    return deleteQuery.toString();
  }


}
