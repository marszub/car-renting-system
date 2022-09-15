#!/bin/bash
sleep 5
HOST_TO_SCAN=$1
echo "$HOST_TO_SCAN"
(echo >/dev/tcp/$HOST_TO_SCAN/80) >/dev/null 2>&1 && echo "80 open"
(echo >/dev/tcp/$HOST_TO_SCAN/8080) >/dev/null 2>&1 && echo "8080 open"