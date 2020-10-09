'use strict';

const { Joi } = require('celebrate');
const { get, patchUrl, sendError } = require('../util/networking.js');
const { makeNotFoundProblem } = require('../util/problems.js');
const constants = require('../util/constants.js');
const findOperation = require('../util/find-operation.js');

module.exports.schema = Joi.object().keys({
    paymentorder: Joi.object().keys({
        operation: Joi.string()
            .valid('SetInstrument')
            .required(),
        instrument: Joi.string().required()
    })
});

async function getEndpoint(swedbankPayId) {
    const paymentorder = await get(swedbankPayId);
    const op = findOperation(paymentorder, constants.opSetInstrument);
    return op ? op.href : null;
}

module.exports.route = async (req, res) => {
    try {
        const paymentId = req.params.id;
        console.log(`Setting instrument of ${paymentId} to ${req.body.paymentorder.instrument}.`);
        const swedbankPayId = global.database.findPspPurchaseId(paymentId);
        console.log(`Payex id ${swedbankPayId}.`);
        const endpoint = swedbankPayId ? await getEndpoint(swedbankPayId) : null;
        if (!endpoint) {
            throw makeNotFoundProblem();
        }
        console.log(`Payex set endpoint ${endpoint}.`);

        const body = {
            paymentorder: {
                operation: 'SetInstrument',
                instrument: req.body.paymentorder.instrument
            }
        };
        const swedbankPayResponse = await patchUrl(endpoint, body);
        res.status(200).send(swedbankPayResponse).end();
    } catch (e) {
        console.log('Failed to set paymentorder instrument');
        console.log(e);
        sendError(res, e);
    }
};
