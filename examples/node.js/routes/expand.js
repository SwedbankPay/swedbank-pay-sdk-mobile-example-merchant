'use strict';

const { Joi } = require('celebrate');
const { get, sendError } = require('../util/networking.js');

module.exports.schema = Joi.object().keys({
    resource: Joi.string()
        .required(),
    expand: Joi.array().items(Joi.string())
        .required()
});

/*
This is a generic way of expanding resources.
*/
const expand = async (req, res) => {
    //There is no need to send this data to the client, so we don't need to authorize here. But if you want to have the logic on the client-side, it is a good thing to protect the data.
    //await authorize(req);
    const resource = req.body.resource;
    const expand = req.body.expand;
    
    return await get(`${resource}?$expand=${expand}`);
};

module.exports.expand = expand;

module.exports.route = async (req, res) => {
    try {

        //Decide on shipping costs by evaluating the paymentOrder.payer.shippingAddress fields and/or give the user alternatives.
        const response = 
        await expand(req, res);

        // Your implementation should at this point perform the task needed from the retrieved information. E.g. if expanding a payer's address info, calculate shipping costs and tell the client to present this to the user, and/or alternative shipping methods.

        // Most likely you shouldn't need to send any of this info back to the mobile client.
        res.status(200).send(response).end();
    } catch (e) {
        console.log('Failed to expand resource');
        console.log(e);
        sendError(res, e);
    }
};