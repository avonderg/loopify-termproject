import React, {useState} from 'react';
import './RouteContainer.css'
import MapContainer from "./MapContainer";
import { Wrapper, Status } from "@googlemaps/react-wrapper";
import Footer from "./Footer";


function RouteContainer() {

    const [lat, setLat] = useState(41.8268)
    const [lng, setLng] = useState(-71.4025)
    const [zoom, setZoom] = useState(15)
    const [pathCoords, setPathCoords] = useState<{ lat: number; lng: number; }[]>([])
    const [miles, setMiles] = useState(0)
    const apiKey = process.env.LOOPIFY_APP_KEY || ""


    function sendMileage(miles : string) {
        setPathCoords([])
        setMiles(+miles)
    }

    // function getRoute() {
    //     if (miles != 0) {
    //         setZoom(+miles)
    //         console.log(zoom);
    //         // TODO: get route from backend, send miles and lat and lng
    //         setPathCoords([
    //             { lat: 41.82564761175736, lng: -71.39906517404758 },
    //             { lat: 41.8274258402602, lng: -71.39933512294401 },
    //             { lat: 41.82728905520579, lng: -71.40056608991783 },
    //             { lat: 41.82558324056852, lng: -71.40037172671241 },
    //             { lat: 41.82564761175736, lng: -71.39906517404758 },
    //         ])
    //     }
    // }

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
            <button onClick={getCurLoc} id="locationButton" > use my location  </button> <br/>

            {/*<label> miles: </label>*/}
            {/*<input type="number" onChange={(e) => sendMileage(e.target.value)}/> <br/>*/}

            <Wrapper apiKey={"AIzaSyBl5KAwLFMirggDCvul35kcVDtFeIdRIe8"} render={render}>
                <MapContainer
                    lat = {lat}
                    lng = {lng}
                    pathCoords = {pathCoords}
                    zoom = {zoom}
                    miles = {miles}
                />
            </Wrapper>

            <Footer/>
        </div>

    )
}
export default RouteContainer;