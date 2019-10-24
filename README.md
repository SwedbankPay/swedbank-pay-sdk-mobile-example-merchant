# Swedbank Pay Mobile SDK Merchant Backend Guide

This repository holds information about how to implement a Merchant Backend application to act as an endpoint between a merchant's mobile application and the Swedbank Pay APIs. 

It features a step by step guide on what the merchant backend MUST do and also offers insights on what the merchant backend SHOULD do to complete its function in an efficient and secure manner.

Implementation examples in some popular programming languages are included.

## Terminology

This document uses the following terminology:

* *SDK* - refers to the Swedbank Pay mobile SDK
* *PSP* - refers to Swedbank Pay eCommerce platform
* *Merchant* - refers to the party using the mobile payments SDK (and implementing the corresponding merchant backend)

## Implementation Guide

This chapter addresses the things to consider when implementing a merchant backend.

### Mobile API 

First of all you need to implement the Swedbank Pay Mobile Backend REST API. Pick a suitable technology stack for your programming language of choice.

See the [Swedbank Pay Mobile Backend API Reference](https://editor.swagger.io/?url=https://raw.githubusercontent.com/SwedbankPay/swedbank-pay-sdk-mobile-example-merchant/master/documentation/Swedbank Paysdk_openapi.yaml) for more info.

#### Authentication

Your mobile application and the merchant backend should implement an authentication / authorization scheme. In the Mobile SDK, this is enabled by allowing you to supply custom HTTP headers into the requests which your backend may then process. 

If this authentication should fail, the merchant backend should then reply with `401 Unauthorized` and return a `Content-Type: application/json` response with a body object describing the error. This response body will in turn be delivered to the mobile application by the SDK for handling (eg. for launching a re-login).

#### Merchant Data

The `POST /paymentorders` API call takes a parameter called `merchantData`. This is to be a free-form JSON object, passed through the API call transparently from the calling mobile SDK to your backend. The contents of this object are arbitrary data used by the Merchant; typically this data would include the used currency along with the purchase amounts and VAT amounts (eg. shopping basket contents).

### Data storage

Your merchant backend will likely want to have an intermediate data storage for storing purchases. Typically you would write the received purchase into a data storage and then make a call to the PSP APIs. And then the PSP responds, you would update the purchase data by adding the status (success/fail) of the action as well as noting the ID of a successful purchase.

### Integrating to Swedbank Pay eCommerce API

Your merchant backend will need to integrate to the Swedbank Pay eCommerce API. For this purpose you will need a *Merchant ID* as well as a *Merchant Token* which you will receive from Swedbank Pay. 

These API calls are not instantaneous; consider this for performance purposes of your backend. Implement the HTTP requests using the best practices of your programming language / platform.

See the [Technical Reference](https://developer.Swedbank Pay.com/xwiki/wiki/developer/view/Main/ecommerce/technical-reference/) for more info.

### Information Security 

Your merchant backend must store the *Merchant ID* and *Merchant Token* somewhere. Do this in a secure manner to avoid them ever leaking to external audiences.

Implement your API in a secure manner over an encrypted TLS connection. The mobile SDK also supports TLS Certificate Pinning for added security against Man-in-the-Middle attacks.

## Resources 

Here are listed the essential resources needed for the implementation work:

* [Swedbank Pay Mobile Backend API Reference](https://editor.swagger.io/?url=https://raw.githubusercontent.com/SwedbankPay/swedbank-pay-sdk-mobile-example-merchant/master/documentation/Swedbank Paysdk_openapi.yaml) 
* [Swedbank Pay eCommerce API Technical Reference](https://developer.Swedbank Pay.com/xwiki/wiki/developer/view/Main/ecommerce/technical-reference/)

## Examples

See the subdirectory `examples/` for implementation examples in some popular programming languages. Currently examples exist for:

* Java
* Node.js



