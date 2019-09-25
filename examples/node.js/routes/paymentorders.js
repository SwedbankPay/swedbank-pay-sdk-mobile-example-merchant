'use strict';

const { Joi } = require('celebrate');
const { post } = require('../util/networking.js');

/**
 * Payload scheme for /paymentorders.
 */
module.exports.schema = Joi.object().keys({
    consumerProfileRef: Joi.string(),
    merchantData: Joi.object().required(),
});

/**
 * Validation schema for our merchant data format.
 */
const merchantDataSchema = Joi.object().keys({
    basketId: Joi.string()
        .required(),
    currency: Joi.string()
        .min(3)
        .max(3)
        .required(),
    languageCode: Joi.string()
        .min(2)
        .max(6)
        .required(),
    items: Joi.array().items(Joi.object({
        itemId: Joi.string()
            .required(),
        quantity: Joi.number()
            .integer()
            .required(),
        price: Joi.number()
            .integer()
            .required(),
        vat: Joi.number()
            .integer()
            .required()
    }))
});

/**
 * Creates a Payment Order based on the merchant configuration, purchase data 
 * and the incoming Request object.
 *
 * @param {object} req our Express request object
 * @returns {number} ID allocated for the payment
 */
const createPaymentOrder = (req) => {
    const merchantData = req.body.merchantData;

    // Attach our user ID - this will indicate the owner of this order
    merchantData.ownerUserId = req.userId;

    const paymentId = global.database.insertPurchse(merchantData);

    const priceReducer = (acc, val) => acc + val.price;
    const vatReducer = (acc, val) => acc + val.vat;

    const totalPrice = merchantData.items.reduce(priceReducer, 0);
    const totalVat = merchantData.items.reduce(vatReducer, 0);

    const baseUrl = `https://${req.headers["host"]}`;

    const paymentOrder = {
        operation: 'Purchase',
        currency: merchantData.currency,
        amount: totalPrice,
        vatAmount: totalVat,
        description: merchantData.basketId,
        userAgent: req.headers["user-agent"],
        language: merchantData.languageCode,
        generateRecurrenceToken: false,
        disablePaymentMenu: false,
        urls: {
            hostUrls: [baseUrl],
            completeUrl: `${baseUrl}/complete`,
            cancelUrl: `${baseUrl}/cancel`
        },
        payeeInfo: {
            payeeId: global.config.merchantId,
            payeeReference: paymentId
        }
    };

    if (req.body.consumerProfileRef) {
        paymentOrder.payer = {
            consumerProfileRef: req.body.consumerProfileRef
        };
    }

    return paymentOrder;
};

/**
 * Calls the PSP endpoint to create a new Payment Order.
 * 
 * The following format is used for our 'merchant data':
 * 
 * {
 *   "basketId": "123",
 *   "currency": "SEK",
 *   "languageCode": "sv-SE",
 *   "items": [
 *     {
 *      "itemId": "1",
 *       "quantity": 1,
 *       "price": 1200,
 *       "vat": 300
 *     },
 *     {
 *       "itemId": "2",
 *       "quantity": 2,
 *      "price": 400,
 *      "vat": 75
 *     }
 *   ]
 * }
 *
 * @see https://developer.payex.com/xwiki/wiki/developer/view/Main/ecommerce/technical-reference/payment-orders-resource/#HCreatingapaymentorder
 * @param {object} req our Express request object
 * @param {object} res our Express response object
 */
module.exports.route = (req, res) => {
    // Parse & validate merchant data
    const merchantData = req.body.merchantData;

    const { error, value } = merchantDataSchema.validate(merchantData);
    if (error) {
        console.log(`merchantData fails to validate: ${JSON.stringify(merchantData)}`);
        const message = error.details.map(i => i.message).join(',');
        res.status(400).json({ error: message });
        return;
    }

    const paymentOrder = createPaymentOrder(req);

    const postBody = {
        paymentorder: paymentOrder
    };

    post('/psp/paymentorders/', postBody)
        .then(pspResponse => {
            global.database.insertPurchaseIdMapping(
                paymentOrder.payeeInfo.payeeReference,
                pspResponse.paymentOrder.id);

            const payload = {
                url: `/paymentorder/${paymentOrder.payeeInfo.payeeReference}`,
                state: pspResponse.paymentOrder.state,
                operations: pspResponse.operations
            };

            res.status(200).send(payload).end();
        })
        .catch(error => {
            console.log(`Failed to call PSP payments API: ${error}`);
            res.status(500).end();
        });
}; 
