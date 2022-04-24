
type TableName = {
    name: string,
  }
  

type Table = {
    name: string,
    headers: string[],
    rows: Record<string, string>[]
}

type BlockTable = {
    headers: string[];
    name: string,
    rows: Block[]
}
  
type ColumnTable = {
    headers: string[];
    name: string,
    rows: KanbanColumn[]
}

type Block = {
    block_id: number,
    block_title: string,
    block_content: string,
    block_date: string,
    column: number
}

type KanbanColumn = {
    id: number,
    name: string,
    index: number,
    blocks: Block[]
}

type Kanban = {
    name: string,
    columns: KanbanColumn[]
}