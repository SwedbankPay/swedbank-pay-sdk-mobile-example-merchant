'use strict';

const { Joi } = require('celebrate');
const { post } = require('../util/networking.js');

/**
 * Request payload schema for /consumers
 */
module.exports.schema = Joi.object().keys({
    consumerCountryCode: Joi.string()
        .alphanum()
        .min(2)
        .max(4)
        .required(),
    msisdn: Joi.string()
        .min(6)
        .max(16),
    email: Joi.string().email(),
    nationalIdentifier: Joi.object().keys({
        socialSecurityNumber: Joi.string().required(),
        countryCode: Joi.string().required()
    })
});

/**
 * Calls the PSP endpoint to initiate a payment session.
 *
 * @see https://developer.payex.com/xwiki/wiki/developer/view/Main/ecommerce/technical-reference/consumers-resource/#HInitiateConsumerSession
 * @param {object} req our Express request object
 * @param {object} res our Express response object
 */
module.exports.route = (req, res) => {
    const postBody = {
        operation: "initiate-consumer-session",
        msisdn: req.body.msisdn,
        email: req.body.email,
        consumerCountryCode: req.body.consumerCountryCode,
        nationalIdentifier: req.body.nationalIdentifier
    };

    post('/psp/consumers/', postBody)
        .then(pspResponse => {
            const payload = {
                operations: pspResponse.operations
            };

            res.status(200).send(payload).end();
        })
        .catch(error => {
            console.log(`Failed to call PSP consumers API: ${error}`);
            res.status(500).end();
        });
};