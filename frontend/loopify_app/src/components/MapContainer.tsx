import React, { useState } from "react";
import "./MapContainer.css";

//https://developers.google.com/maps/documentation/javascript/react-map

interface MapProps {
  lat: number;
  lng: number;
  zoom: number;
  miles: number;
  pathCoords: { lat: number; lng: number }[];
}

type RouteInfo = {
  distance: number;
  coords: google.maps.LatLng[];
};

function MapContainer(props: MapProps) {
  const ref = React.useRef<HTMLDivElement>(null);

  // @ts-ignore
  const [map, setMap] = React.useState<google.maps.Map>(null);
  const [miles, setMiles] = useState(0);
  const [zoom, setZoom] = useState(16.6);
  const [lat, setLat] = useState(41.8268);
  const [lng, setLng] = useState(-71.4025);
  const [dist, setDist] = useState(0);
  const apiKey = process.env.LOOPIFY_APP_KEY || "";

  let path: google.maps.Polyline | null = null;
  let loc: google.maps.Marker | null = null;

  React.useEffect(() => {
    if (ref.current && !map) {
      setMap(
        new window.google.maps.Map(ref.current, {
          center: { lat: lat, lng: lng },
          zoom: zoom,
        })
      );
    }
  }, [ref, map]);

  /**
   * function that changes the mileage when the miles input box is changed.
   * clears the current path on the map (if there is one) and sets the zoom and
   * miles values
   * @param miles input number
   */
  function sendMileage(miles: string) {
    // remove previous path drawn
    if (path) {
      path.setMap(null);
    }

    // reset mileage and zoom value
    setMiles(+miles);
    setZoom(15 - 0.4 * +miles);

    // reset zoom level of map
    map.setZoom(zoom);
    console.log(zoom);
  }

  /**
   * function that clears the current route on the map, retrieves the
   * new path's coordinates from the backend, and draws the new path
   */
  async function getRoute() {
    // if mileage inputted is not empty
    if (miles != 0) {
      // remove previous path
      if (path != null) {
        path.setMap(null);
        console.log("remove " + path);
      }

      // get route from backend
      let info: RouteInfo = { distance: 0, coords: [] };
      await getRouteInfo().then((value) => (info = value));

      let pathCoords: google.maps.LatLng[] = info.coords;
      console.log(pathCoords);
      console.log(info.distance);
      setDist(info.distance);
      console.log(dist);

      // [{ lat: 41.82564761175736, lng: -71.39906517404758 },
      // { lat: 41.8274258402602, lng: -71.39933512294401 },
      // { lat: 41.82728905520579, lng: -71.40056608991783 },
      // { lat: 41.82558324056852, lng: -71.40037172671241 },
      // { lat: 41.82564761175736, lng: -71.39906517404758 }]

      // create new path's polyline
      path = new google.maps.Polyline({
        path: pathCoords, //route.coords,
        geodesic: true,
        strokeColor: "#ea4435",
        strokeOpacity: 0.8,
        strokeWeight: 10,
      });

      // draw path on the map
      path.setMap(map);
      console.log("drew " + path);
    }
  }

  /**
   * Loads the dropdown upon page loading.
   */
  async function getRouteInfo() {
    return new Promise<RouteInfo>(async (resolve) => {
      const userRouteRequest: number[] = [lat, lng, miles];
      const res: Response = await fetch("http://localhost:4567/getRoute", {
        method: "post",
        body: JSON.stringify(userRouteRequest),
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
          "Access-Control-Allow-Origin": "*",
        },
      });
      let route: RouteInfo = { distance: 0, coords: [] };
      let routeData: void = await res.json().then((value) => {
        route = value;
      });
      if (route.coords.length > 0) {
        resolve(route);
      }
    });
  }

  /**
   * function that retrieves user's current location
   */
  async function getCurLoc() {
    console.log(loc);

    // remove previous marker
    if (loc) {
      loc.setMap(null);
      console.log("remove " + loc);
    }
    if (path) {
      path.setMap(null);
    }

    if (navigator.geolocation) {
      await accessLoc();

      // create new marker
      loc = new google.maps.Marker({
        position: { lat: lat, lng: lng },
      });

      // draw path on the map
      loc.setMap(map);
      if (path) {
        path.setMap(null);
      }
      map.setCenter({ lat, lng });
      console.log("drew " + loc);
    }
  }

  /**
   * async function that return a promise after current location
   * has been udpated to user's actual location
   */
  async function accessLoc() {
    return new Promise<void>((resolve) => {
      navigator.geolocation.getCurrentPosition((position) => {
        setLat(position.coords.latitude);
        setLng(position.coords.longitude);
      });
      if (lat != 41.82868 && lng != -71.4025) {
        resolve();
      }
    });
  }

  return (
    <div id="map container">
      <label> base: </label>
      <button onClick={getCurLoc} id="locationButton">
        {" "}
        use my location{" "}
      </button>{" "}
      <br />
      <label> miles: </label>
      <input
        type="number"
        min="0"
        onChange={(e) => sendMileage(e.target.value)}
      />
      <br />
      <button id="goButton" onClick={getRoute}>
        {" "}
        go!{" "}
      </button>{" "}
      <br />
      <div
        id="map"
        style={{ height: "400px", width: "600px", margin: "30px auto" }}
        ref={ref}
      ></div>
      <p> route distance: {Math.round(100 * dist) / 100} miles </p>
    </div>
  );
}
export default MapContainer;
