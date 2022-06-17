import { HttpService, ShortResponse } from "./http-service"
import { AUTH_SERVICE } from "../config";
import { HTTP_NO_CONTENT, HTTP_OK, HTTP_BAD_REQUEST, HTTP_UNAUTHORIZED, isServerError} from "../utils/http-status";
import { tokenStorage } from "./token-storage";

const onResponse = (res) => {
        if (res.status == HTTP_NO_CONTENT) {
            return new ShortResponse(res.status, res.body);
        }else if (res.status == HTTP_BAD_REQUEST) {
            return Promise.reject("Bad request");
        }else if (isServerError(res.status)) {
            return Promise.reject("Server Error");
        }else if (res.status === HTTP_UNAUTHORIZED && res.url !== AUTH_SERVICE + "/auth/token") {
            console.log("user unauthorized");
            tokenStorage.revokeToken();
            window.location.replace("/sign-in");
        }
        return new Promise((resolve, reject) => res.json()
            .then(body => resolve(new ShortResponse(res.status, body)))
            .catch(err => reject(err)));
    }

export class AuthService extends HttpService {
    constructor() {
        super(AUTH_SERVICE, onResponse);
    }
    
    signUp(data) {
        return super.post("/auth/register", data)
            .then(res => {
                if(res.status == HTTP_OK) {
                    tokenStorage.accessToken = res.body.token;
                }
                return res;
            }).catch(err => {return err});
        }

    signIn(data) {
        return super.post("/auth/token", data)
            .then(res => {
                if(res.status == HTTP_OK) {
                    tokenStorage.accessToken = res.body.token;
                }
                return res;
            }).catch(err => {return err});
    }

    signOut(data) {
        return super.post("/auth/logout", data)
            .then(res => {
                if(res.status == HTTP_NO_CONTENT) {
                    tokenStorage.revokeToken();
                }
                return res;
        }).catch(err => {return err});
    }
}
