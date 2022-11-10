import * as React from "react";
import { createTheme } from "@mui/material";
import { HTTP_BAD_REQUEST, HTTP_NO_CONTENT, HTTP_OK } from "../utils/http-status";
import { RentalService } from "../services/rental-service";
import { RentalView } from "./RentalView";
import { CarDBService } from "../services/carDB-service";
import { CarList } from "./CarList";
import HttpPromise from "../utils/HttpPromise";
import { useNavigate } from "react-router-dom";

const theme = createTheme()

async function retrieveData(setState, navigateToMain) {
        const serviceRental = new RentalService();
        const carService = new CarDBService();
        const rentalError = () => {
            console.log("error while extracting data");
            setState({isRental: false, isLoadingRental: false, rentalData: null});
        };

        const carServiceError = () => {
            console.log("Error while extracting car list");
            navigateToMain();
        }
        await Promise.all([
            HttpPromise(5000,
                serviceRental.getMyRental().then(res => {
                    switch (res.status) {
                        case HTTP_OK:
                            console.log("Got reservation data");
                            var rentalData = res.body;
                            rentalData.rentalDuration = 3455233;
                            rentalData.rentalCost = 3423;
                            setState({isRental: true, isLoadingRental: false,
                                      rentalData: rentalData});
                            break;
                        case HTTP_NO_CONTENT:
                            console.log("User has no actvie rental");
                            setState({isRental: false, isLoadingRental: false, rentalData: null});
                            break;
                        case HTTP_BAD_REQUEST:
                            console.log("Bad request");
                            setState({isLoadingRental: false});
                            break;
                        default:
                            console.log("Internal server error");
                            setState({isLoadingRental: false});
                            break;
                }}).catch(rentalError), rentalError),
            HttpPromise(5000,
            carService.carList().then(res => {
                switch (res.status) {
                    case HTTP_OK:
                        console.log("Got cars data");
                        console.log(res.body);
                        setState({isLoadingCars: false, carsData: res.body});
                        break;
                    case HTTP_BAD_REQUEST:
                        console.log("Bad request");
                        break;
                    default:
                        console.log("Internal server error");
                        navigateToMain();
                        break;
            }}).catch(carServiceError), carServiceError)
        ])
}

class RentalClass extends React.Component {
    constructor() {
        super();
        this.state = { isRental: false, isLoadingRental: true, isLoadingCars: true, rentalData: null, carsData: null };
        this.setRentalDataFunction = this.setRentalDataFunction.bind(this);
        this.setRentalFunction = this.setRentalFunction.bind(this);

        const updateState = (updateState) => {
            this.setState(updateState);
        }

        const navigateToMain = () => {
            this.props.navigation("/");
        }

        retrieveData(updateState, navigateToMain);
    }


    setRentalFunction(Data) {
        this.setState({isRental: Data});
    }

    setRentalDataFunction(Data) {
        this.setState({rentalData: Data});
    }

    render() {
        if(!this.state.isLoadingRental && !this.state.isLoadingCars){
            if(this.state.isRental){
                return (<RentalView callbackIsRental = {this.setRentalFunction}
                                    callbackRentalData = {this.setRentalDataFunction}
                                    rentalData = {this.state.rentalData} ></RentalView>);
            } else {
                return (<CarList 
                                 cars = {this.state.carsData}
                                 callbackIsRental = {this.setRentalFunction}
                                 callbackRentalData = {this.setRentalDataFunction}
                                 rentalData = {this.state.rentalData} ></CarList>);
                return(<a style={{textAlign: "center"}}>loading data...</a>);
            }
        }else return <a style={{textAlign: "center"}}>loading data...</a>; 
    }
}

export default function Rental(props) {
    const navigation = useNavigate();
    return <RentalClass {...props} navigation={navigation}></RentalClass>;
}
