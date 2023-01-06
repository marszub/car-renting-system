import http from 'k6/http';
import { dataToCsv, tariffUrl, authUrl } from './common.js';

export function handleSummary (data) {
    return dataToCsv(data, "burst_tariff");
}

export default function () {
    var loginPayload = JSON.stringify({
        "Login":"root",
        "Password":"password"
    });

    const loginParams = {
        headers: {
            'Content-Type': 'application/json'
        },
    };

    var loginResult = http.post(`${authUrl}/api/auth/token`, loginPayload, loginParams);
    var token = loginResult.json();

    var tariffPayload = JSON.stringify({
        "carType": 15,
        "price": 50
    });

    const tariffParams = {
        headers: {
            'Content-Type': 'application/json',
            'userId': token.userID,
            'token':token.token
        },
    };

    http.post(`${tariffUrl}/api/admin/pricing`, tariffPayload, tariffParams);
}