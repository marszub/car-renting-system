import { HttpService, ShortResponse } from "./http-service"
import { AUTH_SERVICE } from "../config";
import { HTTP_NO_CONTENT, HTTP_OK } from "../utils/http-status";
import { tokenStorage } from "./token-storage";

export class AuthService extends HttpService {
    constructor() {
        super(AUTH_SERVICE);
    }
    
    signUp(data) {
        return super.post("/auth/register", data)
            .then(res => {
                if(res.status == HTTP_OK) {
                    tokenStorage.accessToken = res.body.token;
                }
                return res;
            });
        }

    signIn(data) {
        return super.post("/auth/token", data)
            .then(res => {
                if(res.status == HTTP_OK) {
                    tokenStorage.accessToken = res.body.token;
                }
                return res;
            });
    }

    signOut(data) {
        return super.post("/auth/logout", data)
            .then(res => {
                if(res.status == HTTP_NO_CONTENT) {
                    tokenStorage.revokeToken();
                }
                return res;
        });
    }
}
