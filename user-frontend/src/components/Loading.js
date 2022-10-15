import * as React from "react";
import { Container} from "@mui/system";
import { Box, Typography } from "@mui/material";

export default function Loading() {
    return(
        <Container sx = {{marginTop: 8}}>
            <Box sx = {{
                paddingTop: 2,
                textAlign: "center"
            }}>
                <Typography>Loading data...</Typography>
           </Box>
        </Container>
    )
}