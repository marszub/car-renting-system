import * as React from "react";
import { Container } from "@mui/system";
import { createTheme } from "@mui/material";
import { useNavigate } from "react-router-dom";


const theme = createTheme()

export default function MainPage() {
    const navigate = useNavigate();

    return (
        <Container></Container>
    );
}
