import * as React from "react";
import { Container } from "@mui/system";
import { Button } from "@mui/material";
import { createTheme } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { Box } from "@mui/material";


const theme = createTheme()

export default function MainPage() {
    const navigate = useNavigate();


    return (
        <Container></Container>
    );
}
