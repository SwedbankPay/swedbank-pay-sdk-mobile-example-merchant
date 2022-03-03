'use strict';

const { Joi } = require('celebrate');
const { patchUrl, sendError } = require('../util/networking.js');

/**
 * General patch operations for all supported by the example backend under V3. 
 * The app just sends a POST for the patch with parameters to the backend which performs the patch and returns the JSON.
 * Every patch needs a href.
 * 
 */

module.exports.schema = Joi.object().keys({
    href: Joi.string().required(),
    paymentorder: Joi.object().keys({
        operation: Joi.string()
            .valid('SetInstrument')
            .required(),
        instrument: Joi.string().required()
    })
});

module.exports.route = async (req, res) => {
    try {
        let href = new URL(req.body.href);

        // No purpose in trying to restrict by known ids. 
        //const paymentId = href.pathname;
        //console.log(`Setting instrument of ${paymentId} to ${req.body.paymentorder.instrument}.`);
        //const swedbankPayId = global.database.findPspPurchaseId(paymentId);
        //console.log(`Payex id ${swedbankPayId}.`);

        const body = {
            paymentorder: req.body.paymentorder
        };
        const swedbankPayResponse = await patchUrl(href, body);
        res.status(200).send(swedbankPayResponse).end();
    } catch (e) {
        console.log('Failed to set paymentorder instrument');
        console.log(e);
        sendError(res, e);
    }
};
