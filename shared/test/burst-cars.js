import http from 'k6/http';
import { dataToCsv, randomKey, authUrl, carsUrl } from './common.js';
import exec from 'k6/execution';

export function handleSummary (data) {
    return dataToCsv(data, "burst_cars");
}

export default function () {
    var userKey = randomKey(40) + `${exec.vu.idInTest}`;
    var login = 'login' + userKey;
    var email = 'email' + userKey + '@email.com';
    var password = randomKey(20);

    var registerPayload = JSON.stringify({
        "Login":login,
        "Email":email,
        "Password":password
    });

    const registerParams = {
        headers: {
            'Content-Type': 'application/json'
        },
    };

    var registerResult = http.post(`${authUrl}/api/auth/register`, registerPayload, registerParams);
    var token = registerResult.json();

    const carsParams = {
        headers: {
            'Content-Type': 'application/json',
            'userId': token["userID"],
            'token': token["token"]
        },
    };
    http.get(`${carsUrl}/api/cars`, carsParams);
}