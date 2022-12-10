import * as React from "react";
import { PaymentService } from "../../services/payment-service";
import { HTTP_BAD_REQUEST, HTTP_CREATED, HTTP_OK} from "../../utils/http-status";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Typography } from "@mui/material";
import { Container, Box } from "@mui/system";
import AdminCarsTable from "./AdminCarsTable";
import Loading from "../Loading";
import AdminPaymentsTable from "./AdminPaymentsTable";

export default function AdminPayments() {
    const service = new PaymentService();

    const navigate = useNavigate();
    const [paymentData, setPaymentData] = useState(null);

    if(paymentData == null) {
        service.getPaymentsAdmin().then(res => {
            switch (res.status) {
                case HTTP_OK:
                    console.log("Got payments data");
                    console.log(res.body);
                    setPaymentData(res.body);
                    break;
                case HTTP_BAD_REQUEST:
                    console.log("Bad request");
                    break;
                default:
                    console.log("Internal server error");
                    navigate("/admin/error");
                    break;
        }}).catch(err => {
            console.log("Error while extracting Payments list");
            navigate("/admin/error");
        });
    }

    if(paymentData == null) {
       return(<Loading/>);
    }
    return(
        <Container sx = {{marginTop: 8}}>
            <Box sx = {{
                paddingTop: 2,
                textAlign: "center"
            }}>
                <Typography component="h1" variant="h3">Payments</Typography>
                <AdminPaymentsTable payments={paymentData}/>
            </Box>
        </Container>
    );
}
