import React, { useState } from "react";

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
    setZoom(17 - 0.4 * +miles);

    // reset zoom level of map
    map.setZoom(zoom);
    console.log(zoom);
  }

  /**
   * function that clears the current route on the map, retrieves the
   * new path's coordinates from the backend, and draws the new path
   */
  function getRoute() {
    // if mileage inputted is not empty
    if (miles != 0) {
      // remove previous path
      if (path != null) {
        path.setMap(null);
        console.log("remove " + path);
      }

      let route: RouteInfo = { distance: 0, coords: [] };

      let routeData = getRouteInfo().then((value) => (route = value));
      console.log(route.coords);

      // TODO: get route from backend, send miles and lat and lng
      //let pathCoords = routeData.coords;
      // { lat: 41.82564761175736, lng: -71.39906517404758 },
      // { lat: 41.8274258402602, lng: -71.39933512294401 },
      // { lat: 41.82728905520579, lng: -71.40056608991783 },
      // { lat: 41.82558324056852, lng: -71.40037172671241 },
      // { lat: 41.82564761175736, lng: -71.39906517404758 },

      // create new path's polyline
      path = new google.maps.Polyline({
        path: route.coords,
        geodesic: true,
        strokeColor: "#ebf37b",
        strokeOpacity: 0.5,
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
    const userRouteRequest: number[] = [lat, lng, miles];
    const res: Response = await fetch("http://localhost:4567/getRoute", {
      method: "post",
      body: JSON.stringify(userRouteRequest),
      headers: {
        "Content-Type": "application/json; charset=UTF-8",
        "Access-Control-Allow-Origin": "*",
      },
    });
    let routeData: RouteInfo = await res.json();
    return routeData;
    console.log("route distance:");
    console.log(routeData.distance);
    console.log(routeData.coords);
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

    if (navigator.geolocation) {
      await accessLoc();

      // create new marker
      loc = new google.maps.Marker({
        position: { lat: lat, lng: lng },
      });

      // draw path on the map
      loc.setMap(map);
      console.log("drew " + loc);
    }
  }

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
    </div>
  );
}
export default MapContainer;
