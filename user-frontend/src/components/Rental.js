import * as React from "react";
import { Container, padding } from "@mui/system";
import { Typography, TextField, Button } from "@mui/material";
import { Link } from "@mui/material";
import { createTheme, ThemeProvider } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { Box } from "@mui/material";
import { tokenStorage } from "../services/token-storage";
import { CarDBService } from "../services/carDB-service";
import { HTTP_BAD_REQUEST, HTTP_NO_CONTENT } from "../utils/http-status";

const theme = createTheme()

export default function Rental() {
    const navigate = useNavigate();
    
    const service = new CarDBService();

    service.carList().then(res => {
        console.log(res.body);
    })

    return (
        <Container>
            <Box sx={{
                textAlign: "center",
                marginTop: 8
            }}>
                <Typography component="h1" variant="h2">
                    So far, nothing here....
                </Typography>
                <TextField
                    error={true}
                    helperText={tokenStorage.accessToken}
                />
            </Box>
        </Container>
    );
}
