import http from 'k6/http';
import { dataToCsv, randomKey, authUrl, rentalUrl } from './common.js';
import exec from 'k6/execution';
import { sleep } from 'k6';

export function handleSummary (data) {
    return dataToCsv(data, "burst_rent");
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
    var rentalPayload = JSON.stringify({
        "carId":`${exec.vu.idInTest + 1}`,
        "carTypeId":1
    });

    var rentalParams = {
        headers: {
            'Content-Type': 'application/json',
            'userId': token["userID"],
            'token': token["token"]
        },
    };
    var res = http.post(`${rentalUrl}/api/rental`, rentalPayload, rentalParams);
    sleep(10);
}