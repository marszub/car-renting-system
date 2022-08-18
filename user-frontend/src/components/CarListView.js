import * as React from "react";
import { Container } from "@mui/system";
import { Typography, Button } from "@mui/material";
import { createTheme } from "@mui/material";
import { Box } from "@mui/material";
import { CarDBService } from "../services/carDB-service";
import { RentalService } from "../services/rental-service";
import { HTTP_BAD_REQUEST, HTTP_CREATED, HTTP_OK} from "../utils/http-status";
import { useNavigate } from "react-router-dom";
import CarInformation from "./CarInformation";

const theme = createTheme()

class CarListViewClass extends React.Component {
    constructor() {
        super();
        this.handleSubmit = this.handleSubmit.bind(this);
        const carService = new CarDBService();
        this.state = {loading: true, carData: null}

        carService.carList().then(res => {
            switch (res.status) {
                case HTTP_OK:
                    console.log("Got cars data");
                    console.log(res.body);
                    this.setState({loading: false, carData: res.body});
                    break;
                case HTTP_BAD_REQUEST:
                    console.log("Bad request");
                    break;
                default:
                    console.log("Internal server error");
                    this.setState({loading: false, carData: {"cars": [{"carName": "Toyota", "id": 0}, {"carName": "Toyota2", "id": 1}]}});
                    //this.props.navigation("/");
                    break;
        }}).catch(err => {
            console.log("Error while extracting car list");
            this.setState({loading: false, carData: {"cars": [{"carName": "Toyota", "id": 0}, {"carName": "Toyota2", "id": 1}]}});
            //this.props.navigation("/");
        });
    }

    handleSubmit(event) {
        event.preventDefault();
        const service = new RentalService();
        const formData = new FormData(event.currentTarget);
        service.createRental({"carId": formData.get("carId")}).then(res => {
            switch (res.status) {
                case HTTP_CREATED:
                    console.log("rental createad");
                    this.props.callbackIsRental(true);
                    this.props.callbackRentalData(res.body);
                    break;
                case HTTP_BAD_REQUEST:
                    console.log("Bad request");
                    break;
                default:
                    console.log("Internal server error");
                    break;
        }})
    }

    spawnCars(carData, callbackIsRental, callbackRentalData) {
        var list = [];
        for(var i = 0; i < carData.cars.length; i++) {
            list.push(<CarInformation carData = {carData.cars[i]} key = {carData.cars[i]} callbackIsRental = {callbackIsRental} callbackRentalData = {callbackRentalData}></CarInformation>);
        }
        return(list);
    }

    render() {
        if(!this.state.loading) {
            return(
            <Container>
                <Box sx={{
                    textAlign: "center",
                    marginTop: 8
                }}>
                    <Typography component="h1" variant="h2">
                        Available Cars:
                    </Typography>
                    {this.spawnCars(this.state.carData, this.props.callbackIsRental, this.props.callbackRentalData)}
                </Box>
            </Container>)
        }
        else {
            return(<a>Loading</a>)
        }
    }
}

export default function CarListView(props) {
    const navigation = useNavigate();
    return <CarListViewClass {...props} navigation={navigation}></CarListViewClass>;
}
