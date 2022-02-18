'use strict';

const { get, sendError } = require('../util/networking.js');
const { makeUnauthorizedProblem } = require('../util/problems.js');

module.exports.route = async (req, res) => {
    try {
        //There is no need to send this data to the client, so we don't need to authorize here. But if you want to have the logic on the client-side, it is a good thing to protect the data.
        //await authorize(req);
        const payerRef = req.params.ref;
        const psp = req.params.psp; //This is not likely to change, but in case I allow some flexibility here.
        //const response = //Decide on shipping costs by evaluating the paymentOrder.payer.shippingAddress fields and/or give the user alternatives.
        await get(`/${psp}/paymentorders/${payerRef}?$expand=payer`);

        // Your implementation should at this point probably calculate shipping costs and tell the client how to show this to the user, and present alternative shipping methods.
        // In this example, we don't send in anything.

        res.status(200).send().end();
    } catch (e) {
        console.log('Failed to expland payer');
        console.log(e);
        sendError(res, e);
    }
};