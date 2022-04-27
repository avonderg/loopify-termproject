import React, { useState, useEffect } from 'react';


function FrontPage(){
    const [quote, setQuote] = useState("");

    
    useEffect(() => {
        // Update the document title using the browser API
        fetchQuote();
      });

    async function fetchQuote(){
        const myHeaders = new Headers();
        myHeaders.append('Content-Type', 'application/json; charset=UTF-8');
        myHeaders.append('Access-Control-Allow-Origin',"*");
        const q = await fetch("https://zenquotes.io/", {
            headers: myHeaders
        }).then((res) => res.json()).then((JSONres) => JSONres.q)
        setQuote(q);
    }
    return (
        <p>{quote}</p>
    );
}

export default FrontPage