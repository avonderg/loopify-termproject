import React from 'react';
import './QuoteContainer.css';
import MotivationalQuote from "./MotivationalQuote";


function QuoteContainer() {

    function scrollToRouteFinder() {
        window.scrollTo({
            top: document.documentElement.scrollHeight,
            behavior: 'smooth'
        });
    }

    return (
        <div className="QuoteContainer">
            <h2> Find your perfect route. </h2>

            <p> Need motivation? </p>

            <MotivationalQuote/>

            <button onClick={scrollToRouteFinder}> Get Started </button>

            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320">
                <path
                    //d="M0,96L48,122.7C96,149,192,203,288,218.7C384,235,480,213,576,197.3C672,181,768,171,864,192C960,213,1056,267,1152,277.3C1248,288,1344,256,1392,240L1440,224L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z">
                    d="M0,128L60,122.7C120,117,240,107,360,122.7C480,139,600,181,720,197.3C840,213,960,203,1080,176C1200,149,1320,107,1380,85.3L1440,64L1440,320L1380,320C1320,320,1200,320,1080,320C960,320,840,320,720,320C600,320,480,320,360,320C240,320,120,320,60,320L0,320Z">
                </path>
            </svg>
        </div>
    )
}
export default QuoteContainer;