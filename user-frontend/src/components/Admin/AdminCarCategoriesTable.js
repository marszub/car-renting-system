import * as React from "react";
import { TarrifService } from "../../services/tarrif-service";
import { HTTP_BAD_REQUEST, HTTP_CREATED, HTTP_UNAUTHORIZED } from "../../utils/http-status";
import { Grid, TextField, Button, Typography, Box } from "@mui/material";
import "../../styles/admin-cars-table-style.css";
import Popup from 'reactjs-popup';
import { useNavigate } from "react-router-dom";
import 'reactjs-popup/dist/index.css';
import { tarrifValidators } from "../../validators/tarrif-validators";
import { useState } from "react";

export default function AdminCarCategoriesTable(props) {
    const [tarrifErrorMessage, setTarrifErrorMessage] = useState("");
    const [, updateState] = React.useState();
    const forceUpdate = React.useCallback(() => updateState({}), []);
    const navigate = useNavigate();

    const resetErrors = () => {
        setTarrifErrorMessage("");
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);
        const tarrifData = {"carType": formData.get("ID"), 
                            "price": parseInt(formData.get("tarrif")*100)};
        var tarrifError = tarrifValidators.validateTarrif(tarrifData.price);
        setTarrifErrorMessage(tarrifError);

        if(tarrifError) {
            return;
        }

        var tarrifService = new TarrifService();
        tarrifService.addPricing(tarrifData).then(res => {
            switch(res.status) {
                case HTTP_CREATED:
                    console.log("Tarrif created");
                    var newData = props.carCategoriesTarrifs.tarrifs;
                    newData.push(tarrifData);
                    props.carDataTarrifsCallback({"tarrifs": newData});
                    forceUpdate();
                    break;
                case HTTP_UNAUTHORIZED:
                    console.log("Wrong login or password");
                    break;
                case HTTP_BAD_REQUEST:
                    console.log("Bad request");
                    navigate("/admin/error");
                    break;
                default:
                    console.log("Internal server error");
                    navigate("/admin/error");
                    break;
            }
        })
    };

    const listCarCategories = (carCategoriesData, carCategoriesTarrifsData) => {
        var list = [];
        for(var i = 0; i < carCategoriesData.length; i++) {
            var j = 0;
            for(j = 0; j < carCategoriesTarrifsData.length; j++) {
                if(carCategoriesData[i].id == carCategoriesTarrifsData[j].carType){
                    break;
                }
            }
            var tarrifValue = 
                        <Popup trigger={<Button>Add tarrif</Button>}
                            modal={true} onClose={resetErrors} lockScroll={true}
                            contentStyle={{width: "40%", textAlign:"center", borderRadius: "20px"}}>
                                <Typography component="h5" variant="h5">
                                    Add carCategory's tarrif
                                </Typography>
                                <Box component="form"
                                    onSubmit={handleSubmit} 
                                    sx={{
                                        textAlign: "center",
                                        marginTop: 1 
                                    }}
                                >
                                    <input type="hidden" id="ID"
                                           value={carCategoriesData[i].id}
                                           name = "ID"
                                    />
                                    <Grid container spacing={3}>
                                        <Grid item xs={12}>
                                            <TextField
                                                autoComplete="off" 
                                                autoFocus 
                                                name="tarrif" 
                                                required 
                                                label="tarrif" 
                                                error={tarrifErrorMessage != ""}
                                                helperText={tarrifErrorMessage}
                                                id="tarrif"/>
                                        </Grid>
                                        <Grid item xs={12}>
                                            <Button type="submit" variant="contained">
                                                Submit
                                            </Button>
                                        </Grid>
                                    </Grid>
                                </Box>
                        </Popup>
            if(j < carCategoriesTarrifsData.length)
                tarrifValue = carCategoriesTarrifsData[j].price/100
            list.push(
                <tr key = {carCategoriesData[i].id}>
                    <th>
                        {carCategoriesData[i].id}
                    </th>
                    <th>
                        {carCategoriesData[i].carCategoryName}
                    </th>
                    <th>
                        {tarrifValue.toFixed(2)}
                    </th>
                </tr>
            )
        }
        return(list);
    };

    return(
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
                {listCarCategories(props.carCategories.carCategories,
                                   props.carCategoriesTarrifs.tarrifs)}
                <tr>
                    <th colSpan={3} style={{textAlign: "center"}}>
                        <Button onClick={() => navigate("/admin/carCategories/new")}>
                            Add Car Category
                        </Button>
                    </th>
                </tr>
            </tbody>
        </table>
    )
}

