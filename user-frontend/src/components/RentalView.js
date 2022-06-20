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

const theme = createTheme()

export class RentalView extends React.Component {
    constructor() {
        super();
    }
    
    endRental(rentalId, isRentalCallback, rentalDataCallback) {
        console.log("TSETT");
        const serviceRental = new RentalService();
        serviceRental.endRental(rentalId).then(res => {
            switch (res.status) {
                case HTTP_NO_CONTENT:
                    console.log("User has no actvie rental");
                    isRentalCallback(false);
                    rentalDataCallback(null);
                    break;
                case HTTP_BAD_REQUEST:
                    console.log("Bad request");
                    break;
                default:
                    console.log("Internal server error");
                    break;
        }})
    }

    render() {
        return(
        <Container>
            <Box sx={{
                textAlign: "center",
                marginTop: 8
            }}>
                <Typography component="h1" variant="h2">
                    Your current rental:
                </Typography>
                <Typography component="h1" variant="h2">
                    CarId: {this.props.rentalData.carId}
                </Typography>
                <Typography component="h1" variant="h2">
                    RentalId: {this.props.rentalData.reservationId}
                </Typography>
                <Typography component="h1" variant="h2">
                    RentalTime: {this.props.rentalData.reservationTimestamp}
                </Typography>
                <Button fullWidth variant="contained" onClick={() => {this.endRental(this.props.rentalData.reservationId, this.props.callbackIsRental, this.props.callbackRentalData)}}>
                    End rental
                </Button>
            </Box>
        </Container>)
    }
}