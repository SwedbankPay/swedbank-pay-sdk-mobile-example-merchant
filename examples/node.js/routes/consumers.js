'use strict';

const { Joi } = require('celebrate');
const { post, sendError } = require('../util/networking.js');

/**
 * Request payload schema for /consumers
 */
module.exports.schema = Joi.object().keys({
    operation: Joi.string()
        .valid('initiate-consumer-session')
        .required(),
    language: Joi.string()
        .required(),
    shippingAddressRestrictedToCountryCodes: Joi.array().items(Joi.string())
        .required()
});

/**
 * Calls the PSP endpoint to initiate a payment session.
 *
 * @see https://developer.swedbankpay.com/checkout/checkin#checkin-back-end
 * @param {object} req our Express request object
 * @param {object} res our Express response object
 */
module.exports.route = (req, res) => {
    post('/psp/consumers/', req.body)
        .then(pspResponse => {
            res.status(200).send(pspResponse).end();
        })
        .catch(error => {
            console.log('Failed to call PSP consumers API');
            console.log(error);
            sendError(res, error);
        });
};
