import { HttpService } from "./http-service";
import { PAYMENT_SERVICE } from "../config";

export class PaymentService extends HttpService {
    constructor() {
        super(PAYMENT_SERVICE);
    }

    endRental(body) {
        return super.post("/payment", body);
    }

    getPaymentsAdmin() {
        return super.get("/admin/payment", "", null, "admin");
    }
}