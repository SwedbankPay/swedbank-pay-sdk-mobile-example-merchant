'use strict';

const { Joi } = require('celebrate');
const { get, patchUrl, sendError } = require('../util/networking.js');
const { makeUnauthorizedProblem, makeNotFoundProblem } = require('../util/problems.js');
const constants = require('../util/constants.js');
const findOperation = require('../util/find-operation.js');

async function authorize(req) {
    // To allow, add "allowDeletePayerOwnedPaymentToken": true to appconfig.json
    // A real implementation must do access control to only
    // allow a user to access their own tokens.
    if (!global.config.allowDeletePayerOwnedPaymentToken) {
        throw makeUnauthorizedProblem();
    }
}

module.exports.schema = Joi.object().keys({
    state: Joi.string()
        .valid('Deleted')
        .required(),
    comment: Joi.string()
        .allow('')
        .required()
});

function findToken(tokens, token) {
    if (Array.isArray(tokens)) {
        return tokens.find((t) => {
            return t.paymentToken == token;
        });
    } else {
        return null;
    }
}

async function getEndpoint(payerRef, token) {
    const response = await get(`/psp/paymentorders/payerownedpaymenttokens/${payerRef}`);
    const ownedTokens = response ? response.payerOwnedPaymentTokens : null;
    const tokens = ownedTokens ? ownedTokens.paymentTokens : null;
    const tokenInfo = findToken(tokens, token);
    if (!tokenInfo) throw makeNotFoundProblem()
    const op = findOperation(tokenInfo, constants.opDeletePaymentToken);
    return op ? op.href : null;
}

module.exports.route = async (req, res) => {
    try {
        await authorize(req);
        const payerRef = req.params.ref;
        const token = req.params.token;
        const endpoint = await getEndpoint(payerRef, token);
        if (!endpoint) throw makeNotFoundProblem('Delete operation missing');
        const response = patchUrl(endpoint, req.body);
        res.status(200).send(response).end();
    } catch (e) {
        console.log('Failed to delete payment token');
        console.log(e);
        sendError(res, e);
    }
};
