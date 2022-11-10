import * as React from "react";
import { Container } from "@mui/system";
import { Typography, Button, Box } from "@mui/material";
import { createTheme } from "@mui/material";
import { HTTP_BAD_REQUEST, HTTP_NO_CONTENT, HTTP_OK} from "../utils/http-status";
import { RentalService } from "../services/rental-service";

const theme = createTheme()

export class RentalView extends React.Component {
    constructor(props) {
        super();
        this.setCurrentCost = this.setCurrentCostFunction.bind(this);
        var time = this.calculate_time(props.rentalData.rentalDuration);
        this.state = { currentCost: (props.rentalData.rentalCost/100).toFixed(2), time: this.format_time(time)};
        this.runTimer(time);
    }

    setCurrentCostFunction(Data) {
        this.setState({currentCost: Data});
    }

    runTimer(time) {
        var seconds = time.seconds;
        var minutes = time.minutes;
        var hours = time.hours;
        setInterval(() => {
            seconds += 1;
            if(seconds == 60) {
                seconds = 0;
                minutes += 1;
                if(minutes == 60) {
                    minutes = 0;
                    hours += 1;
                }
            }
            var formatted_time = this.format_time({seconds: seconds,
                                                   minutes: minutes,
                                                   hours: hours});
            this.setState({time: formatted_time});
        }, 1000);
    }

    format_time(time) {
        var seconds = time.seconds;
        var minutes = time.minutes;
        var hours = time.hours;
        if(seconds < 10) {
            seconds = `0${seconds}`;
        }
        if(minutes < 10) {
            minutes = `0${minutes}`;
        }
        if(hours < 10) {
            hours = `0${hours}`;
        }
        return {seconds: seconds, minutes: minutes, hours: hours};
    }

    calculate_time(timer) {
        var hours = Math.floor(timer/3600000);
        timer = timer % 3600000;
        var minutes = Math.floor(timer/60000);
        timer = timer % 60000;
        var seconds = Math.floor(timer/1000);
        return {hours: hours, minutes: minutes, seconds: seconds};
    }

    endRental(rentalId, isRentalCallback, rentalDataCallback) {
        const serviceRental = new RentalService();
        serviceRental.endRental(rentalId).then(res => {
            switch (res.status) {
                case HTTP_NO_CONTENT:
                    console.log("User has no actvie rental");
                    isRentalCallback(false);
                    rentalDataCallback(null);
                    break;
                case HTTP_BAD_REQUEST:
                    console.log("Bad request");
                    break;
                default:
                    console.log("Internal server error");
                    break;
        }})
    }

    calculateCost(setCurrentCost) {
        const serviceRental = new RentalService();
        serviceRental.getCurrentRentalCost().then(res => {
            switch (res.status) {
                case HTTP_OK:
                    console.log("Successfully retrieved rental cost");
                    setCurrentCost((res.body/100).toFixed(2));
                    break;
                case HTTP_BAD_REQUEST:
                    console.log("Bad request");
                    break;
                default:
                    console.log("Internal server error");
                    break;
            }
        })
    }

    render() {
        return(
        <Container>
            <Box sx={{
                textAlign: "center",
                marginTop: 8
            }}>
                <Typography component="h1" variant="h2">
                    Your current rental:
                </Typography>
                <Typography component="h1" variant="h2">
                    {this.state.time.hours!="00" && this.state.time.hours}{this.state.time.hours!="00"&&":"}
                    {this.state.time.minutes}:{this.state.time.seconds}
                </Typography>
                <Typography component="h1" variant="h2">
                    CarId: {this.props.rentalData.carId}
                </Typography>
                <Typography component="h1" variant="h2">
                    RentalId: {this.props.rentalData.reservationId}
                </Typography>
                <Typography component="h1" variant="h2">
                    Current rental cost: {this.state.currentCost}
                </Typography>
                <Box>
                    <Box style={{display: "inline"}}>
                        <Button fullwidth variant="contained" onClick={() => this.calculateCost(this.setCurrentCost)}>
                            Get current cost
                        </Button>
                    </Box>
                    <Box style={{display: "inline", marginLeft: "10px"}}>
                        <Button fullwidth variant="contained" onClick={() => {
                                this.endRental(this.props.rentalData.reservationId, this.props.callbackIsRental, this.props.callbackRentalData)
                            }}>
                            End rental
                        </Button>
                    </Box>
                </Box>
            </Box>
        </Container>)
    }
}