import * as React from "react";
import { Container, padding } from "@mui/system";
import { Typography } from "@mui/material";
import { Link } from "@mui/material";
import { createTheme, ThemeProvider } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { Box } from "@mui/material";

const theme = createTheme()

export default function PageNotFound() {
    const navigate = useNavigate();

    return (
        <ThemeProvider theme = {theme}>
            <Container maxWidth="sm">
                <Box sx={{
                    textAlign: "center",
                    flexDirection: "column",
                    display: "flex",
                    marginTop: 8
                }}>
                    <Typography component="h1" variant="h1">
                        404
                    </Typography>
                    <Typography component="p" sx={{fontSize: 25}}>
                        This page does not exist
                    </Typography>
                    <Typography component="p" sx={{fontSize:25}}>
                        Return to <Link onClick={() => navigate("/")} href="">home page</Link>
                    </Typography>
                </Box>
            </Container>
        </ThemeProvider>
    );
}
