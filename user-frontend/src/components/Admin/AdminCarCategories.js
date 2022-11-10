import * as React from "react";
import { CarDBService } from "../../services/carDB-service";
import { TarrifService } from "../../services/tarrif-service";
import { HTTP_BAD_REQUEST, HTTP_OK} from "../../utils/http-status";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Typography } from "@mui/material";
import { Container, Box } from "@mui/system";
import AdminCarCategoriesTable from "./AdminCarCategoriesTable";
import Loading from "../Loading";
import HttpPromise from "../../utils/HttpPromise";

export default function AdminCarCategories() {
    const carService = new CarDBService();
    const tarrifService = new TarrifService();

    const navigate = useNavigate();
    const [carCategoriesData, setCarCategoriesData] = useState(null);
    const [carCategoriesTarrifsData, setCarCategoriesTarrifsData] = useState(null);

    const carCategoryError = () => {
        console.log("Error while extracting carCategories list");
        navigate("/admin/error");
    }

    const carCategoriesTarrifsError = () => {
        console.log("Error while extracting carCategories tarrifs");
        navigate("/admin/error");
    }

    if(carCategoriesData == null && carCategoriesTarrifsData == null) {
        Promise.all([
            HttpPromise(5000,
                carService.carCategoriesList().then(res => {
                    switch (res.status) {
                        case HTTP_OK:
                            console.log("Got carCategories data");
                            console.log(res.body);
                            setCarCategoriesData(res.body);
                            break;
                        case HTTP_BAD_REQUEST:
                            navigate("/admin/error");
                            break;
                        default:
                            console.log("Internal server error");
                            navigate("/admin/error");
                            break;
                }}).catch(carCategoryError),
            carCategoryError),
            HttpPromise(5000,
                tarrifService.getPricing().then(res => {
                    switch (res.status) {
                        case HTTP_OK:
                            console.log("Got tarrifs data");
                            console.log(res.body);
                            setCarCategoriesTarrifsData(res.body);
                            break;
                        case HTTP_BAD_REQUEST:
                            console.log("Bad request");
                            navigate("/admin/error");
                            break;
                        default:
                            console.log("Internal server error");
                            navigate("/admin/error");
                            break;
                }}).catch(carCategoriesTarrifsError), carCategoriesTarrifsError)
        ])
    }

    if(carCategoriesData == null || carCategoriesTarrifsData == null) {
       return(<Loading/>);
    }
    return(
        <Container sx = {{marginTop: 8}}>
            <Box sx = {{
                paddingTop: 2,
                textAlign: "center"
            }}>
                <Typography component="h1" variant="h3">Available car categories</Typography>
                <AdminCarCategoriesTable carCategories = {carCategoriesData}
                                         carDataCallback = {setCarCategoriesData}
                                         carCategoriesTarrifs = {carCategoriesTarrifsData}
                                         carDataTarrifsCallback = {setCarCategoriesTarrifsData}>
                </AdminCarCategoriesTable>
            </Box>
        </Container>
    );
}
