#!/bin/bash

cd ../java/merchant
./gradlew bootRun --args ' --api-key='"$API_KEY"' --merchant-token='"$MERCHANT_TOKEN"' --merchant-id='"$MERCHANT_ID"'' &
APP_PID=$!
cd -
sleep 5
until curl localhost:8080/healthcheck; do sleep 2; done

curl -X POST -d @requests/paymentOrderRequest.json -H "Content-Type: application/json" -H "x-payex-sample-apikey: $API_KEY" localhost:8080/paymentorders | jq .

kill $APP_PID
