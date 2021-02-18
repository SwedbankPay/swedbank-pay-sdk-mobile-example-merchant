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
        const response = await get(`/psp/paymentorders/payerownedpaymenttokens/${payerRef}`);
        const ownedTokens = response ? response.payerOwnedPaymentTokens : null;
        const tokens = ownedTokens ? ownedTokens.paymentTokens : null;
        for (const tokenInfo of tokens) {
            const token = tokenInfo.paymentToken;
            if (token) {
                // All the tokens should have this field,
                // but we cannot form the delete url without it,
                // so we have to check.
                const deleteUrl = `/payers/${payerRef}/paymentTokens/${token}`;
                tokenInfo.mobileSDK = { delete: deleteUrl };
            }
        }
        res.status(200).send(response).end();
    } catch (e) {
        console.log('Failed to get payer owned tokens');
        console.log(e);
        sendError(res, e);
    }
};
