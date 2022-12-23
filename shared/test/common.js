import exec from 'k6/execution';

const baseUrl = 'http://localhost'
export const authUrl = `${baseUrl}:5010`;
export const carsUrl = `${baseUrl}:5030`;
export const tariffUrl = `${baseUrl}:5049`;

export function randomKey(length) {
    var result = '';
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    var charactersLength = characters.length;
    for ( var i = 0; i < length; i++ ) {
        result += characters.charAt(Math.floor(Math.random() * charactersLength));
    }
    return result;
}

export function dataToCsv (data, name) {
    var vus = exec.test.options.scenarios.default.vus;
    var fileName = `/test/results/_res_${name}_${vus}.csv`;
    var row = `${name}`;

    row += "," + vus;
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