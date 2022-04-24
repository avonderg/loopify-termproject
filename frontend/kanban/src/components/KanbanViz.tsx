import React from 'react';
import { useState } from 'react';
import DisplayKanban from './DisplayKanban.tsx';

export default function KanbanViz(){
    const [globalBlocks, setBlocks] = useState({headers: [], name: "", rows: []} as BlockTable)
    const [globalColumns, setColumns] = useState({headers: [], name: "", rows: []} as ColumnTable)
    const [kanbanData, setKanbanData] = useState({name: "", columns: []} as Kanban)

    // Searches for a word
    function searchWord(event){
        let filteredBlocks = Object.assign({}, globalBlocks, 
            {rows: globalBlocks.rows.filter((block: Block) => block.block_content.includes(event.target.value))})
        console.log(event.target.value)
        setKanban(filteredBlocks, globalColumns)
    }
    
    // Fetches all tables and sets the table data in the form of a kanban table
    async function fetchKanban(){
        let blockTable: BlockTable = await fetchTableData("block") as unknown as BlockTable
        let columnTable: ColumnTable = await fetchTableData("column") as unknown as ColumnTable
        let tagData = await fetchTableData("tags")
        setBlocks(blockTable)
        setColumns(columnTable)
        setKanban(blockTable, columnTable);
    }

    // Creates a kanban table given the blocks and columns
    function setKanban(blockTable, columnTable){
        let columns: KanbanColumn[] = columnTable.rows.map((row: KanbanColumn) => Object.assign({}, row, {blocks: blockTable.rows.filter((block: Block) => block.column === row.id)}))
        let kanban: Kanban = {name: "Kanban Table", columns: columns}
        setKanbanData(kanban)
    }

    /// fetches the table data for the specified table
    async function fetchTableData(tableName: string) {
        const TablePostParams : TableName = {
            name: tableName,
        };
    
        //API request to the table endpoint to fetch the given table
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
            console.log(tableData)
            return tableData;
    }

    return (
        <div> 
            <button onClick={fetchKanban}>Load Kanban table</button>
            <label htmlFor="search">Search for a word or phrase:</label>
            <input type="text" id="search" name="search" onChange={searchWord}></input>
            <DisplayKanban kanban={kanbanData}></DisplayKanban>
        </div>
    )
}