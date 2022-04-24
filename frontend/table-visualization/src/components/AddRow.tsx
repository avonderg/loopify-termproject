import React from "react";

/**
 * This component handles the adding functionality and graphics for each row.
 */
export default function AddRow(props: {
  tName: string;
  headerNames: string[];
  loadTable: Function;
}) {
  const entryData: Map<string, string> = new Map();

  //set up the map representing the new row to be added
  for (const name of props.headerNames) {
    entryData.set(name, "");
  }

  //updates the new row to be added according to user input in the text boxes
  const update: Function = (header: string, value: string) => {
    entryData.set(header, value);
  };

  //sends the post API request to the add endpoint to add the row to the SQL database
  const sendReq = async () => {
    const addPostParams = {
      tableName: props.tName,
      entryData: Object.fromEntries(entryData),
    };

    const res: Response = await fetch("http://localhost:4567/insert", {
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

  return (
    <tr id={"addRow"}>
      {props.headerNames.map((header: string) => (
        <td>
          <input
            type="text"
            placeholder={header}
            onChange={(a) => update(header, a.target.value)}
          ></input>
        </td>
      ))}
      <td>
        <button onClick={() => sendReq()}>Add Row</button>
      </td>
    </tr>
  );
}
