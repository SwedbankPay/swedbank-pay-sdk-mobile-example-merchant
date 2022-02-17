'use strict';

const { get, sendError } = require('../util/networking.js');
const { makeUnauthorizedProblem } = require('../util/problems.js');

async function authorize(req) {
    // To allow, add "allowGetPayerOwnedPaymentTokens": true to appconfig.json
    // A real implementation must do access control to only
    // allow a user to access their own tokens.
    if (!global.config.allowGetPayerOwnedPaymentTokens) {
        throw makeUnauthorizedProblem();
    }
}

module.exports.route = async (req, res) => {
    try {
        //This is sensative data but can only be accessed by the client, additional authorizations shouldn't be needed at this point.
        //await authorize(req);
        const payerRef = req.params.ref;
        const psp = req.params.psp; //This is not likely to change, but in case I allow some flexibility here.
        const response = await get(`/${psp}/paymentorders/${payerRef}?$expand=payer`);

        // Your implementation should at this point probably calculate shipping costs and tell the client how to show this to the user, and present alternative shipping methods.
        res.status(200).send().end();
    } catch (e) {
        console.log('Failed to get payer tests');
        console.log(e);
        sendError(res, e);
    }
};

/*
Remove debug info when done:
{"paymentOrder":
    {"id":"/psp/paymentorders/62088122-3d87-4636-2fb8-08d9ec88b178","created":"2022-02-15T14:31:41.2140660Z","updated":"2022-02-15T14:31:41.4447836Z","operation":"Purchase","status":"Initialized","currency":"SEK","amount":1000,"vatAmount":200,"description":"Order 12345678","initiatingSystemUserAgent":"SwedbankPaySDK-Android/1.0","language":"en-US","availableInstruments":["CreditCard","Invoice-PayExFinancingSe","Invoice-PayMonthlyInvoiceSe","CarPay","Swish","CreditAccount"],"implementation":"Starter","integration":"","instrumentMode":false,"guestMode":true,"orderItems":
        {"id":"/psp/paymentorders/62088122-3d87-4636-2fb8-08d9ec88b178/orderitems"},
        "urls":
            {"id":"/psp/paymentorders/62088122-3d87-4636-2fb8-08d9ec88b178/urls"},
        "payeeInfo":
            {"id":"/psp/paymentorders/62088122-3d87-4636-2fb8-08d9ec88b178/payeeinfo"},
        "payer":
            {"id":"/psp/paymentorders/62088122-3d87-4636-2fb8-08d9ec88b178/payers"},
            "history":
            {"id":"/psp/paymentorders/62088122-3d87-4636-2fb8-08d9ec88b178/history"},
            "failed":
            {"id":"/psp/paymentorders/62088122-3d87-4636-2fb8-08d9ec88b178/failed"},
        "aborted":
            {"id":"/psp/paymentorders/62088122-3d87-4636-2fb8-08d9ec88b178/aborted"},
        "paid":
            {"id":"/psp/paymentorders/62088122-3d87-4636-2fb8-08d9ec88b178/paid"},
        "cancelled":
            {"id":"/psp/paymentorders/62088122-3d87-4636-2fb8-08d9ec88b178/cancelled"},
        "financialTransactions":
            {"id":"/psp/paymentorders/62088122-3d87-4636-2fb8-08d9ec88b178/financialtransactions"}
        ,"failedAttempts":
            {"id":"/psp/paymentorders/62088122-3d87-4636-2fb8-08d9ec88b178/failedattempts"}
        ,"metadata":
            {"id":"/psp/paymentorders/62088122-3d87-4636-2fb8-08d9ec88b178/metadata"}
        },"operations":
            [
                {"method":"PATCH","href":"https://api.externalintegration.payex.com/psp/paymentorders/62088122-3d87-4636-2fb8-08d9ec88b178","rel":"update-order","contentType":"application/json"},
                {"method":"PATCH","href":"https://api.externalintegration.payex.com/psp/paymentorders/62088122-3d87-4636-2fb8-08d9ec88b178","rel":"abort","contentType":"application/json"},
                {"method":"GET","href":"https://ecom.externalintegration.payex.com/checkout/5bb472e127cdc20c748d9b032c7a2c3ccb0854ea86fba17aea2a45135ed42a69","rel":"redirect-checkout","contentType":"text/html"},
                {"method":"GET","href":"https://ecom.externalintegration.payex.com/checkout/core/client/checkout/5bb472e127cdc20c748d9b032c7a2c3ccb0854ea86fba17aea2a45135ed42a69?culture=en-US","rel":"view-checkout","contentType":"application/javascript"}
            ]
        }
        //get: psp/paymentorders/62088122-3d87-4636-2fb8-08d9ec88b178?$expand=payer

        second attempt:

        {"paymentOrder":{"id":"/psp/paymentorders/d63f8079-01d9-499e-6f7d-08d9f03a45d6","created":"2022-02-15T20:13:36.8551064Z","updated":"2022-02-15T20:13:36.9723316Z","operation":"Purchase","status":"Initialized","currency":"SEK","amount":1000,"vatAmount":200,"description":"Order 12345678","initiatingSystemUserAgent":"SwedbankPaySDK-Android/1.0","language":"en-US","availableInstruments":["CreditCard","Invoice-PayExFinancingSe","Invoice-PayMonthlyInvoiceSe","CarPay","Swish","CreditAccount"],"implementation":"Starter","integration":"","instrumentMode":false,"guestMode":true,"orderItems":{"id":"/psp/paymentorders/d63f8079-01d9-499e-6f7d-08d9f03a45d6/orderitems"},"urls":{"id":"/psp/paymentorders/d63f8079-01d9-499e-6f7d-08d9f03a45d6/urls"},"payeeInfo":{"id":"/psp/paymentorders/d63f8079-01d9-499e-6f7d-08d9f03a45d6/payeeinfo"},"payer":{"id":"/psp/paymentorders/d63f8079-01d9-499e-6f7d-08d9f03a45d6/payers"},"history":{"id":"/psp/paymentorders/d63f8079-01d9-499e-6f7d-08d9f03a45d6/history"},"failed":{"id":"/psp/paymentorders/d63f8079-01d9-499e-6f7d-08d9f03a45d6/failed"},"aborted":{"id":"/psp/paymentorders/d63f8079-01d9-499e-6f7d-08d9f03a45d6/aborted"},"paid":{"id":"/psp/paymentorders/d63f8079-01d9-499e-6f7d-08d9f03a45d6/paid"},"cancelled":{"id":"/psp/paymentorders/d63f8079-01d9-499e-6f7d-08d9f03a45d6/cancelled"},"financialTransactions":{"id":"/psp/paymentorders/d63f8079-01d9-499e-6f7d-08d9f03a45d6/financialtransactions"},"failedAttempts":{"id":"/psp/paymentorders/d63f8079-01d9-499e-6f7d-08d9f03a45d6/failedattempts"},"metadata":{"id":"/psp/paymentorders/d63f8079-01d9-499e-6f7d-08d9f03a45d6/metadata"}},"operations":[{"method":"PATCH","href":"https://api.externalintegration.payex.com/psp/paymentorders/d63f8079-01d9-499e-6f7d-08d9f03a45d6","rel":"update-order","contentType":"application/json"},{"method":"PATCH","href":"https://api.externalintegration.payex.com/psp/paymentorders/d63f8079-01d9-499e-6f7d-08d9f03a45d6","rel":"abort","contentType":"application/json"},{"method":"GET","href":"https://ecom.externalintegration.payex.com/checkout/core/client/checkout/7b8c32e24d327ed8446fcdf257c36d55fea8e9dcec3ad7359db75bebdf5b73b8?culture=en-US","rel":"view-checkout","contentType":"application/javascript"}]}

using get: /psp/paymentorders/d63f8079-01d9-499e-6f7d-08d9f03a45d6?$expand=payer
gives:

payer: {
      id: '/psp/paymentorders/d63f8079-01d9-499e-6f7d-08d9f03a45d6/payers',
      reference: '6ee913a1-85ef-4aed-ada2-8e9f5850931d',
      name: 'Förnamn Efternamn',
      email: 'kent@hej.ee',
      msisdn: '+46733123123',
      hashedFields: [Object],
      shippingAddress: [Object],
      device: [Object]
    },

*/