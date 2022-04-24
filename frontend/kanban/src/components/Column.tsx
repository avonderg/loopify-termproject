import React from "react"
import Block from "./Block.tsx"
import "./Column.css"

export default function Column(props : {kanbanColumn: KanbanColumn}){
    return (
        <div className="column">
            <h1>{props.kanbanColumn.name}</h1>
            {props.kanbanColumn.blocks.map((block: Block) => <Block blockData={block}></Block>)}
        </div>
    )
}