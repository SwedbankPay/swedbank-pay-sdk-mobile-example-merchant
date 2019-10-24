# Sample Merchant Backend implementation, Node.js 

This is a sample implementation of a Swedbank Pay merchant backend implemented in Node.js.

The core technology stack used is npm, node.js 10, express.js.

The application implemeents the [Swedbank Pay Mobile Backend API Reference](https://raw.githubusercontent.com/SwedbankPay/swedbank-pay-sdk-mobile-example-merchant/master/documentation/Swedbank Paysdk_openapi.yaml) and further calls the Swedbank Pay eCommerce platform APIs ('PSP') to perform the authentication and payment functions. The server code ships with the secrets required to talk to the PSP. It also presents a naive in-memory data storage solution for storing the merchant data.

## Configuration file

The application needs to ship some secrets. It does this naively via `appconfig.json` file. This file is not included in the Git repository; instead, there is `example.appconfig.json` which you can use as a template for your `appconfig.json` file. Just replace all the values in `<brackets>` with proper values and you are set.

## Authentication

The sample backend assumes the following HTTP headers to be present in all requests:

* `x-Swedbank Pay-sample-apikey` - A fixed API key. The client applications must store this value securely and include it in all requests.
* `x-Swedbank Pay-sample-access-token` - an access token identifying the current user / device. The client applications must store this value securely and include it in all requests.

## Data format

The data format for the `merchantData` parameter in the `POST /paymentorders` API call is as follows:

```json
 {
   "basketId": "123",
   "currency": "SEK",
   "languageCode": "sv-SE",
   "items": [
     {
      "itemId": "1",
       "quantity": 1,
       "price": 1200,
       "vat": 300
     },
     {
       "itemId": "2",
       "quantity": 2,
      "price": 400,
      "vat": 75
     }
   ]
 }
```

The monetary amounts (`price` and `vat`) are represented as multiplies of the lowest denominating unit in the defined currency, eg. for `SEK` currency a value of `100` would mean 1 SEK (100 öre). Both the `price` and `vat` represent the complete sum of purchasing `quantity` x `itemId`. Find out the unit price by dividing by `quantity`.

## Docker

This application has been packaged as a Docker container. 

### Testing the image locally

Build the Docker image:

```
docker build -t Swedbank Pay/merchant-sample-nodejs .
```

Run the Docker image:

```
docker run -p 8080:8080 -d Swedbank Pay/merchant-sample-nodejs
```

Now the merchant API is running at `http://localhost:8080`.

### Deploying the Docker container

Here are some examples for deploying the application's Docker container into cloud environments.

#### Google Cloud Run

The simplest way to run a Docker container in a cloud is to use [Google Cloud Run](https://cloud.google.com/run/).

Replace `<PROJECT-ID>` with your GCP project ID.

Build your image using Cloud Build:

```
gcloud builds submit --project=<PROJECT-ID> --tag gcr.io/<PROJECT-ID>/Swedbank Pay/merchant-sample-nodejs
```

Deploy to Cloud Run:

```
gcloud beta run deploy --project=<PROJECT-ID> --image gcr.io/<PROJECT-ID>/Swedbank Pay/merchant-sample-nodejs --platform managed
```

See the output of this command to find out the public endpoint address.

## Google App Engine

The simplest way to deploy this app to the cloud is Google's AppEngine. 

To deploy it, set up your project and run: 

```sh
GCP_PROJECT_ID=<PROJECT-ID> ./deploy-google-appengine.sh 
```

Replace `<PROJECT-ID>` with your GCP project ID.

## References

* [Swedbank Pay eCommerce platform API](https://developer.Swedbank Pay.com/xwiki/wiki/developer/view/Main/ecommerce/technical-reference/)

