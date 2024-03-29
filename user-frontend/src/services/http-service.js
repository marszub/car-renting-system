import {HTTP_BAD_REQUEST, HTTP_NO_CONTENT, HTTP_UNAUTHORIZED, isServerError, HTTP_COLISION} from "../utils/http-status";
import { tokenStorage } from "./token-storage";
import { tokenAdminStorage } from "./token-admin-storage";
import { userIdStorage } from "./userId-storage";
import { adminIdStorage } from "./adminId-storage";

const onResponseFunction = (res, service) => {
        if (res.status == HTTP_NO_CONTENT) {
            return new ShortResponse(res.status, res.body);
        }else if (res.status == HTTP_BAD_REQUEST) {
            return Promise.reject("Bad request");
        }else if (isServerError(res.status)) {
            return Promise.reject("Server Error");
        }else if (res.status === HTTP_UNAUTHORIZED && service == "user") {
            console.log("user unauthorized");
            tokenStorage.revokeToken();
            userIdStorage.revokeUserId();
            window.location.replace("/sign-in");
        }else if (res.status === HTTP_UNAUTHORIZED && service == "admin") {
            console.log("admin unauthorized");
            tokenAdminStorage.revokeToken();
            adminIdStorage.revokeAdminId();
            window.location.replace("/admin/sign-in");
        }
        return new Promise((resolve, reject) => res.json()
            .then(body => resolve(new ShortResponse(res.status, body)))
            .catch(err => reject(err)));
    }


export class HttpService {
    constructor(servicePath, onResponse = onResponseFunction) {
        this.servicePath = servicePath;
        this.onResponse = onResponse;
    }

    request(path, requestMethod, body, service) {
        return fetch(`${this.servicePath}${path}`, {
            method: requestMethod,
            headers: {
                'Content-Type': 'application/json; charset=UTF-8',
                ...(service == "user" && tokenStorage.accessToken) && {'token': tokenStorage.accessToken},
                ...(service == "user" && userIdStorage.userId) && {'userId': userIdStorage.userId},
                ...(service == "admin" && tokenAdminStorage.accessToken) && {'token': tokenAdminStorage.accessToken},
                ...(service == "admin" && adminIdStorage.adminId) && {'userId': adminIdStorage.adminId},
            },
            credentials: 'include',
            body: body && JSON.stringify(body)
        }).then((res) => this.onResponse(res, service));
    }

    get(path, query, body=null, service="user") {
        return this.request(`${path}?${new URLSearchParams(query)}`, "GET", body, service);
    }

    post(path, body, service="user") {
        return this.request(path, "POST", body, service);
    }
    
    patch(path, body, service="user") {
        return this.request(path, "PATCH", body, service);
    }

    put(path, body=null, service="user") {
        return this.request(path, "PUT", body, service);
    }

    delete(path, body, service="user") {
        return this.request(path, "DELETE", body, service);
    }

    onUnexpectedHttpStatus(status) {
        return Promise.reject(`Unexpected HTTP status ${status}`);
    }

}

export class ShortResponse {
    constructor(status, body) {
        this.status = status;
        this.body = body;
    }
}
