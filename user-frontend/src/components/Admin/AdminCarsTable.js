import * as React from "react";
import { CarDBService } from "../../services/carDB-service";
import { CarManagerService } from "../../services/carManager-service";
import { HTTP_BAD_REQUEST, HTTP_COLISION, HTTP_NO_CONTENT } from "../../utils/http-status";
import { Grid, TextField, Button, Typography, Box } from "@mui/material";
import "../../styles/admin-cars-table-style.css";
import { useNavigate } from "react-router-dom";
import AddLocationAltIcon from '@mui/icons-material/AddLocationAlt';
import Popup from 'reactjs-popup';
import 'reactjs-popup/dist/index.css';
import { coordinatesValidators } from "../../validators/coordinates-validators";
import { useState } from "react";

export default function AdminCarsTable(props) {
    const navigate = useNavigate();
    const [, updateState] = React.useState();
    const forceUpdate = React.useCallback(() => updateState({}), []);
    const [latitudeErrorMessage, setLatitudeErrorMessage] = useState("");
    const [longitudeErrorMessage, setLongitudeErrorMessage] = useState("");
    const [carTokenErrorMessage, setCarTokenErrorMessage] = useState("");

    const handleSubmit = (event) => {
        event.preventDefault();
        const service = new CarDBService();
        const formData = new FormData(event.currentTarget);
        var carId = formData.get("ID");
        const coordinates = {
            "latitude": parseFloat(formData.get("Latitude")),
            "longitude": parseFloat(formData.get("Longitude")),
        }

        var latitudeMessage = coordinatesValidators.validateLatitude(coordinates.latitude)
        var longitudeMessage = coordinatesValidators.validateLongitude(coordinates.longitude)

        setLatitudeErrorMessage(latitudeMessage);
        setLongitudeErrorMessage(longitudeMessage);

        if(latitudeMessage || longitudeMessage) {
            return;
        }

        var carIdInt = parseInt(carId);

        service.updateCarLocation({"coordinates": coordinates}, carId).then( res => {
            switch (res.status) {
                case HTTP_NO_CONTENT:
                    console.log("Coordinates successfully changed!");
                    var data = props.cars;
                    for(var i = 0; i < data.cars.length; i++) {
                        if(data.cars[i].id == carIdInt) {
                            data.cars[i].coordinates = coordinates;
                            props.carDataCallback(data);
                            forceUpdate();
                            break;
                        }
                    }
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
        });
    };

    const handleSettingCarToken = (event) => {
        event.preventDefault();
        const service = new CarManagerService();
        const formData = new FormData(event.currentTarget);
        var carId = formData.get("ID");

        var carIdInt = parseInt(carId);
        var carToken = formData.get("token");

        service.setCarToken({"carId": carId, "carToken": carToken}).then( res => {
            switch (res.status) {
                case HTTP_NO_CONTENT:
                    console.log("Token successfully added");
                    var data = props.carTokens;
                    data.cars.push({"carId": carId, "carToken": carToken});
                    props.carTokensCallback(data);
                    forceUpdate();
                    break;
                case HTTP_COLISION:
                    console.log("COLISION!");
                    setCarTokenErrorMessage("Car with that token already exists!");
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
        });
    };

    const resetErrors = () => {
        setLatitudeErrorMessage("");
        setLongitudeErrorMessage("");
        setCarTokenErrorMessage("");
    };

    const listCars = (data, carsTokensData) => {
        var list = [];
        for(var i = 0; i < data.length; i++) {
            var j = 0;
            for(j = 0; j < carsTokensData.length; j++) {
                if(data[i].id == carsTokensData[j].carId){
                    break;
                }
            }
            var tokenValue = 
                        <Popup trigger={<Button>Add token</Button>}
                            modal={true} onClose={resetErrors} lockScroll={true}
                            contentStyle={{width: "40%", textAlign:"center", borderRadius: "20px"}}>
                                <Typography component="h5" variant="h5">
                                    Add token
                                </Typography>
                                <Box component="form"
                                    onSubmit={handleSettingCarToken} 
                                    sx={{
                                        textAlign: "center",
                                        marginTop: 1 
                                    }}
                                >
                                    <input type="hidden" id="ID"
                                           value={data[i].id}
                                           name = "ID"
                                    />
                                    <Grid container spacing={3}>
                                        <Grid item xs={12}>
                                            <TextField
                                                autoComplete="off" 
                                                autoFocus 
                                                name="token" 
                                                required 
                                                label="token" 
                                                error={carTokenErrorMessage != ""}
                                                helperText={carTokenErrorMessage}
                                                id="token"/>
                                        </Grid>
                                        <Grid item xs={12}>
                                            <Button type="submit" variant="contained">
                                                Submit
                                            </Button>
                                        </Grid>
                                    </Grid>
                                </Box>
                        </Popup>
            if(j < carsTokensData.length)
                tokenValue = carsTokensData[j].carToken
            var popup = null
            popup = 
                <Popup trigger={<Button><AddLocationAltIcon/></Button>}
                    modal={true} onClose={resetErrors} lockScroll={true}
                    contentStyle={{width: "40%", textAlign:"center", borderRadius: "20px"}}>
                        <Typography component="h5" variant="h5">
                            Change car's "{data[i].carName}" coordinates
                        </Typography>
                        <Box component="form"
                            onSubmit={handleSubmit} 
                            sx={{
                                textAlign: "center",
                                marginTop: 1 
                            }}
                        >
                            <input type="hidden" id="ID" value={data[i].id} name = "ID"/>
                            <Grid container spacing={3}>
                                <Grid item xs={12}>
                                    <TextField
                                        autoComplete="off" 
                                        autoFocus 
                                        defaultValue={data[i].coordinates.latitude}
                                        name="Latitude" 
                                        required 
                                        label="Latitude" 
                                        error={latitudeErrorMessage != ""}
                                        helperText={latitudeErrorMessage}
                                        id="Latitude"/>
                                </Grid>
                                <Grid item xs={12}>
                                    <TextField
                                        autoComplete="off" 
                                        autoFocus 
                                        name="Longitude" 
                                        defaultValue={data[i].coordinates.longitude}
                                        required 
                                        type="Longitude"
                                        label="Longitude" 
                                        error={longitudeErrorMessage != ""}
                                        helperText={longitudeErrorMessage}
                                        id="Longitude"/>
                                </Grid>
                                <Grid item xs={12}>
                                    <Button type="submit" variant="contained">
                                        Submit
                                    </Button>
                                </Grid>
                            </Grid>
                        </Box>
                </Popup>
            list.push(
                <tr key = {data[i].id}>
                    <th>
                        {data[i].id}
                    </th>
                    <th>
                        {data[i].carName}
                    </th>
                    <th>
                        {data[i].carCategory.carCategoryName}
                    </th>
                    <th>
                        {tokenValue}
                    </th>
                    <th>
                        {data[i].coordinates.latitude}
                    </th>
                    <th>
                        {data[i].coordinates.longitude}
                    </th>
                    <th>
                        {data[i].carStatus}
                    </th>
                    <th>
                        {popup}
                    </th>
                </tr>
            )
        }
        return(list);
    };

    return(
        <table>
            <colgroup>
               <col span="1" style={{width: "5%"}}/>
               <col span="1" style={{width: "20%"}}/>
               <col span="1" style={{width: "17%"}}/>
               <col span="1" style={{width: "10%"}}/>
               <col span="1" style={{width: "15%"}}/>
               <col span="1" style={{width: "15%"}}/>
               <col span="1" style={{width: "15%"}}/>
               <col span="1" style={{width: "7%"}}/>
            </colgroup>
            <thead>
                <tr>
                    <th>
                        Car Id
                    </th>
                    <th>
                        Car Name
                    </th>
                    <th>
                        Car Category
                    </th>
                    <th>
                        Car token
                    </th>
                    <th>
                        Car latitude
                    </th>
                    <th>
                        Car longitude
                    </th>
                    <th>
                        Status
                    </th>
                    <th>
                        Edit
                    </th>
                </tr>
            </thead>
            <tbody>
                {listCars(props.cars.cars, props.carTokens.cars)}
                <tr>
                    <th colSpan={8} style={{textAlign: "center"}}>
                        <Button onClick={() => navigate("/admin/cars/new")}>Add Car</Button>
                    </th>
                </tr>
            </tbody>
        </table>
    )
}

