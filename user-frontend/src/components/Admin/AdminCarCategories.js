import * as React from "react";
import { CarDBService } from "../../services/carDB-service";
import { HTTP_BAD_REQUEST, HTTP_CREATED, HTTP_OK} from "../../utils/http-status";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Typography } from "@mui/material";
import { Container, Box } from "@mui/system";
import AdminCarCategoriesTable from "./AdminCarCategoriesTable";
import Loading from "../Loading";

export default function AdminCarCategories() {
    const carService = new CarDBService();

    const navigate = useNavigate();
    const [carCategoriesData, setCarCategoriesData] = useState(null);

    if(carCategoriesData == null) {
        carService.carCategoriesList().then(res => {
            switch (res.status) {
                case HTTP_OK:
                    console.log("Got carCategories data");
                    console.log(res.body);
                    setCarCategoriesData(res.body);
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

    if(carCategoriesData == null) {
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
                                carDataCallback = {setCarCategoriesData}></AdminCarCategoriesTable>
            </Box>
        </Container>
    );
}
