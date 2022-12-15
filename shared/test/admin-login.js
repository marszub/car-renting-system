import http from 'k6/http';
import { sleep } from 'k6';

const baseUrl = 'http://192.168.0.100:5010';

export function handleSummary (data) {
    var fileName = `/results/_res${data.metrics.vus_max.values.value}.csv`;
    var row = `${data.metrics.vus_max.values.value}`;
    row += "," + data.metrics.http_req_failed.values.rate;
    row += "," + data.metrics.data_sent.values.rate;
    row += "," + data.metrics.data_received.values.rate;
    row += "," + data.metrics.iterations.values.rate;
    row += "," + data.metrics.http_reqs.values.rate;

    row += "," + data.metrics.http_req_duration.values.avg;
    row += "," + data.metrics.http_req_duration.values.min;
    row += "," + data.metrics.http_req_duration.values.med;
    row += "," + data.metrics.http_req_duration.values.max;
    row += "," + data.metrics.http_req_duration.values["p(95)"];

    row += "," + data.metrics.http_req_waiting.values.avg;
    row += "," + data.metrics.http_req_waiting.values.min;
    row += "," + data.metrics.http_req_waiting.values.med;
    row += "," + data.metrics.http_req_waiting.values.max;
    row += "," + data.metrics.http_req_waiting.values["p(95)"];

    row += "," + data.metrics.http_req_connecting.values.avg;
    row += "," + data.metrics.http_req_connecting.values.min;
    row += "," + data.metrics.http_req_connecting.values.med;
    row += "," + data.metrics.http_req_connecting.values.max;
    row += "," + data.metrics.http_req_connecting.values["p(95)"];

    var result = {};
    result[fileName] = row;
    return result;
}

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
}