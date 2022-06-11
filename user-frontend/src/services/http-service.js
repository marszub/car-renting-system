import SelectInput from "@mui/material/Select/SelectInput";
import {HTTP_BAD_REQUEST, HTTP_NO_CONTENT, HTTP_UNAUTHORIZED, isServerError, HTTP_COLISION} from "../utils/http-status";
import { tokenStorage } from "./token-storage";

export class HttpService {
    constructor(servicePath) {
        this.servicePath = servicePath
    }

    request(path, requestMethod, body) {
        return fetch(`${this.servicePath}${path}`, {
            method: requestMethod,
            headers: {
                'Content-Type': 'application/json; charset=UTF-8',
                ...(tokenStorage.accessToken) && {'Authorization': + tokenStorage.accessToken},
            },
            credentials: 'include',
            body: body && JSON.stringify(body, this.replacer)
        }).then(this.onResponse);
    }

    onResponse(res) {
        if (res.status == HTTP_NO_CONTENT) {
            return new ShortResponse(res.status, res.body);
        }else if (res.status == HTTP_BAD_REQUEST) {
            return Promise.reject("Bad request");
        }else if (isServerError(res.status)) {
            return Promise.reject("Server Error");
        }else if (res.status === HTTP_UNAUTHORIZED) {
            alert("USER UNAUTHORIZED");
            console.log("user unauthorized");
            tokenStorage.revokeToken();
            window.location.replace("/sign-in");
        }
        return new Promise((resolve, reject) => res.json()
            .then(body => resolve(new ShortResponse(res.status, body)))
            .catch(err => reject(err)));
    }
 
    replacer(key, value) {
        if (value instanceof Map) {
            return Object.fromEntries(value.entries());
        } else if (value instanceof Set) {
            return Array.from(value);
        } else if (value === null) {
            return undefined;
        } else {
            return value;
        }
    }

    get(path, query, body=null) {
        return this.request(`${path}?${new URLSearchParams(query)}`, "GET", body);
    }

    post(path, body) {
        return this.request(path, "POST", body);
    }
    
    patch(path, body) {
        return this.request(path, "PATCH", body);
    }

    delete(path, body) {
        return this.request(path, "DELETE", body);
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
