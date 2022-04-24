import React, { useState } from "react";

/**
 * This component deals with the row deleteing functionality for each row.
 */

export default function UpdateRow(props: {
  tName: string;
  headerNames: string[];
  row: Record<string, string>;
  loadTable: Function;
}) {
  const originalData: Map<string, string> = new Map();

  for (const name of props.headerNames) {
    originalData.set(name, props.row[name]);
  }

  const sendReq = async () => {
    //post params for the delete post request
    const addPostParams = {
      tableName: props.tName,
      deleteConditions: Object.fromEntries(originalData),
    };

    //API call to the delete endpoint
    const res: Response = await fetch("http://localhost:4567/delete", {
      method: "post",
      body: JSON.stringify(addPostParams),
      headers: {
        "Content-Type": "application/json; charset=UTF-8",
        "Access-Control-Allow-Origin": "*",
      },
    });

    // gets resp msg
    let msg: string = await res.json();
    console.log(msg);

    props.loadTable(props.tName);
  };
  return (
    <td>
      <button onClick={() => sendReq()}>Delete Row</button>
    </td>
  );
}
