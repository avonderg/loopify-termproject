import React, {useState} from 'react';
import { GoogleMap, useJsApiLoader , LoadScript} from '@react-google-maps/api';
import GoogleMapReact from 'google-map-react';
import Path from "./Path";


function MapContainer() {

    const mapStyles = {
        height: "100vh",
        width: "100%"
    };
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

  const apiKey = "zaCELgL.0imfnc8mVLWwsAawjYr4Rx-Af50DDqtlx"

  const getLocation = () => {
    let lat = 41.8268;
    let lng = 71.4025;
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        lat = position.coords.latitude;
        lng = position.coords.longitude;
      })}
    return {
      lat: lat,
      lng: lng
    }
  }

  return ( //isLoaded ? (
      <div>
        <p>
          Latitude:
          {getLocation().lat}
          <br/>
          Longitude:
          {getLocation().lng}
        </p>

        // Important! Always set the container height explicitly
        <div style={{ height: '100vh', width: '100%' }}>
          <GoogleMapReact
              bootstrapURLKeys={{ key: apiKey }}
              defaultCenter={getLocation()}
              defaultZoom={zoom}
          >
            <Path/>
          </GoogleMapReact>
        </div>

        {/*<LoadScript googleMapsApiKey={apiKey}>*/}
        {/*  <GoogleMapReact*/}
        {/*      mapContainerStyle={mapStyles}*/}
        {/*      center={getLocation()}*/}
        {/*      zoom={10}*/}
        {/*      // onLoad={onLoad}*/}
        {/*      // onUnmount={onUnmount}*/}
        {/*  >*/}
        {/*  </GoogleMapReact>*/}

        {/*</LoadScript>*/}
      </div>
  ) //: <></>

}

export default MapContainer;