import React, {useEffect, useState} from 'react';
import './MotivationalQuote.css';

function MotivationalQuote() {

    const [quote, setQuote] = useState("");


    useEffect(() => {
        getQuotes();
    }, []);

    const getQuotes = () => {
        fetch("https://type.fit/api/quotes")
            .then(function (response) { return response.json()})
            .then((data) => { setQuote(data[Math.floor(Math.random() * data.length)].text) })
    }

  return (
      <div className='MotivationalQuote'>
          <h1> {quote} </h1>
      </div>
  );

}

export default MotivationalQuote;