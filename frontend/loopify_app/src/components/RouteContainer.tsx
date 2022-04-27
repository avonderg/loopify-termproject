import React, {useState} from 'react';
import './RouteContainer.css'
import MapContainer from "./MapContainer";
import { Wrapper, Status } from "@googlemaps/react-wrapper";


function RouteContainer() {

    const [lat, setLat] = useState(41.8268)
    const [lng, setLng] = useState(-71.4025)
    const [zoom, setZoom] = useState(15)
    const [pathCoords, setPathCoords] = useState<{ lat: number; lng: number; }[]>([])
    const [miles, setMiles] = useState(0)
    const apiKey = process.env.LOOPIFY_APP_KEY || ""


    function sendMileage(miles : string) {
        setMiles(+miles)
        setZoom(+miles)
    }

    function getRoute() {
        // TODO: get route from backend, send miles and lat and lng
        setPathCoords([
            { lat: 41.82564761175736, lng: -71.39906517404758 },
            { lat: 41.8274258402602, lng: -71.39933512294401 },
            { lat: 41.82728905520579, lng: -71.40056608991783 },
            { lat: 41.82558324056852, lng: -71.40037172671241 },
            { lat: 41.82564761175736, lng: -71.39906517404758 },
        ])
    }

    function getCurLoc() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition((position) => {
                setLat(position.coords.latitude);
                setLng(position.coords.longitude);
            })
        }
    }

    const render = (status: Status) => {
        return <h1>{status}</h1>;
    };

    return (
        <div className="RouteContainer">
            <h2> find a loop </h2>

            <label> base: </label>
            <button onClick={getCurLoc}> use my location </button> <br/>

            <label> miles: </label>
            <input type="number" onChange={(e) => sendMileage(e.target.value)}/> <br/>


            <button id="goButton" onClick={getRoute}> go! </button> <br/>

            <Wrapper apiKey={"AIzaSyBl5KAwLFMirggDCvul35kcVDtFeIdRIe8"} render={render}>
                <MapContainer
                    lat = {lat}
                    lng = {lng}
                    pathCoords = {pathCoords}
                    zoom = {zoom}
                />
            </Wrapper>


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