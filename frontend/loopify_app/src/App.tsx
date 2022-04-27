import React from 'react';
import './App.css';
import './components/Path'
import MapContainer from "./components/MapContainer";
import Title from "./components/Title";
import QuoteContainer from "./components/QuoteContainer";
import RouteContainer from "./components/RouteContainer";


function App() {


  return (
    <div className="App">
        <Title/>
        <QuoteContainer/>
        <RouteContainer/>
    </div>
  );
}

export default App;
