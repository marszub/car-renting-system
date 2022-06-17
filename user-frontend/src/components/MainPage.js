import * as React from "react";
import { Container, padding } from "@mui/system";
import { Typography, TextField, Button } from "@mui/material";
import { Link } from "@mui/material";
import { createTheme, ThemeProvider } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { Box } from "@mui/material";
import { tokenStorage } from "../services/token-storage";
import { AuthService } from "../services/auth-service";
import { HTTP_BAD_REQUEST, HTTP_NO_CONTENT } from "../utils/http-status";

const theme = createTheme()

export default function MainPage() {
    const navigate = useNavigate();

    const logOut = (event) => {
        const service = new AuthService();
        service.signOut().then(res => {
            switch (res.status) {
                case HTTP_NO_CONTENT:
                    console.log("Logged out");
                    navigate("/");
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
        }).catch(err => {
            console.log("err");
            navigate("/error");
        });
    }

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
            <Box sx = {{
                textAlign: "center",
                marginTop: 8}}>
                <Button onClick={() => {navigate("/sign-in")}}>
                    Login
                </Button>
            </Box>
            <Box sx = {{
                textAlign: "center",
                marginTop: 8}}>
                <Button onClick={() => {navigate("sign-up")}}>
                    Register
                </Button>
            </Box>
            <Box sx = {{
                textAlign: "center",
                marginTop: 8}}>
                <Button onClick={logOut}>
                    Log out
                </Button>
            </Box>
        </Container>
    );
}
