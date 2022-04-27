import React from 'react';

import Path from "./Path";
import {Status, Wrapper} from "@googlemaps/react-wrapper";

//https://developers.google.com/maps/documentation/javascript/react-map

interface MapProps {
    lat : number;
    lng : number;
    zoom : number;
    pathCoords : { lat: number; lng: number; }[];
}


function MapContainer(props:MapProps) {

    const ref = React.useRef<HTMLDivElement>(null);
    // @ts-ignore
    const [map, setMap] = React.useState<google.maps.Map>(null);

    const center: google.maps.LatLngLiteral = {lat: props.lat, lng: props.lng};

    const apiKey = process.env.LOOPIFY_APP_KEY || ""


    React.useEffect(() => {
        if (ref.current && !map) {
            setMap(new window.google.maps.Map(ref.current, {
                center: center,
                zoom: props.zoom
            }));
        }
    }, [ref, map]);

    const path = new google.maps.Polyline({
        path: props.pathCoords,
        geodesic: true,
        strokeColor: "#f5f542",
        strokeOpacity: 0.3,
        strokeWeight: 10,
    });

    path.setMap(map)


    return (
            <div id="map" style={{height: '400px', width: '600px', margin: '30px auto'}} ref={ref}>
            </div>
    )
}
export default MapContainer;