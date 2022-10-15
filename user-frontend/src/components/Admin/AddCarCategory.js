import * as React from "react";
import { Container} from "@mui/system";
import { Grid, TextField, Typography } from "@mui/material";
import { createTheme} from "@mui/material";
import { Box } from "@mui/material";
import { Button } from "@mui/material";
import { CarDBService } from "../../services/carDB-service";
import { useNavigate } from "react-router-dom";
import { HTTP_CREATED, HTTP_BAD_REQUEST, HTTP_UNAUTHORIZED } from "../../utils/http-status";

const theme = createTheme()

export default function AddCarCategory() {
    const navigate = useNavigate();
    const service = new CarDBService();

    const handleSubmit = (event) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);

        service.createCarCategory({"categoryName": formData.get("carCategoryName")}).then( res => {
            switch (res.status) {
                case HTTP_CREATED:
                    console.log("carCategory created");
                    navigate("/admin/carCategories")
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
                    Create new CarCategory
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
                            name="carCategoryName" 
                            required 
                            fullWidth 
                            label="carCategoryName" 
                            id="carCategoryName"/>
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

