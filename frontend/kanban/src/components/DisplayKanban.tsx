import React, { useEffect, useState } from 'react'
import Column from './Column.tsx'
import "./DisplayKanban.css"

export default function DisplayKanban(props: {kanban: Kanban}){
  return (
    <>
    <h1>{props.kanban.name}</h1>
    <div className="kanban">
      {props.kanban.columns.map((col) => <Column kanbanColumn={col}></Column>)}
    </div>
    </>
  )
}