import * as React from "react";
import { createTheme } from "@mui/material";
import CarMap from "./CarMap.js"

const theme = createTheme()

export class CarList extends React.Component {
    constructor() {
        super();
    }

    render() {
        return(
            <CarMap cars = {this.props.cars.cars}
                    callbacksMap = {{isRental: this.props.callbackIsRental,
                                     rentalData: this.props.callbackRentalData }}/>)
    }
}
