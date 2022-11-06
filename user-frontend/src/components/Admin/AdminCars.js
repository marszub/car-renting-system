import * as React from "react";
import { CarDBService } from "../../services/carDB-service";
import { HTTP_BAD_REQUEST, HTTP_CREATED, HTTP_OK} from "../../utils/http-status";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Typography } from "@mui/material";
import { Container, Box } from "@mui/system";
import AdminCarsTable from "./AdminCarsTable";
import Loading from "../Loading";

export default function AdminCars() {
    const carService = new CarDBService();

    const navigate = useNavigate();
    const [carData, setCarData] = useState(null);

    if(carData == null) {
        carService.adminCarList().then(res => {
            switch (res.status) {
                case HTTP_OK:
                    console.log("Got cars data");
                    setCarData(res.body);
                    break;
                case HTTP_BAD_REQUEST:
                    console.log("Bad request");
                    break;
                default:
                    console.log("Internal server error");
                    navigate("/");
                    break;
        }}).catch(err => {
            console.log("Error while extracting carCategories list");
            navigate("/");
        });
    }

    if(carData == null) {
       return(<Loading/>);
    }
    return(
        <Container sx = {{marginTop: 8}}>
            <Box sx = {{
                paddingTop: 2,
                textAlign: "center"
            }}>
                <Typography component="h1" variant="h3">Available cars</Typography>
                <AdminCarsTable cars = {carData} carDataCallback = {setCarData}></AdminCarsTable>
            </Box>
        </Container>
    );
}
