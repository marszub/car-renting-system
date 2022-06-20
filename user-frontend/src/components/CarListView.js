import * as React from "react";
import { Container, padding } from "@mui/system";
import { Grid, TextField, Typography, Button } from "@mui/material";
import { createTheme, ThemeProvider } from "@mui/material";
import { Box, List } from "@mui/material";
import { CarDBService } from "../services/carDB-service";
import { RentalService } from "../services/rental-service";
import { HTTP_BAD_REQUEST, HTTP_CREATED, HTTP_OK} from "../utils/http-status";

const theme = createTheme()

export class CarListView extends React.Component {
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
                    break;
        }});
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
                    <Box component="form" onSubmit={this.handleSubmit} sx={{
                        textAlign: "center",
                        marginTop: 1 
                    }}>
                        <Grid container spacing={3}>
                            <Grid item xs={12}>
                                <TextField
                                    autoComplete="off" 
                                    name="carId" 
                                    required 
                                    fullWidth 
                                    label="carId" 
                                    id="carId"/>
                            </Grid>
                            <Grid item xs={12}>
                                <Button fullWidth type="submit" variant="contained">
                                    Rent
                                </Button>
                            </Grid>
                        </Grid>
                    </Box>
                </Box>
            </Container>)
        }
        else {
            return(<a>Loading</a>)
        }
    }
}