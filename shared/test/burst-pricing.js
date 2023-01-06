import http from 'k6/http';
import { dataToCsv, tariffUrl } from './common.js';

export function handleSummary (data) {
    return dataToCsv(data, "burst_pricing");
}

export default function () {
    const pricingParams = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    http.get(`${tariffUrl}/api/pricing`, pricingParams);
}