import { HttpService } from "./http-service";
import { RENTAL_SERVICE } from "../config";

export class RentalService extends HttpService {
    constructor() {
        super(RENTAL_SERVICE);
    }

    createRental(data) {
        return super.post("/rental", data);
    }

    endRental(rentalId) {
        return super.put("/rental/" + rentalId, null);
    }

    getMyRental() {
        return super.get("/rental", "");
    }
}