// run tsc in this directory to compile to js before testing
type Table = {
   name: string,
   headers: string[],
   rows: Record<string, string>[]
 }

 type ServerError = {
   error: string
 }

 type TableName = {
    name: string,
 }

//sets up loadButton, dropdown menu, and table element
const loadButton : HTMLButtonElement = document.getElementById("loader") as HTMLButtonElement;
const dropdown : HTMLDivElement = document.getElementById("dropdown") as HTMLDivElement;
const table : HTMLTableElement = document.getElementById("displayTable") as HTMLTableElement;
let rowCount : number = 0;

//retrieves all the table names and puts it into a string array
fetch("http://localhost:4567/tableNames").then((res : Response) => res.json()).then((tableNames: string[]) => updateDropdown(tableNames))

//makes an item in the dropdown menu for each table name & updates list when new db is loaded
function updateDropdown(names : string[]) : void {
    dropdown.innerHTML = "";
    dropdown.innerHTML += "<select name=\"tables\" id=\"tableNames\"></select>";

    let tableNamesList : HTMLSelectElement = document.getElementById("tableNames") as HTMLSelectElement;
    names.forEach((table : string) => tableNamesList.innerHTML += `<option value=\"${table}\">${table}</option>`);
}

//wait for user to choose a specific table from the dropdown
loadButton.addEventListener("click", load)

//credit: Suraj A
async function load() : Promise<void> {
    let tableNames : HTMLSelectElement = document.getElementById("tableNames") as HTMLSelectElement;

    const TablePostParams : TableName = {
        name: tableNames.value,
    };

    const res : Response = await fetch("http://localhost:4567/table", {
    method: 'post',
    body: JSON.stringify(TablePostParams),
    headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        "Access-Control-Allow-Origin":"*"
        }
    });

    // fetches the table data for the loaded sql database and table specified by dropdown from backend
    let tableData : Table = await res.json();

    updateTable(tableData);
}

//reloads table
function updateTable(tableInfo : Table) : void {
    table.innerHTML = "";
    let headers : HTMLTableRowElement = table.insertRow();

    //uses helper to map column index to header name
    let headerNames : Map<number, string> = insertHeaders(tableInfo.headers, headers)

    console.log(headerNames)
    console.log(tableInfo.rows)
    insertRows(table, tableInfo.rows, headerNames)
}

//creates each header cell for a specific table
function insertHeaders(headers: string[], headerRow : HTMLTableRowElement) : Map<number, string> {
    let map : Map<number, string> = new Map<number, string>();

    for(let i = 0; i < headers.length; i ++) {
        let cell : HTMLTableCellElement = headerRow.insertCell(i)
        cell.innerHTML = headers[i];
        map.set(i, headers[i])
    }

    return map;
}

//loads the rows and content of a table
function insertRows(table : HTMLTableElement, rows : Record<string, string>[], headerMap: Map<number, String>) {
    for(let i = 0; i < rows.length; i++) {
        let row : HTMLTableRowElement = table.insertRow()
        for(let entry of Array.from(headerMap.entries())) {
            let key = entry[0];
            console.log("key" + key)

            let value = entry[1];
            console.log("value: " + value)

            let cell : HTMLTableCellElement = row.insertCell(key)
            let record : Record<string, string> = rows[i]

            console.log(record)
            console.log(key)
            console.log(record[value])

            let cellInfo = document.createTextNode(record[value]);
            cell.appendChild(cellInfo)
        }
    }
}
