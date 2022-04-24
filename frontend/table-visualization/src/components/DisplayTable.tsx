import { getValue } from "@testing-library/user-event/dist/utils";
import { stringify } from "querystring";
import React, { useEffect, useState } from "react";
import { text } from "stream/consumers";
import AddRow from "./AddRow";
import UpdateRow from "./UpdateRow";
import DeleteRow from "./DeleteRow";
import "./DisplayTable.css";

type Table = {
  name: string;
  headers: string[];
  rows: Record<string, string>[];
};

type Row = {
  id: number;
  data: string[];
};

/**
 * This function, used a component within the TableViz component, handles displaying
 * the actual table.
 * @param props
 * @returns
 */
export default function DisplayTable(props: {
  tableName: string;
  tableDisplaying: boolean;
  tableData: Table;
  loadTable: Function;
}) {
  const items: any = [];
  function initializeItems(rowsToDisplay: Record<string, string>[]) {
    for (const row of props.tableData.rows) {
      items.push(
        <tr>
          {getValue(row)}
          <UpdateRow
            tName={props.tableName}
            headerNames={props.tableData.headers}
            row={row}
            loadTable={props.loadTable}
          />
          <DeleteRow
            tName={props.tableName}
            headerNames={props.tableData.headers}
            row={row}
            loadTable={props.loadTable}
          />
        </tr>
      );
    }
  }

  function getValue(row: Record<string, string>) {
    const rowSingles = [];
    for (const header of props.tableData.headers) {
      rowSingles.push(
        <td className="border" key={row[header]}>
          {row[header]}{" "}
        </td>
      );
    }
    return rowSingles;
  }

  const [rowsToDisplay, setRowsToDisplay] = useState(
    initializeItems(props.tableData.rows)
  );
  let headerToSortBy = props.tableData.headers[0];

  const tableHeaders = props.tableData.headers.map((header: string) => (
    <td
      id="header"
      className="border"
      key={header}
      onClick={(e) => {
        sortBy(header);
      }}
    >
      {header}
    </td>
  ));

  const headerSortedAscending: Map<string, boolean> = new Map();
  props.tableData.headers.forEach((header) => {
    headerSortedAscending.set(header, true);
  });

  function sortBy(header: string) {
    //for each header: create an ascending or descending var to check which way to sort by
    if (props.tableData.rows.length == 0) {
      //no need to run this function for a table with no rows
      return;
    }
    headerToSortBy = header;
    let newItemsArr = props.tableData.rows;
    let sortedAscending: boolean = false;
    if (/^\d+$/.test(newItemsArr[1][headerToSortBy])) {

      for (let i = 0; i < newItemsArr.length - 1; i++) {
        if (
          parseInt(newItemsArr[i][headerToSortBy]) <
          parseInt(newItemsArr[i + 1][headerToSortBy])
        ) {
          sortedAscending = true;
          break;
        }
      }
    } else {
      for (let i = 0; i < newItemsArr.length - 1; i++) {
        if (
          newItemsArr[i][headerToSortBy] < newItemsArr[i + 1][headerToSortBy]
        ) {
          sortedAscending = true;
          break;
        }
      }
    }

    if (sortedAscending) {
      newItemsArr.sort(compareDescending);
      // console.log("here")
      headerSortedAscending.set(header, false);
    } else {
      newItemsArr.sort(compareAscending);
      headerSortedAscending.set(header, true);
    }
    initializeItems(newItemsArr);
    setRowsToDisplay(items);
  }

  function compareAscending(
    row1: Record<string, string>,
    row2: Record<string, string>
  ) {
    if (/^\d+$/.test(row1[headerToSortBy])) {
      if (parseInt(row1[headerToSortBy]) < parseInt(row2[headerToSortBy])) {
        return -1;
      }
      if (parseInt(row1[headerToSortBy]) > parseInt(row2[headerToSortBy])) {
        return 1;
      }
      return 0;
    } else {
      if (row1[headerToSortBy] < row2[headerToSortBy]) {
        return -1;
      }
      if (row1[headerToSortBy] > row2[headerToSortBy]) {
        return 1;
      }
      return 0;
    }
  }

  function compareDescending(
    row1: Record<string, string>,
    row2: Record<string, string>
  ) {
    if (/^\d+$/.test(row1[headerToSortBy])) {
      if (parseInt(row1[headerToSortBy]) > parseInt(row2[headerToSortBy])) {
        return -1;
      }
      if (parseInt(row1[headerToSortBy]) < parseInt(row2[headerToSortBy])) {
        return 1;
      }
      return 0;
    } else {
      if (row1[headerToSortBy] > row2[headerToSortBy]) {
        return -1;
      }
      if (row1[headerToSortBy] < row2[headerToSortBy]) {
        return 1;
      }
      return 0;
    }
  }

  return (
    <div id="content">
      <table id="primaryTable" style={{ marginBottom: "1%", color: "white" }}>
        <tr id="headers" className="border">
          {tableHeaders}
        </tr>
        {items}
      </table>
      {props.tableDisplaying && (
        <AddRow
          headerNames={props.tableData.headers}
          tName={props.tableName}
          loadTable={props.loadTable}
        ></AddRow>
      )}
    </div>
  );
}
