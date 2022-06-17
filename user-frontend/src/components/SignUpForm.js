import * as React from "react";
import { Container} from "@mui/system";
import { Grid, TextField, Typography } from "@mui/material";
import { createTheme} from "@mui/material";
import { SignUpData } from "../models/sign-up-data";
import { Box } from "@mui/material";
import { Button } from "@mui/material";
import { AuthService } from "../services/auth-service";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { HTTP_COLISION, HTTP_OK, HTTP_BAD_REQUEST } from "../utils/http-status";
import { authValidators } from "../validators/auth-validators";

const theme = createTheme()

export default function SignUpForm() {
    const navigate = useNavigate();

    const [loginErrorMessage, setLoginErrorMessage] = useState("");
    const [emailErrorMessage, setEmailErrorMessage] = useState("");
    const [passwordErrorMessage, setPasswordErrorMessage] = useState("");
    const [repeatPasswordErrorMessage, setRepeatPasswordErrorMessage] = useState("");

    const handleSubmit = (event) => {
        event.preventDefault();
        const service = new AuthService();
        const formData = new FormData(event.currentTarget);
        const requestBody = new SignUpData(
            formData.get("login"),
            formData.get("email"),
            formData.get("password")
        );

        var loginMessage = authValidators.validateLogin(requestBody.login);
        var emailMessage = authValidators.validateEmail(requestBody.email);
        var passwordMessage = authValidators.validatePassword(requestBody.password);
        var passwordsMatching = authValidators.validatePasswordMatch(requestBody.password, formData.get("repeatPassword"));

        setRepeatPasswordErrorMessage(passwordsMatching);

        if(passwordMessage == "") {
            setPasswordErrorMessage(passwordsMatching);
        } else {
            setPasswordErrorMessage(passwordMessage);
        }

        setEmailErrorMessage(emailMessage);
        setLoginErrorMessage(loginMessage);

        if(loginMessage || emailMessage || passwordMessage || passwordsMatching) {
            return;
        }

        service.signUp(requestBody).then( res => {
            switch (res.status) {
                case HTTP_OK:
                    console.log("Logged in");
                    navigate("/");
                    break;
                case HTTP_COLISION:
                    var error = "User with that login or email already exists!";
                    console.log(error);
                    setLoginErrorMessage(error);
                    setEmailErrorMessage(error);
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
                    Register new account
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
                            name="email" 
                            required 
                            fullWidth 
                            error={emailErrorMessage != ""}
                            helperText={emailErrorMessage}
                            label="Email" 
                            id="email"/>
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
                        <TextField
                            autoComplete="off" 
                            autoFocus 
                            name="repeatPassword" 
                            required 
                            type="password"
                            fullWidth 
                            error={repeatPasswordErrorMessage != ""}
                            helperText={repeatPasswordErrorMessage}
                            label="Repeat Password" 
                            id="repeatPassword"/>
                    </Grid>
                    <Grid item xs={12}>
                        <Button fullWidth type="submit" variant="contained">
                            Create account
                        </Button>
                    </Grid>
                </Grid>
            </Box>
        </Container>
    );
}
