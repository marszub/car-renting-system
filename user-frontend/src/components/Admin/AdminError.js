import * as React from "react";
import { Container, padding } from "@mui/system";
import { Typography } from "@mui/material";
import { Link } from "@mui/material";
import { createTheme, ThemeProvider } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { Box } from "@mui/material";

const theme = createTheme()

export default function AdminError() {
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
                        500
                    </Typography>
                    <Typography component="p" sx={{fontSize: 25}}>
                        Internal server error
                    </Typography>
                    <Typography component="p" sx={{fontSize:25}}>
                        Return to <Link onClick={() => navigate("/admin")} href="">home page</Link>
                    </Typography>
                </Box>
            </Container>
        </ThemeProvider>
    );
}
