import { Typography, Button } from "@mui/material";
import { Box } from "@mui/system";
import { Marker, InfoWindow } from "@react-google-maps/api"
import * as React from "react";
import { useState } from "react";
import { RentalService } from "../services/rental-service";
import { HTTP_BAD_REQUEST, HTTP_CREATED} from "../utils/http-status";


function openTooltip(setTooltipOpen) {
  setTooltipOpen(true);
}

function closeTooltip(setTooltipOpen) {
  setTooltipOpen(false);
}

function rentCar(carId, carTypeId, callbackIsRental, callbackRentalData) {
    const service = new RentalService();
    var body = {"carId": carId, "carTypeId": carTypeId};
    console.log(body);
    service.createRental(body).then(res => {
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

export default function CarMarker(props) {
  const [isTooltipOpen, setTooltipOpen] = useState(false);
  return(
      <Marker 
        position={ props.position }
        onClick={ () => {
            openTooltip(setTooltipOpen)
          } }
      >
        { isTooltipOpen &&
          <InfoWindow onCloseClick={() => closeTooltip(setTooltipOpen)}>
            <div>
              <Box sx={{textAlign: "center"}}>
                <Typography sx={{fontSize: 17}}>
                  {props.carData.name}
                </Typography>
                <Typography sx={{fontSize: 12}}>
                  Id: {props.carData.id}
                </Typography>
                <Button onClick={() => rentCar(props.carData.id,
                                               props.carData.categoryId,
                                               props.callbacksMap.isRental,
                                               props.callbacksMap.rentalData)}>
                    Rent
                </Button>
              </Box>
            </div>
          </InfoWindow>
        }
      </Marker>
  )
} 
