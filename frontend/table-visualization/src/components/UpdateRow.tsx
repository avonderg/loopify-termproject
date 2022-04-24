import React, { useState } from "react";
import "./DisplayTable.css";

/**
 * This component handles the updating functionality and graphics for each row.
 */
export default function UpdateRow(props: {
  tName: string;
  headerNames: string[];
  row: Record<string, string>;
  loadTable: Function;
}) {
  const originalData: Map<string, string> = new Map();
  const updateData: Map<string, string> = new Map();

  //set up the original and updated row hashmaps to be used for the update API call
  for (const name of props.headerNames) {
    originalData.set(name, props.row[name]);
    updateData.set(name, props.row[name]);
  }

  //fills in the update map with the values to update
  const update: Function = (header: string, value: string) => {
    updateData.set(header, value);
  };

  //send the post API request to the update endpoint
  const sendReq = async () => {
    const addPostParams = {
      tableName: props.tName,
      colNameToOldVal: Object.fromEntries(originalData),
      colNameToNewVal: Object.fromEntries(updateData),
    };

    const res: Response = await fetch("http://localhost:4567/update", {
      method: "post",
      body: JSON.stringify(addPostParams),
      headers: {
        "Content-Type": "application/json; charset=UTF-8",
        "Access-Control-Allow-Origin": "*",
      },
    });

    // gets resp msg
    let msg: string = await res.json();
    props.loadTable(props.tName);
  };

  //html outline for the updates to return
  return (
    <td>
      {props.headerNames.map((header: string) => (
        <td>
          <input
            type="text"
            placeholder={props.row[header]}
            onChange={(a) => update(header, a.target.value)}
          ></input>
        </td>
      ))}
      <td>
        <button onClick={() => sendReq()}>Update Row</button>
      </td>
    </td>
  );
}
