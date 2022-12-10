import * as React from "react";
import { Container, Box } from "@mui/system";
import { Typography } from "@mui/material";
import { useState } from "react";
import { createTheme } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { HTTP_BAD_REQUEST, HTTP_OK} from "../utils/http-status";
import Loading from "./Loading";
import { TarrifService } from "../services/tarrif-service";
import { CarDBService } from "../services/carDB-service";
import HttpPromise from "../utils/HttpPromise";

const theme = createTheme()

export default function Tarrif() {
    const carService = new CarDBService();
    const tarrifService = new TarrifService();

    const navigate = useNavigate();
    const [carCategoriesData, setCarCategoriesData] = useState(null);
    const [carCategoriesTarrifsData, setCarCategoriesTarrifsData] = useState(null);

    const carCategoryError = () => {
        console.log("Error while extracting carCategories list");
        navigate("/");
    }

    const carCategoriesTarrifsError = () => {
        console.log("Error while extracting carCategories tarrifs");
        navigate("/");
    }

    if(carCategoriesData == null && carCategoriesTarrifsData == null) {
        Promise.all([
            HttpPromise(5000,
                carService.carCategoriesListUser().then(res => {
                    switch (res.status) {
                        case HTTP_OK:
                            console.log("Got carCategories data");
                            setCarCategoriesData(res.body);
                            break;
                        case HTTP_BAD_REQUEST:
                            console.log("Bad request");
                            break;
                        default:
                            console.log("Internal server error");
                            navigate("/admin");
                            break;
                }}).catch(carCategoryError),
            carCategoryError),
            HttpPromise(5000,
                tarrifService.getPricingUser().then(res => {
                    switch (res.status) {
                        case HTTP_OK:
                            console.log("Got tarrifs data");
                            setCarCategoriesTarrifsData(res.body);
                            break;
                        case HTTP_BAD_REQUEST:
                            console.log("Bad request");
                            break;
                        default:
                            console.log("Internal server error");
                            navigate("/admin");
                            break;
                }}).catch(carCategoriesTarrifsError), carCategoriesTarrifsError)
        ])
    }

    const listCarCategories = () => {
        var categoriesTarrifsData = carCategoriesTarrifsData.tarrifs;
        var categoriesData = carCategoriesData.carCategories;
        var list = [];
        for(var i = 0; i < categoriesData.length; i++) {
            var j = 0;
            for(j = 0; j < categoriesTarrifsData.length; j++) {
                if(categoriesData[i].id == categoriesTarrifsData[j].carType){
                    break;
                }
            }
            var tarrifValue = "error";
            if(j < categoriesTarrifsData.length)
                tarrifValue = categoriesTarrifsData[j].price/100;
            if(tarrifValue != "error") {
                list.push(
                    <tr key = {categoriesData[i].id}>
                        <th>
                            {categoriesData[i].id}
                        </th>
                        <th>
                            {categoriesData[i].carCategoryName}
                        </th>
                        <th>
                            {tarrifValue.toFixed(2)}
                        </th>
                    </tr>
                )
            }
        }
        return(list);
    }

    if(carCategoriesData == null || carCategoriesTarrifsData == null) {
        return (<Loading></Loading>);
    }

    return (
        <Container sx = {{marginTop: 8}}>
            <Box sx = {{
                paddingTop: 2,
                textAlign: "center"
            }}>
                <Typography component="h1" variant="h3">Available car categories</Typography>
                <table>
                    <colgroup>
                       <col span="1" style={{width: "10%"}}/>
                       <col span="1" style={{width: "30%"}}/>
                       <col span="1" style={{width: "20%"}}/>
                    </colgroup>
                    <thead>
                        <tr>
                            <th>
                                Id
                            </th>
                            <th>
                                Name
                            </th>
                            <th>
                                Tarrif [PLN/min]
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        {listCarCategories()}
                    </tbody>
                </table>
                <Typography color="gray">Each tariff contains additional fee of 7.00 PLN required to start the rental</Typography>
            </Box>
        </Container>
    );
}
