import { HttpService, ShortResponse } from "./http-service"
import { AUTH_SERVICE } from "../config";
import { HTTP_NO_CONTENT, HTTP_OK, HTTP_BAD_REQUEST, HTTP_UNAUTHORIZED, isServerError} from "../utils/http-status";
import { tokenAdminStorage } from "./token-admin-storage";
import { adminIdStorage } from "./adminId-storage";

const onResponse = (res, service) => {
        if (res.status == HTTP_NO_CONTENT) {
            return new ShortResponse(res.status, res.body);
        }else if (res.status == HTTP_BAD_REQUEST) {
            return Promise.reject("Bad request");
        }else if (isServerError(res.status)) {
            return Promise.reject("Server Error");
        }else if (res.status === HTTP_UNAUTHORIZED && res.url !== AUTH_SERVICE + "/auth/token") {
            console.log("admin unauthorized");
            tokenAdminStorage.revokeToken();
            adminIdStorage.revokeAdminId();
            window.location.replace("/admin/sign-in");
        }
        return new Promise((resolve, reject) => res.json()
            .then(body => resolve(new ShortResponse(res.status, body)))
            .catch(err => reject(err)));
    }

export class AdminAuthService extends HttpService {
    constructor() {
        super(AUTH_SERVICE, onResponse);
    }
    
    signUp(data) {
        return super.post("/auth/register", data, "admin")
            .then(res => {
                if(res.status == HTTP_OK) {
                    tokenAdminStorage.accessToken = res.body.token;
                    console.log(res.body.userID);
                    adminIdStorage.adminId = parseInt(res.body.userID);
                }
                return res;
            }).catch(err => {return err});
        }

    signIn(data) {
        return super.post("/auth/token", data, "admin")
            .then(res => {
                if(res.status == HTTP_OK) {
                    tokenAdminStorage.accessToken = res.body.token;
                    adminIdStorage.adminId = parseInt(res.body.userID);
                }
                return res;
            }).catch(err => {return err});
    }

    signOut(data) {
        return super.post("/auth/logout", data, "admin")
            .then(res => {
                if(res.status == HTTP_NO_CONTENT) {
                    tokenAdminStorage.revokeToken();
                    adminIdStorage.revokeAdminId();
                }
                return res;
        }).catch(err => {return err});
    }
}
