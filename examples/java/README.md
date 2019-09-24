# Sample Merchant Backend implementation, Java

This is a sample implementation of a PayEx merchant backend implemented in Java.

The core technology stack used is Java8, Gradle, Spring Boot.

The application implemeents the [PayEx Mobile Backend API](TODO) specification and further calls the PayEx eCommerce platform APIs ('PSP') to perform the authentication and payment functions. The server code ships with the secrets required to talk to the PSP. It also presents a naive in-memory data storage solution for storing the merchant data.

## Configuration file

The application needs to ship some secrets. It does this naively via `src/main/resources/application.properties` file. This file is not included in the Git repository; instead, there is `src/main/resources/example.application.properties` which you can use as a template for your `application.properties` file. Just replace all the values in `<brackets>` with proper values and you are set.

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

## Running locally

Run the application locally using the following command:

```sh
./gradlew bootRun
```

## Docker

This application has been packaged as a Docker container. 

### Testing the image locally

Build the application into a .jar:

```sh
./gradlew bootJar
```

Build the Docker image:

```sh
docker build --build-arg jarfile=build/libs/merchant-sample-java-1.0.0.jar -t payex/merchant-sample-java .
```

Run the Docker image:

```sh
docker run -p 8080:8080 -d payex/merchant-sample-java
```

Now the merchant API is running at `http://localhost:8080`.

### Deploying the Docker container

Here are some examples for deploying the application's Docker container into cloud environments.

#### Google Cloud Run

The simplest way to run a Docker container in a cloud is to use [Google Cloud Run](https://cloud.google.com/run/).

Replace `<PROJECT-ID>` with your GCP project ID.

Build your image using Cloud Build:

```
gcloud builds submit --project=<PROJECT-ID> --tag gcr.io/<PROJECT-ID>/payex/merchant-sample-java
```

Deploy to Cloud Run:

```
gcloud beta run deploy --project=<PROJECT-ID> --image gcr.io/<PROJECT-ID>/payex/merchant-sample-java --platform managed
```

See the output of this command to find out the public endpoint address.

## References

* [PayEx eCommerce platform API](https://developer.payex.com/xwiki/wiki/developer/view/Main/ecommerce/technical-reference/)
