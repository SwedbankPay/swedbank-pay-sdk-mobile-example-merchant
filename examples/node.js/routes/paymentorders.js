'use strict';

const { Joi } = require('celebrate');
const { post, sendError } = require('../util/networking.js');

/**
 * Validation schema for paymentorder.
 */
const paymentOrderSchema = Joi.object().keys({
    operation: Joi.string()
        .valid('Purchase', 'Verify')
        .required(),
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
    generateRecurrenceToken: Joi.boolean()
        .required(),
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
            .length(30)
            .required(),
        payeeName: Joi.string().allow(''),
        productCategory: Joi.string().allow(''),
        orderReference: Joi.string()
            .allow('')
            .length(50),
        subsite: Joi.string()
            .allow('')
            .length(40)
    }).required(),
    payer: Joi.object({
        consumerProfileRef: Joi.string()
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
    })
});

/**
 * Payload scheme for /paymentorders.
 */

module.exports.schema = Joi.object().keys({
    paymentorder: paymentOrderSchema//.required()
});


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
    // Force amount to 1 for safeguarding against errors in prod testing
    paymentOrder.amount = 100;
    paymentOrder.vatAmount = 25;
    const orderItems = paymentOrder.orderItems;
    if (orderItems) {
        for (const item of paymentOrder.orderItems) {
            item.unitPrice = 0;
            if (item.discountPrice) item.discountPrice = 0;
            item.amount = 0;
            item.vatAmount = 0;
        }
        if (orderItems.length > 0) {
            const firstItem = orderItems[0];
            firstItem.quantity = 1;
            firstItem.unitPrice = 100;
            if (firstItem.discountPrice) firstItem.discountPrice = 0;
            firstItem.vatPercent = 2500;
            firstItem.amount = 100;
            firstItem.vatAmount = 25;
        }
    }

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
    preparePaymentOrder(paymentOrder);

    post('/psp/paymentorders/', req.body)
        .then(pspResponse => {
            // Store the Swedbank Pay id (url) for this payment order
            // for capture, etc. Note that this example does not really
            // use the stored id for anything, but a real application
            // would need it.
            global.database.insertPurchaseIdMapping(
                paymentOrder.payeeInfo.payeeReference,
                pspResponse.paymentOrder.id);

            res.status(200).send(pspResponse).end();
        })
        .catch(error => {
            console.log('Failed to call PSP payments API');
            console.log(error);
            sendError(res, error);
        });
};
