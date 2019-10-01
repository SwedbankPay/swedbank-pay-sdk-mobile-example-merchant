'use strict';

const { get, sendError } = require('../util/networking.js');

/**
 * Retrieves a single payment order by its ID (in path param).
 * 
 * @param {object} req our Express request object
 * @param {object} res our Express response object
 */
module.exports.route = (req, res) => {
    const id = req.params.id;

    // Find the corresponding PSP order 
    const paymentOrder = global.database.findPurchase(id);

    // The order must exist and belong to the user making this request
    if (!paymentOrder || (paymentOrder.ownerUserId !== req.userId)) {
        console.log(`Purchase not found!`);
        res.status(404).end();
        return;
    }

    get(paymentOrder.pspPurchaseId)
        .then(pspResponse => {
            const payload = {
                state: pspResponse.paymentOrder.state,
                operations: pspResponse.operations
            };

            res.status(200).send(payload).end();
        })
        .catch(error => {
            console.log('Failed to call PSP payments API');
            console.log(error);
            sendError(res, error);
        });
};
