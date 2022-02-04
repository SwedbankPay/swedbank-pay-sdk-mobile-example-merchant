'use strict';

const { Joi } = require('celebrate');
const { post, sendError } = require('../util/networking.js');
const constants = require('../util/constants.js');
const findOperation = require('../util/find-operation.js');
const { makeUnauthorizedProblem } = require('../util/problems.js');


/**
 * Validation schema for paymentorder.
 */
const paymentOrderSchema = Joi.object().keys({
    operation: Joi.string()
        .valid('Purchase', 'Verify')
        .required(),
    productName: Joi.string()
        .valid('Checkout3', 'checkout3'),
    currency: Joi.string()
        .required(),
    amount: Joi.number()
        .integer()
        .required(),
    vatAmount: Joi.number()
        .integer()
        .required(),
    description: Joi.string()
        .allow('')
        .required(),
    userAgent: Joi.string()
        .required(),
    language: Joi.string()
        .required(),
    instrument: Joi.string(),
    generateRecurrenceToken: Joi.boolean()
        .required(),
    generatePaymentToken: Joi.boolean(),
    disableStoredPaymentDetails: Joi.boolean(),
    restrictedToInstruments: Joi.array().items(Joi.string()),
    urls: Joi.object({
        hostUrls: Joi.array().items(Joi.string())
            .required(),
        completeUrl: Joi.string()
            .required(),
        cancelUrl: Joi.string(),
        paymentUrl: Joi.string(),
        callbackUrl: Joi.string(),
        termsOfServiceUrl: Joi.string()
    }).required(),
    payeeInfo: Joi.object({
        payeeId: Joi.string()
            .allow('')
            .required(),
        payeeReference: Joi.string()
            .allow('')
            .max(30)
            .required(),
        payeeName: Joi.string().allow(''),
        productCategory: Joi.string().allow(''),
        orderReference: Joi.string()
            .allow('')
            .max(50),
        subsite: Joi.string()
            .allow('')
            .max(40)
    }).required(),
    payer: Joi.object({
        consumerProfileRef: Joi.string(),
        email: Joi.string(),
        msisdn: Joi.string(),
        payerReference: Joi.string(),
    }),
    orderItems: Joi.array().items(Joi.object({
        reference: Joi.string()
            .allow('')
            .required(),
        name: Joi.string()
            .allow('')
            .required(),
        type: Joi.string()
            .valid(
              'PRODUCT',
              'SERVICE',
              'SHIPPING_FEE',
              'PAYMENT_FEE',
              'DISCOUNT',
              'VALUE_CODE',
              'OTHER'
            )
            .required(),
        class: Joi.string()
            .allow('')
            .required(),
        itemUrl: Joi.string()
            .allow(''),
        imageUrl: Joi.string()
            .allow(''),
        description: Joi.string()
            .allow(''),
        discountDescription: Joi.string()
            .allow(''),
        quantity: Joi.number()
            .integer()
            .required(),
        quantityUnit: Joi.string()
            .allow('')
            .required(),
        unitPrice: Joi.number()
            .integer()
            .required(),
        discountPrice: Joi.number()
            .integer(),
        vatPercent: Joi.number()
            .integer()
            .required(),
        amount: Joi.number()
            .integer()
            .required(),
        vatAmount: Joi.number()
            .integer()
            .required()
    })),
    riskIndicator: Joi.object({
        deliveryEmailAdress: Joi.string(),
        deliveryTimeFrameIndicator: Joi.string()
            .valid('01','02','03','04'),
        preOrderDate: Joi.string(),
        preOrderPurchaseIndicator: Joi.string()
            .valid('01','02'),
        shipIndicator: Joi.string()
            .valid('01','02','03','04','05','06','07'),
        giftCardPurchase: Joi.boolean(),
        reOrderPurchaseIndicator: Joi.string()
            .valid('01','02'),
        pickUpAddress: Joi.object({
            name: Joi.string()
                .allow(''),
            streetAddress: Joi.string()
                .allow(''),
            coAddress: Joi.string()
                .allow(''),
            city: Joi.string()
                .allow(''),
            zipCode: Joi.string()
                .allow(''),
            countryCode: Joi.string()
                .allow('')
        })
    }),
    disablePaymentMenu: Joi.boolean(),
    paymentToken: Joi.string(),
    initiatingSystemUserAgent: Joi.string()
});

/**
 * Payload scheme for /paymentorders.
 */
module.exports.schema = Joi.object().keys({
    paymentorder: paymentOrderSchema.required()
});

function checkPaymentToken(req, paymentToken) {
    // To allow, add "allowUsePaymentToken": true to appconfig.json
    // A real implementation must do access control to only
    // allow a user to use their own tokens.
    if (!global.config.allowUsePaymentToken) {
        throw makeUnauthorizedProblem();
    }
}

function checkPaymentOrder(req, paymentOrder) {
    // To allow, add "allowGetPayerTokens": true to appconfig.json
    // A real implementation must do access control to only
    // allow a user to access their own tokens.
    if (paymentOrder.paymentToken) {
        checkPaymentToken(req, paymentOrder.paymentToken);
    }
}


/**
 * Preprocesses the payment order received from the client.
 * The payment order is stored in our own database for future
 * reference. Also, this example defers creating
 * the payeeReference to the backend, so that is done here as well.
 * A real application would probably also do some validation
 * on the payment order here.
 * @param {object} paymentOrder the payment order to preprocess
 */
function preparePaymentOrder(paymentOrder) {
    // Insert the payment order in our own database
    // to generate a unique reference for it.
    const paymentId = global.database.insertPurchse(paymentOrder);
    paymentOrder.payeeInfo.payeeId = global.config.merchantId;
    paymentOrder.payeeInfo.payeeReference = paymentId
}

/**
 * Calls the PSP endpoint to create a new Payment Order.
 *
 * @see https://developer.swedbankpay.com/checkout/payment-menu#payment-menu-back-end
 * @param {object} req our Express request object
 * @param {object} res our Express response object
 */
module.exports.route = (req, res) => {
    const paymentOrder = req.body.paymentorder;

    try {
        checkPaymentOrder(req, paymentOrder);
    } catch (e) {
        sendError(res, e);
        return;
    }

    preparePaymentOrder(paymentOrder);

    post('/psp/paymentorders/', req.body)
        .then(pspResponse => {
            // payeeReference is our paymentId,
            // as set in preparePaymentOrder
            const paymentId = paymentOrder.payeeInfo.payeeReference;

            // Store the Swedbank Pay id (url) for this payment order
            // for further operations.
            // capture, etc. Note that this example does not really
            // use the stored id for anything, but a real application
            // would need it.
            global.database.insertPurchaseIdMapping(
                paymentId,
                pspResponse.paymentOrder.id);

            // If the payment order allows setting the payment instrument,
            // we must expose this capability to the client.
            const setInstrumentOp = findOperation(pspResponse, constants.opSetInstrument);
            if (setInstrumentOp) {
                const setInstrumentUrl = `/paymentorders/${paymentId}/setInstrument`;
                pspResponse.mobileSDK = { setInstrument: setInstrumentUrl };
            }

            res.status(200).send(pspResponse).end();
        })
        .catch(error => {
            console.log('Failed to call PSP payments API');
            console.log(error);
            sendError(res, error);
        });
};
