import { HttpService } from "./http-service";
import { CARMANAGER_SERVICE } from "../config";

export class CarManagerService extends HttpService {
    constructor() {
        super(CARMANAGER_SERVICE);
    }

    adminCarList() {
        return super.get("/admin/cars", "", null, "admin");
    }

    setCarToken(body) {
        return super.post("/admin/cars", body, "admin");
    }
}
