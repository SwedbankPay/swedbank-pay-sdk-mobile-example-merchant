'use strict';

const { Joi } = require('celebrate');
const { patchUrl, sendError } = require('../util/networking.js');
const { makeUnauthorizedProblem } = require('../util/problems.js');

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
            .valid('SetInstrument', 'Abort'),
        instrument: Joi.string(),
        abortReason: Joi.string()
    })
});

async function authorize(req) {
    // To allow, add "allowPatchResource": true to appconfig.json
    // A real implementation must do access control to only allow a user to access their own data.
    if (!global.config.allowPatchResource) {
        throw makeUnauthorizedProblem();
    }
}

module.exports.route = async (req, res) => {
    try {
        
        await authorize(req);
        
        let href = new URL(req.body.href);
        
        // Only allow patching known ids.
        // In a real-world application, only the same user may patch their own payments.
        const paymentId = href.pathname;
        //console.log(`Setting instrument of ${paymentId} to ${req.body.paymentorder.instrument}.`);
        const swedbankPayId = global.database.findPspPurchaseId(paymentId);
        //console.log(`Payex id ${swedbankPayId}.`);
        if (!swedbankPayId) {
            throw makeUnauthorizedProblem();
        }

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
