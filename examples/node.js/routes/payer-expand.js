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
        console.log('Failed to expland payer');
        console.log(e);
        sendError(res, e);
    }
};