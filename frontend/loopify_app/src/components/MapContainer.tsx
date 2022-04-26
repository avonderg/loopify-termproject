import React from 'react';

import Path from "./Path";

//https://developers.google.com/maps/documentation/javascript/react-map

interface MapProps {
    lat : number;
    lng : number;
}


function MapContainer(props:MapProps) {

    const ref = React.useRef<HTMLDivElement>(null);
    const [map, setMap] = React.useState<google.maps.Map>();

    const zoom = 10;
    const apiKey = process.env.LOOPIFY_APP_KEY || ""
    const center: google.maps.LatLngLiteral = {lat: props.lat, lng: props.lng};

    React.useEffect(() => {
        if (ref.current && !map) {
            setMap(new window.google.maps.Map(ref.current, {
                center: center,
                zoom: zoom
            }));
        }
    }, [ref, map]);


    return (
            <div id="map" style={{height: '400px', width: '600px', margin: '30px auto'}} ref={ref}/>
    )
}
export default MapContainer;