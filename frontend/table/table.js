"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
//sets up loadButton, dropdown menu, and table element
const loadButton = document.getElementById("loader");
const dropdown = document.getElementById("dropdown");
const table = document.getElementById("displayTable");
let rowCount = 0;
//retrieves all the table names and puts it into a string array
fetch("http://localhost:4567/tableNames").then((res) => res.json()).then((tableNames) => updateDropdown(tableNames));
//makes an item in the dropdown menu for each table name & updates list when new db is loaded
function updateDropdown(names) {
    dropdown.innerHTML = "";
    dropdown.innerHTML += "<select name=\"tables\" id=\"tableNames\"></select>";
    let tableNamesList = document.getElementById("tableNames");
    names.forEach((table) => tableNamesList.innerHTML += `<option value=\"${table}\">${table}</option>`);
}
//wait for user to choose a specific table from the dropdown
loadButton.addEventListener("click", load);
//credit: Suraj A
function load() {
    return __awaiter(this, void 0, void 0, function* () {
        let tableNames = document.getElementById("tableNames");
        const TablePostParams = {
            name: tableNames.value,
        };
        const res = yield fetch("http://localhost:4567/table", {
            method: 'post',
            body: JSON.stringify(TablePostParams),
            headers: {
                'Content-Type': 'application/json; charset=UTF-8',
                "Access-Control-Allow-Origin": "*"
            }
        });
        // fetches the table data for the loaded sql database and table specified by dropdown from backend
        let tableData = yield res.json();
        updateTable(tableData);
    });
}
//reloads table
function updateTable(tableInfo) {
    table.innerHTML = "";
    let headers = table.insertRow();
    //uses helper to map column index to header name
    let headerNames = insertHeaders(tableInfo.headers, headers);
    console.log(headerNames);
    console.log(tableInfo.rows);
    insertRows(table, tableInfo.rows, headerNames);
}
//creates each header cell for a specific table
function insertHeaders(headers, headerRow) {
    let map = new Map();
    for (let i = 0; i < headers.length; i++) {
        let cell = headerRow.insertCell(i);
        cell.innerHTML = headers[i];
        map.set(i, headers[i]);
    }
    return map;
}
//loads the rows and content of a table
function insertRows(table, rows, headerMap) {
    for (let i = 0; i < rows.length; i++) {
        let row = table.insertRow();
        for (let entry of Array.from(headerMap.entries())) {
            let key = entry[0];
            console.log("key" + key);
            let value = entry[1];
            console.log("value: " + value);
            let cell = row.insertCell(key);
            let record = rows[i];
            console.log(record);
            console.log(key);
            console.log(record[value]);
            let cellInfo = document.createTextNode(record[value]);
            cell.appendChild(cellInfo);
        }
    }
}
