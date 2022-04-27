import React, {useState} from 'react';
import './MotivationalQuote.css';

function MotivationalQuote() {

    const [quote, setQuote] =
        useState("Dead last finish is greater than did not finish, which trumps did not start.");

    let randomQuote = fetch("https://zenquotes.io/api/today", {
        method: 'GET',
    }).then((r: { json: () => any; }) => r.json()).then((db: any) => getQuote(db))


    function getQuote(db : any) {
        return db.toArray()[0]
    }

  return (
      <div className='MotivationalQuote'>
          <h1>
              {quote}
          </h1>
      </div>
  );

}

export default MotivationalQuote;