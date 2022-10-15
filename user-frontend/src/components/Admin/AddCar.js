import * as React from "react";
import { Container} from "@mui/system";
import { Grid, Select, TextField, Typography,
         MenuItem, InputLabel, FormControl } from "@mui/material";
import { createTheme} from "@mui/material";
import { Box } from "@mui/material";
import { Button } from "@mui/material";
import { CarDBService } from "../../services/carDB-service";
import { useNavigate } from "react-router-dom";
import { HTTP_OK, HTTP_CREATED, HTTP_BAD_REQUEST, HTTP_UNAUTHORIZED } from "../../utils/http-status";
import { useState } from "react";
import Loading from "../Loading";

const theme = createTheme()

export default function AddCar() {
    const navigate = useNavigate();
    const [carCategoriesData, setCarCategoriesData] = useState(null);
    const [selectedCategory, setSelectedCategory] = useState("");
    const service = new CarDBService();

    if(carCategoriesData == null) {
        service.carCategoriesList().then(res => {
            switch (res.status) {
                case HTTP_OK:
                    console.log("Got car categories");
                    console.log(res.body);
                    setCarCategoriesData(res.body);
                    break;
                case HTTP_BAD_REQUEST:
                    console.log("Bad request");
                    break;
                default:
                    console.log("Internal server error");
                    navigate("/");
                    break;
        }}).catch(err => {
            console.log("Error while extracting car list");
            navigate("/");
        });
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);

        service.createCar({"name": formData.get("carName"),
                           "carCategory": formData.get("carCategory")}).then( res => {
            switch (res.status) {
                case HTTP_CREATED:
                    console.log("carCreated");
                    navigate("/admin/cars");
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

    const handleChange = (event) => {
        setSelectedCategory(event.target.value);
    }

    if(carCategoriesData == null) {
        return(<Loading/>);
    }

    const spawnCarCategories = (data) => {
        var list = [];
        for(var i = 0; i < data.length; i++) {
            list.push(<MenuItem key={data[i].id} value={data[i].id}>
                          {data[i].carCategoryName}
                      </MenuItem>
            )
        }
        return(list);
    };

    return (
        <Container>
            <Box sx = {{
                textAlign: "center",
                marginTop: 8
            }}>
                <Typography component="h1" variant="h3">
                    Create new Car
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
                            name="carName" 
                            required 
                            fullWidth 
                            label="carName" 
                            id="carName"/>
                    </Grid>
                    <Grid item xs={12}>
                        <FormControl fullWidth style={{"textAlign": "left"}}>
                            <InputLabel>Car category</InputLabel>
                            <Select
                                autoComplete="off" 
                                name="carCategory" 
                                required 
                                fullWidth 
                                value = {selectedCategory}
                                label="carCategory" 
                                onChange={handleChange}
                                id="carCategory">
                                {
                                    spawnCarCategories(carCategoriesData.carCategories)
                                }
                            </Select>
                        </FormControl>
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

