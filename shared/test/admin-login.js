import http from 'k6/http';
import { sleep } from 'k6';

const baseUrl = 'http://192.168.0.100:5010';

export let options = {
    vus: 1000,
    duration: '5s',
    thresholds: {
        http_req_duration: ['p(95)<1500']
    }
};

export default function () {
    const payload = JSON.stringify({
        "Login":"root",
        "Password":"password"
    });

    var params = {
        headers: {
            'Content-Type': 'application/json'
        },
    };

    http.post(`${baseUrl}/api/Auth/token`, payload, params);
    sleep(1);
}