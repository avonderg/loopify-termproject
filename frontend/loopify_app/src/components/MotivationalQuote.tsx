import React, {useEffect, useState} from 'react';
import './MotivationalQuote.css';

function MotivationalQuote() {

    const [quote, setQuote] = useState("");
        // "Dead last finish is greater than did not finish, which trumps did not start."

    const [quotes, setQuotes] = useState([])
    const [isLoading, setIsLoading] = useState(false)


    useEffect(() => {
        getQuotes();
      }, []);

      const getQuotes = () => {
        fetch("https://type.fit/api/quotes")
          .then(function (response) {
            return response.json()
          })
          .then((data) => {
            setQuotes(data)
            console.log(quotes)
            console.log("hello")

            let max = data.length
            let randNum = Math.floor(Math.random() * max)
            let quote = data[randNum].text
            setQuote(quote)

            console.log(data.length)
            console.log(data)
            console.log(quote)

          })
      }

    
    // let randomQuote = fetch("https://type.fit/api/quotes")
    // .then(function(response) {
    //   return response.json();
    // })
    // .then(function(data) {
    //   console.log(data[0].text);
    // });

    // function getRandomQuote() {
    //     let max = apiQuotes.length
    //     let randNum = Math.floor(Math.random() * max)
    //     console.log("hellooo")
    //     console.log(randNum)
    //     console.log(apiQuotes.length)
    //     // let quote = apiQuotes[randNum]
    //     // setQuote(quote)
    // }
  
    
    // fetch("https://zenquotes.io/api/today", {
    //     method: 'GET',
    // }).then((r: { json: () => any; }) => r.json()).then((db: any) => getQuote(db))


    // function getQuote(db : any) {
    //     return db.toArray()[0]
    // }

  return (
      <div className='MotivationalQuote'>
          <h1>
              {quote}
          </h1>
      </div>
  );

}

export default MotivationalQuote;