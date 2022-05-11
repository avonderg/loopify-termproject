import React from "react";
import "./QuoteContainer.css";
import MotivationalQuote from "./MotivationalQuote";

function QuoteContainer() {
  function scrollToRouteFinder() {
    window.scrollTo({
      top: document.documentElement.scrollHeight / 2.1,
      behavior: "smooth",
    });
  }

  return (
    <div className="QuoteContainer">
      <h2> Find your perfect route. </h2>

      <p> Need motivation? </p>

      <MotivationalQuote />

      <button onClick={scrollToRouteFinder} style={{ cursor: "pointer" }}>
        {" "}
        Get Started{" "}
      </button>

      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320">
        <path d="M0,128L60,122.7C120,117,240,107,360,122.7C480,139,600,181,720,197.3C840,213,960,203,1080,176C1200,149,1320,107,1380,85.3L1440,64L1440,320L1380,320C1320,320,1200,320,1080,320C960,320,840,320,720,320C600,320,480,320,360,320C240,320,120,320,60,320L0,320Z"></path>
      </svg>
    </div>
  );
}
export default QuoteContainer;
