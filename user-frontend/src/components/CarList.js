import * as React from "react";
import { Container } from "@mui/system";
import { createTheme } from "@mui/material";
import { CarDBService } from "../services/carDB-service";
import { HTTP_BAD_REQUEST, HTTP_OK} from "../utils/http-status";
import { useNavigate } from "react-router-dom";
import CarMap from "./CarMap.js"

const theme = createTheme()

class CarListClass extends React.Component {
    constructor() {
        super();
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
                    //this.props.navigation("/");
                    break;
        }}).catch(err => {
            console.log("Error while extracting car list");
            this.setState({loading: false, carData: {cars: 
                [{carName: "Car1", id: 0, latitude: 49.799481, longitude: 18.911419},
                 {carName: "Car2", id: 1, latitude: 49.796793, longitude: 18.918125},
                 {carName: "Car3", id: 2, latitude: 49.797821, longitude: 18.907599}]
            }});
            //this.props.navigation("/");
        });
    }

    render() {
        if(!this.state.loading) {
            return(
            <Container>
                <CarMap cars = {this.state.carData.cars}
                        callbacksMap = {{isRental: this.props.callbackIsRental,
                                         rentalData: this.props.callbackRentalData }}/>
            </Container>)                                                         
        }
        else {
            return(<a>Loading</a>)
        }
    }
}

export default function CarList(props) {
    const navigation = useNavigate();
    return <CarListClass {...props} navigation={navigation}></CarListClass>;
}
