#!/bin/bash
set -e
cd ../node.js
npm install
cp example.appconfig.json appconfig.json
export SWBPAY_merchantId=$MERCHANT_ID SWBPAY_apiKey=$API_KEY SWBPAY_merchantToken=$MERCHANT_TOKEN
node ./app.js &
APP_PID=$!
cd -
sleep 5
curl -v -s --show-error -X POST -d @requests/paymentOrderRequest_v1.json -H "Content-Type: application/json" -H "x-payex-sample-apikey: $API_KEY" -H "x-payex-sample-access-token: doot_doot" localhost:8080/paymentorders | jq .

kill $APP_PID
