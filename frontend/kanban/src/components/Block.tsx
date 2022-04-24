import React from 'react';
import './Block.css'

export default function Block(props: {blockData: Block}){
    return (
        <div className='block'>
            <h3>{props.blockData.block_title}</h3>
            <p>{props.blockData.block_content}</p>
        </div>
    )
}