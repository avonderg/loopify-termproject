import React, {useState} from 'react';
import './RouteContainer.css'
import MapContainer from "./MapContainer";
import { Wrapper, Status } from "@googlemaps/react-wrapper";
import Footer from "./Footer";


function RouteContainer() {

    const apiKey = process.env.LOOPIFY_APP_KEY || ""

    const render = (status: Status) => {
        return <h1>{status}</h1>;
    };

    return (
        <div className="RouteContainer">

            <h2> find a loop </h2>

            <Wrapper apiKey={"AIzaSyBl5KAwLFMirggDCvul35kcVDtFeIdRIe8"} render={render}>
                <MapContainer/>
            </Wrapper>

            <Footer/>
        </div>

    )
}
export default RouteContainer;