import { GoogleMap, useLoadScript, MarkerF } from "@react-google-maps/api"
import * as React from "react";
import { API_KEY } from "../config";
import { useMemo } from  "react";
import "../styles/car-map-style.css";
import CarMarker from "./CarMarker";
import { Grid, TextField, Button } from "@mui/material";
import { useState } from "react";
import { coordinatesValidators } from "../validators/coordinates-validators";

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
            position = {{lat: cars[i].coordinates.latitude, lng: cars[i].coordinates.longitude}}
            carData = {{name: cars[i].carName, id: cars[i].id, categoryId: cars[i].carCategory.id}}
            callbacksMap = {callbacks}
            key = {cars[i].id}
        />);
    }
    return(list);
}

function transformData(data) {
    var newData = data.split(', ');
    if(newData.length != 2) {
        return "Wrong data format";
    }
    return {lat: newData[0], lng: newData[1]};
}

function Map(props) {
    const [position, setPosition] = useState({ lat: 49.8066, lng: 19.04805 })
    const [inputError, setInputError] = useState("");

    const handleSubmit = (event) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);
        var coordinates = transformData(formData.get("position"));
        if(coordinates == "Wrong data format") {
            setInputError(coordinates);
            return;
        }

        coordinates = {lat: parseFloat(coordinates.lat), lng: parseFloat(coordinates.lng)};

        var errorMessage = coordinatesValidators.validateLatitude(coordinates.lat)
        setInputError(errorMessage);
        if(errorMessage)
            return;
        errorMessage = coordinatesValidators.validateLongitude(coordinates.lng)
        setInputError(errorMessage);
        if(errorMessage)
            return;
        
        setPosition(coordinates);
    }
    
    const defineClass = (message) => {
        if(message == "") {
            return "map-container"; 
        } 
        return "map-container2";
    }

    return(
        <div>
            <Grid component="form" onSubmit={handleSubmit}
                  container spacing={3} style={{marginTop: "64px", textAlign: "center"}}>
                <Grid item xs={3}/>
                <Grid item xs={6}>
                    <TextField
                        autoComplete="off" 
                        autoFocus 
                        name="position" 
                        required 
                        defaultValue={position.lat.toString() + ", " + position.lng.toString()}
                        style={{width: "40%"}}
                        error={inputError != ""}
                        helperText={inputError}
                        label="Current location" 
                        id="position"/>
                    <Button type="submit" style={{height: "56px"}}>
                        Set location
                    </Button>
                </Grid>
                <Grid item xs={3}/>
            </Grid>
            <GoogleMap
                zoom = { 11 }
                center = { position }
                mapContainerClassName = { defineClass(inputError) }
            >
                {spawnCars(props.cars, props.callbacksMap)}
            </GoogleMap>
        </div>
    );
}
