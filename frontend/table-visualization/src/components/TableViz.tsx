import React from "react";
import { useState } from "react";
import DisplayTable from "./DisplayTable";
// import TableNames from './TableNames';
// import TableLoader from

type TableName = {
  name: string;
};

type Table = {
  name: string;
  headers: string[];
  rows: Record<string, string>[];
};

/**
 * The next-highest level rendering after App.jsx. This TableViz includes the table
 * selection, the actual rendering and loading of the SQL tables as well as the table
 * modification functionality.
 * @returns
 */
export default function TableViz() {
  //state variables for all the dynamic components
  const [tableNames, setTableNames] = useState([] as string[]);
  const [tableToDisplay, setTableToDisplay] = useState("" as string);
  const [tableData, setTableData] = useState({
    name: "",
    headers: [],
    rows: [],
  } as Table);
  const [tableLoaded, setTableLoaded] = useState(false);

  /**
   * Loads the dropdown upon page loading.
   */
  async function getTableNames() {
    const res = await fetch("http://localhost:4567/tableNames");
    let tableNamesList: string[] = await res.json();
    setTableNames(tableNamesList);
  }

  /// fetches and sets the table data for the specified table
  async function fetchAndSetTableData(tableName: string) {
    const TablePostParams: TableName = {
      name: tableName,
    };

    //API request to the table endpoint to fetch the given table
    const res: Response = await fetch("http://localhost:4567/table", {
      method: "post",
      body: JSON.stringify(TablePostParams),
      headers: {
        "Content-Type": "application/json; charset=UTF-8",
        "Access-Control-Allow-Origin": "*",
      },
    });

<<<<<<< HEAD
    // fetches the table data for the loaded sql database and table specified by dropdown from backend
    let tableData: Table = await res.json();
    setTableData(tableData);
    return tableData;
  }
=======
  // fetches the table data for the loaded sql database and table specified by dropdown from backend
  let tableData : Table = await res.json();
      console.log(tableData)
      setTableData(tableData)
      return tableData;
}
>>>>>>> kanban

  //html framework to be rendered
  return (
    <div className="table">
      <h1
        style={{
          fontFamily: "ribesblack",
          marginTop: "0%",
          fontSize: "400%",
          color: "white",
        }}
      >
        Sprint 3 Table Visualization
      </h1>

      <button
        id="loader"
        style={{
          marginBottom: "1%",
          fontFamily: "ribesblack",
          fontSize: "150%",
          color: "black",
        }}
        onClick={() => {
          getTableNames();
        }}
      >
        Load Table Data
      </button>
      {getTableNames}
      <div id="dropdown" style={{ marginBottom: "1%" }}>
        <select
          id="dropdownMenu"
          onChange={(e) => {
            if (e.target.value !== "Select a Table:") {
              setTableLoaded(true);
              setTableToDisplay(e.target.value);
              fetchAndSetTableData(e.target.value);
            }
          }}
        >
          <option>Select a Table:</option>
          {tableNames.map((table, index) => {
            return (
              <option key={index} value={table}>
                {table}
              </option>
            );
          })}
        </select>
      </div>

      <DisplayTable
        tableName={tableToDisplay}
        tableDisplaying={tableLoaded}
        tableData={tableData}
        loadTable={fetchAndSetTableData}
      />
    </div>
  );
}
