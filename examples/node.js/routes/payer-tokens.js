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
        await authorize(req);
        const payerRef = req.params.ref;
        const tokens = await get(`/psp/paymentorders/payerownedpaymenttokens/${payerRef}`);
        res.status(200).send(tokens).end();
    } catch (e) {
        console.log('Failed to get payer owned tokens');
        console.log(e);
        sendError(res, e);
    }
};
