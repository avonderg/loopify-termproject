import React from 'react';
import { GoogleMap, useJsApiLoader , LoadScript} from '@react-google-maps/api';
import GoogleMapReact, {BootstrapURLKeys} from 'google-map-react';
import Path from "./Path";
import mapExample from "/Users/kleoku/term-project-avonderg-bpiekarz-jurrutic-kku2-sgundotr/frontend/loopify-app/src/images/figmaMap.png"

// https://www.npmjs.com/package/google-map-react
// --> google-map-react
// https://medium.com/@yelstin.fernandes/render-a-map-component-using-react-google-maps-5f7fb3e418bb
// --> react-google-maps
// https://medium.com/@allynak/how-to-use-google-map-api-in-react-app-edb59f64ac9d
// --> react-google-maps with components
// https://developers.google.com/maps/get-started#api-key
// --> api key



function MapContainer() {

    const zoom = 10;

    // const { isLoaded } = useJsApiLoader({
    //   id: 'google-map-script',
    //   googleMapsApiKey: "YOUR_API_KEY"
    // })

    // const [map, setMap] = React.useState(null)
    //
    // const onLoad = React.useCallback(function callback(map) {
    //   const bounds = new window.google.maps.LatLngBounds();
    //   map.fitBounds(bounds);
    //   setMap(map)
    // }, [])
    //
    // const onUnmount = React.useCallback(function callback(map) {
    //   setMap(null)
    // }, [])

    const apiKey = "AIzaSyBl5KAwLFMirggDCvul35kcVDtFeIdRIe8"

    const getLocation = () => {
        let lat = 41.8268;
        let lng = 71.4025;
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition((position) => {
                lat = position.coords.latitude;
                lng = position.coords.longitude;
            })
        }
        return {
            lat: lat,
            lng: lng
        }
    }

    return (
        <div>
            <div style={{height: '400px', width: '100%'}}>
                <div className="Map">
                    <img src= {mapExample} style={{width:'500px', height:'auto', margin: '50px', overflow: 'hidden'}}/>
                </div>
                {/*<GoogleMapReact*/}
                {/*    bootstrapURLKeys={{key: apiKey}}*/}
                {/*    defaultCenter={getLocation()}*/}
                {/*    defaultZoom={zoom}*/}
                {/*/>*/}
            </div>
        </div>
    )
}
export default MapContainer;