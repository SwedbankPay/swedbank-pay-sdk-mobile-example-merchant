#!/bin/bash
set -e


cd ../node.js
cp example.appconfig.json appconfig.json
export SWBPAY_merchantId=$MERCHANT_ID SWBPAY_apiKey=$API_KEY SWBPAY_merchantToken=$MERCHANT_TOKEN
node ./app.js &
APP_PID=$!
cd -
until curl localhost:8080/healthcheck; do sleep 2; done
sleep 5
result=$(curl -v -s --show-error -X POST -d @requests/paymentOrderRequest_v1.json -H "Content-Type: application/json" -H "x-payex-sample-apikey: $API_KEY" -H "x-payex-sample-access-token: doot_doot" localhost:8080/paymentorders)
status=$(echo $result | jq .status)

echo $result | jq .
kill $APP_PID

if [ $status == "null" ]; then
		# things are ok (I think...)
		echo "No error status reported"
		exit 0
fi

if [ $status -gt 299 ]
     then
     echo "Request reported error"
     exit 1
 	 else
 	 echo "Request worked"
fi