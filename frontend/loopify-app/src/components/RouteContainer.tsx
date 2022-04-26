import React from 'react';
import './RouteContainer.css'
import MapContainer from "./MapContainer";


function RouteContainer() {

    function sendMileage(miles : string) {

    }

    return (
        <div className="RouteContainer">
            <h2>
                find a loop
            </h2>

            <label> base: </label>
            <button> use my location </button> <br/>

            <label> miles: </label>
            <input
                type="text"
                onChange={(e) => sendMileage(e.target.value)}
            />

            <MapContainer/>

            <div className="Footer">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320">
                    <path
                        d="M0,64L60,85.3C120,107,240,149,360,170.7C480,192,600,192,720,181.3C840,171,960,149,1080,154.7C1200,160,1320,192,1380,208L1440,224L1440,320L1380,320C1320,320,1200,320,1080,320C960,320,840,320,720,320C600,320,480,320,360,320C240,320,120,320,60,320L0,320Z">
                    </path>
                </svg>
            </div>

        </div>

    )
}
export default RouteContainer;