import * as React from "react";
import { Container} from "@mui/system";
import { Grid, TextField, Typography } from "@mui/material";
import { createTheme} from "@mui/material";
import { SignInData } from "../models/sign-in-data";
import { Box } from "@mui/material";
import { Button } from "@mui/material";
import { AuthService } from "../services/auth-service";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { HTTP_COLISION, HTTP_OK, HTTP_BAD_REQUEST, HTTP_UNAUTHORIZED } from "../utils/http-status";
import { authValidators } from "../validators/auth-validators";

const theme = createTheme()

export default function SignInForm() {
    const navigate = useNavigate();

    const [loginErrorMessage, setLoginErrorMessage] = useState("");
    const [passwordErrorMessage, setPasswordErrorMessage] = useState("");

    const handleSubmit = (event) => {
        event.preventDefault();
        const service = new AuthService();
        const formData = new FormData(event.currentTarget);
        const requestBody = new SignInData(
            formData.get("login"),
            formData.get("password")
        );

        var loginMessage = authValidators.validateLogin(requestBody.login);
        var passwordMessage = authValidators.validateLogin(requestBody.password);

        setLoginErrorMessage(loginMessage);
        setPasswordErrorMessage(passwordMessage);

        if(loginMessage || passwordMessage) {
            return;
        }

        service.signIn(requestBody).then( res => {
            switch (res.status) {
                case HTTP_OK:
                    console.log("Logged in");
                    navigate("/");
                    break;
                case HTTP_UNAUTHORIZED:
                    setLoginErrorMessage("Wrong login or password");
                    setPasswordErrorMessage("Wrong login or password");
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
                    Login
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
                            autoFocus 
                            name="login" 
                            required 
                            fullWidth 
                            label="Login" 
                            error={loginErrorMessage != ""}
                            helperText={loginErrorMessage}
                            id="login"/>
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            autoComplete="off" 
                            autoFocus 
                            name="password" 
                            required 
                            type="password"
                            fullWidth 
                            error={passwordErrorMessage != ""}
                            helperText={passwordErrorMessage}
                            label="Password" 
                            id="password"/>
                    </Grid>
                    <Grid item xs={12}>
                        <Button fullWidth type="submit" variant="contained">
                            Login
                        </Button>
                    </Grid>
                </Grid>
            </Box>
        </Container>
    );
}

