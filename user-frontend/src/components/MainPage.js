import * as React from "react";
import { Container, padding } from "@mui/system";
import { Typography, TextField } from "@mui/material";
import { Link } from "@mui/material";
import { createTheme, ThemeProvider } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { Box } from "@mui/material";
import { tokenStorage } from "../services/token-storage";

const theme = createTheme()

export default function MainPage() {
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
