import * as React from "react";
import { Typography, Button, CssBaseline } from "@mui/material";
import { Box } from "@mui/material";
import { HTTP_BAD_REQUEST, HTTP_CREATED, HTTP_OK} from "../utils/http-status";
import { RentalService } from "../services/rental-service";

function rentCar(carId, callbackIsRental, callbackRentalData) {
    const service = new RentalService();
    service.createRental({"carId": carId}).then(res => {
        switch (res.status) {
            case HTTP_CREATED:
                console.log("rental createad");
                callbackIsRental(true);
                callbackRentalData(res.body);
                break;
            case HTTP_BAD_REQUEST:
                console.log("Bad request");
                break;
            default:
                console.log("Internal server error");
                break;
    }}).catch(err => {
        console.log("Unabled to connect to rental microservice")
    })

}

export default function CarInformation(props) {
    return(
        <Box>
            <Typography>
                CarId: {props.carData.id}
            </Typography>
            <Typography>
                CarName: {props.carData.carName}
            </Typography>
            <Button onClick={() => rentCar(props.carData.id, props.callbackIsRental, props.callbackRentalData)}>
                Rent
            </Button>
            <hr></hr>
        </Box>
    )
}
