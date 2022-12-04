import * as React from "react";
import { CarDBService } from "../../services/carDB-service";
import { CarManagerService } from "../../services/carManager-service";
import { HTTP_BAD_REQUEST, HTTP_CREATED, HTTP_OK} from "../../utils/http-status";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Typography } from "@mui/material";
import { Container, Box } from "@mui/system";
import AdminCarsTable from "./AdminCarsTable";
import HttpPromise from "../../utils/HttpPromise";
import Loading from "../Loading";

export default function AdminCars() {
    const carService = new CarDBService();
    const carManagerService = new CarManagerService();

    const navigate = useNavigate();
    const [carData, setCarData] = useState(null);
    const [carTokensData, setCarTokensData] = useState(null);

    const carsListError = () => {
        console.log("Error while extracting Cars list");
        navigate("/admin/error");
    }

    const carsTokenListError = () => {
        console.log("Error while extracting cars token list");
        navigate("/admin/error");
    }

    if(carData == null && carTokensData == null) {
        Promise.all([
            HttpPromise(5000,
                carService.adminCarList().then(res => {
                    switch (res.status) {
                        case HTTP_OK:
                            console.log("Got cars data");
                            console.log(res.body);
                            setCarData(res.body);
                            break;
                        case HTTP_BAD_REQUEST:
                            console.log("Bad request");
                            break;
                        default:
                            console.log("Internal server error");
                            navigate("/admin/error");
                            break;
                }}).catch(carsListError),
            carsListError),
            HttpPromise(5000,
                carManagerService.adminCarList().then(res => {
                    switch (res.status) {
                        case HTTP_OK:
                            console.log("Got car tokens data");
                            console.log(res.body);
                            setCarTokensData(res.body);
                            break;
                        case HTTP_BAD_REQUEST:
                            console.log("Bad request");
                            break;
                        default:
                            console.log("Internal server error");
                            navigate("/admin/error");
                            break;
                    }
                }).catch(carsTokenListError),
            carsTokenListError)
        ])
    }

    if(carData == null || carTokensData == null) {
       return(<Loading/>);
    }
    return(
        <Container sx = {{marginTop: 8}}>
            <Box sx = {{
                paddingTop: 2,
                textAlign: "center"
            }}>
                <Typography component="h1" variant="h3">Available cars</Typography>
                <AdminCarsTable cars = {carData} carTokens = {carTokensData} carTokensCallback = {setCarTokensData} carDataCallback = {setCarData}></AdminCarsTable>
            </Box>
        </Container>
    );
}
