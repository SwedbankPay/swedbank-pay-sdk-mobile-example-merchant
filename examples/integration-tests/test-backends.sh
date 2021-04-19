#!/bin/bash
set -e
cd ../java/merchant
./gradlew bootRun --args ' --api-key='"$API_KEY"' --merchant-token='"$MERCHANT_TOKEN"' --merchant-id='"$MERCHANT_ID"'' &
APP_PID=$!
cd -
sleep 5
until curl localhost:8080/healthcheck; do sleep 2; done

curl -s --show-error -X POST -d @requests/paymentOrderRequest.json -H "Content-Type: application/json" -H "x-payex-sample-apikey: $API_KEY" -H "x-payex-sample-access-token: doot_doot" localhost:8080/paymentorders | jq .

kill $APP_PID
sleep 5

cd ../node.js
cp example.appconfig.json appconfig.json
export SWBPAY_merchantId=$MERCHANT_ID SWBPAY_apiKey=$API_KEY SWBPAY_merchantToken=$MERCHANT_TOKEN
node ./app.js &
APP_PID=$!
cd -
sleep 5
curl -v -s --show-error -X POST -d @requests/paymentOrderRequest_v1.json -H "Content-Type: application/json" -H "x-payex-sample-apikey: $API_KEY" -H "x-payex-sample-access-token: doot_doot" localhost:8080/paymentorders | jq .

kill $APP_PID
