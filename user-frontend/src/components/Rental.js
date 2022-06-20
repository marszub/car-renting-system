import * as React from "react";
import { Container, padding } from "@mui/system";
import { Typography, TextField, Button } from "@mui/material";
import { Link } from "@mui/material";
import { createTheme, ThemeProvider } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { Box } from "@mui/material";
import { tokenStorage } from "../services/token-storage";
import { CarDBService } from "../services/carDB-service";
import { useState } from "react";
import { HTTP_BAD_REQUEST, HTTP_NO_CONTENT, HTTP_OK} from "../utils/http-status";
import { RentalService } from "../services/rental-service";
import { RentalView } from "./RentalView";
import { CarListView } from "./CarListView";

const theme = createTheme()

export class Rental extends React.Component {
    constructor() {
        super();
        this.state = { isRental: false, isLoading: true, rentalData: null };
        this.setRentalDataFunction = this.setRentalDataFunction.bind(this);
        this.setRentalFunction = this.setRentalFunction.bind(this);
        
        const serviceRental = new RentalService();

        serviceRental.getMyRental().then(res => {
            switch (res.status) {
                case HTTP_OK:
                    console.log("Got reservation data");
                    console.log(res.body);
                    this.setState({isRental: true, isLoading: false, rentalData: res.body});
                    break;
                case HTTP_NO_CONTENT:
                    console.log("User has no actvie rental");
                    this.setState({isRental: false, isLoading: false, rentalData: null});
                    break;
                case HTTP_BAD_REQUEST:
                    console.log("Bad request");
                    break;
                default:
                    console.log("Internal server error");
                    break;
        }});
    }


    setRentalFunction(Data) {
        this.setState({isRental: Data});
    }

    setRentalDataFunction(Data) {
        this.setState({rentalData: Data});
    }

    render() {
        if(!this.state.isLoading){
            if(this.state.isRental){
                return (<RentalView callbackIsRental = {this.setRentalFunction}
                                    callbackRentalData = {this.setRentalDataFunction}
                                    rentalData = {this.state.rentalData} ></RentalView>);
            } else {
                return (<CarListView callbackIsRental = {this.setRentalFunction}
                                    callbackRentalData = {this.setRentalDataFunction}
                                    rentalData = {this.state.rentalData} ></CarListView>);
                return(<a style={{textAlign: "center"}}>loading data...</a>);
            }
        }else return <a style={{textAlign: "center"}}>loading data...</a>; 
    }
}