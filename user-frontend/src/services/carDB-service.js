import { HttpService } from "./http-service";
import { CAR_DB_SERVICE } from "../config";

export class CarDBService extends HttpService {
    constructor() {
        super(CAR_DB_SERVICE);
    }

    carList() {
        return super.get("/cars", "");
    }

    adminCarList() {
        return super.get("/admin/cars", "", null, "admin");
    }

    createCar(carData) {
        return super.post("/admin/cars", carData, "admin");
    }

    createCarCategory(carCategoryData) {
        return super.post("/admin/carCategories", carCategoryData, "admin");
    }

    carCategoriesList() {
        return super.get("/admin/carCategories", "", null, "admin");
    }

    carCategoriesListUser() {
        return super.get("/carCategories", "", null);
    }

    updateCarLocation(body, carId) {
        return super.patch("/admin/cars/" + carId, body, "admin");
    }
}
