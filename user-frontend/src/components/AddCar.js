import * as React from "react";
import { Container} from "@mui/system";
import { Grid, TextField, Typography } from "@mui/material";
import { createTheme} from "@mui/material";
import { Box } from "@mui/material";
import { Button } from "@mui/material";
import { CarDBService } from "../services/carDB-service";
import { useNavigate } from "react-router-dom";
import {HTTP_CREATED, HTTP_BAD_REQUEST, HTTP_UNAUTHORIZED } from "../utils/http-status";

const theme = createTheme()

export default function AddCar() {
    const navigate = useNavigate();

    const handleSubmit = (event) => {
        event.preventDefault();
        const service = new CarDBService();
        const formData = new FormData(event.currentTarget);

        service.createCar({"name": formData.get("carName")}).then( res => {
            switch (res.status) {
                case HTTP_CREATED:
                    console.log("carCreated");
                    break;
                case HTTP_UNAUTHORIZED:
                    console.log("Wrong login or password");
                    break;
                case HTTP_BAD_REQUEST:
                    console.log("Bad request");
                    navigate("/error");
                    break;
                default:
                    console.log("Internal server error");
                    navigate("/error");
                    break;
            }
        });
    };

    return (
        <Container>
            <Box sx = {{
                textAlign: "center",
                marginTop: 8
            }}>
                <Typography component="h1" variant="h3">
                    AddCar
                </Typography>
            </Box>
            <Box component="form" onSubmit={handleSubmit} sx={{
                textAlign: "center",
                marginTop: 1 
            }}>
                <Grid container spacing={3}>
                    <Grid item xs={12}>
                        <TextField
                            autoComplete="off" 
                            name="carName" 
                            required 
                            fullWidth 
                            label="carName" 
                            id="carName"/>
                    </Grid>
                    <Grid item xs={12}>
                        <Button fullWidth type="submit" variant="contained">
                            Create
                        </Button>
                    </Grid>
                </Grid>
            </Box>
        </Container>
    );
}

