import React, {useState} from 'react';

//https://developers.google.com/maps/documentation/javascript/react-map

interface MapProps {
    lat : number;
    lng : number;
    zoom : number;
    miles : number;
    pathCoords : { lat: number; lng: number; }[];
}


function MapContainer(props:MapProps) {

    const ref = React.useRef<HTMLDivElement>(null);
    // @ts-ignore
    const [map, setMap] = React.useState<google.maps.Map>(null);

    const center: google.maps.LatLngLiteral = {lat: props.lat, lng: props.lng};

    const apiKey = process.env.LOOPIFY_APP_KEY || ""

    let path: google.maps.Polyline | null = null;

    const [miles, setMiles] = useState(0)
    const [zoom, setZoom] = useState(10)

    React.useEffect(() => {
        if (ref.current && !map) {
            setMap(new window.google.maps.Map(ref.current, {
                center: center,
                zoom: zoom
            }));
        }
    }, [ref, map]);

    /**
     * function that changes the mileage when the miles input box is changed.
     * clears the current path on the map (if there is one) and sets the zoom and
     * miles values
     * @param miles input number
     */
    function sendMileage(miles : string) {
        // remove previous path drawn
        if(path) {
            path.setMap(null)
        }

        // reset mileage and zoom value
        setMiles(+miles)
        setZoom(17-(0.4)*(+miles))

        // reset zoom level of map
        map.setZoom(zoom)
        console.log(zoom)
    }

    /**
     * function that clears the current route on the map, retrieves the
     * new path's coordinates from the backend, and draws the new path
     */
    function getRoute() {
        // if mileage inputted is not empty
        if(miles != 0) {

            // remove previous path
            if(path!=null){
                path.setMap(null)
                console.log("remove " + path);
            }

            // TODO: get route from backend, send miles and lat and lng
            let pathCoords = [
                { lat: 41.82564761175736, lng: -71.39906517404758 },
                { lat: 41.8274258402602, lng: -71.39933512294401 },
                { lat: 41.82728905520579, lng: -71.40056608991783 },
                { lat: 41.82558324056852, lng: -71.40037172671241 },
                { lat: 41.82564761175736, lng: -71.39906517404758 },
            ]

            // create new path's polyline
            path = new google.maps.Polyline({
                path: pathCoords,
                geodesic: true,
                strokeColor: "#ebf37b",
                strokeOpacity: 0.5,
                strokeWeight: 10,
            });

            // draw path on the map
            path.setMap(map)
            console.log("drew " + path)
        }
    }

    return (
        <div id="map container">

            <label> miles: </label>
            <input type="number" onChange={(e) => sendMileage(e.target.value)}/> <br/>

            <button id="goButton" onClick={getRoute}> go! </button> <br/>

            <div id="map" style={{height: '400px', width: '600px', margin: '30px auto'}} ref={ref}>
            </div>

        </div>
    )
}
export default MapContainer;