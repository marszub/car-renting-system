import { HttpService } from "./http-service";
import { TARRIF_SERVICE } from "../config";

export class TarrifService extends HttpService {
    constructor() {
        super(TARRIF_SERVICE);
    }

    addPricing(data) {
        return super.post("/admin/pricing", data, "admin");
    }

    getPricing() {
        return super.get("/admin/pricing", "", null, "admin");
    }

    getPricingUser() {
        return super.get("/admin/pricing", "", null, "admin");
    }
}