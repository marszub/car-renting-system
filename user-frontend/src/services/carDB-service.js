import { HttpService } from "./http-service";
import { CAR_DB_SERVICE } from "../config";

export class CarDBService extends HttpService {
    constructor() {
        super(CAR_DB_SERVICE);
    }

    carList() {
        return super.get("/cars", "");
    }

    createCar(carData) {
        return super.post("/cars", carData);
    }
}