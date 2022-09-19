import { GoogleMap, useLoadScript, MarkerF } from "@react-google-maps/api"
import * as React from "react";
import { API_KEY } from "../config";
import { useMemo } from  "react";
import "../styles/car-map-style.css";
import CarMarker from "./CarMarker";

export default function CarMap(props) {
    const { isLoaded } = useLoadScript({
        googleMapsApiKey: API_KEY
    });

    if (!isLoaded) return <div>Loading...</div>
    return <Map cars = {props.cars}
                callbacksMap = {props.callbacksMap}/> 
}

function spawnCars(cars, callbacks) {
    var list = [];
    for(var i = 0; i < cars.length; i++) {
        list.push(
        <CarMarker
            position = {{lat: cars[i].latitude, lng: cars[i].longitude}}
            carData = {{name: cars[i].carName, id: cars[i].id}}
            callbacksMap = {callbacks}
            key = {cars[i].id}
        />);
    }
    return(list);
}

function Map(props) {
    const center = useMemo(() => ({ lat: 49.8066, lng: 19.04805 }), [])

    return(
        <GoogleMap
            zoom = { 11 }
            center = { center }
            mapContainerClassName = "map-container"
        >
            {spawnCars(props.cars, props.callbacksMap)}
        </GoogleMap>
    );
}
