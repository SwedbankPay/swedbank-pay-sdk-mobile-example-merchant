'use strict';

const { Joi } = require('celebrate');
const { get, sendError } = require('../util/networking.js');

module.exports.schema = Joi.object().keys({
    resource: Joi.string()
        .required(),
    expand: Joi.string()
        .required()
});

/*
This is a generic way of expanding resources but here we are only using it to get the shippingAddress of payers. There is nothing stopping you from using it in a broader scope.
*/
module.exports.route = async (req, res) => {
    try {
        //There is no need to send this data to the client, so we don't need to authorize here. But if you want to have the logic on the client-side, it is a good thing to protect the data.
        //await authorize(req);
        const resource = req.body.resource;
        const expand = req.body.expand;
        
        //Decide on shipping costs by evaluating the paymentOrder.payer.shippingAddress fields and/or give the user alternatives.
        //const response = 
        await get(`${resource}?$expand=${expand}`);
        //console.log(response.paymentOrder.payer.shippingAddress)

        // Your implementation should at this point probably calculate shipping costs and tell the client to present this to the user, and/or alternative shipping methods.
        // In this example, we don't send in anything other than that things are ok.

        res.status(200).send().end();
    } catch (e) {
        console.log('Failed to expand resource');
        console.log(e);
        sendError(res, e);
    }
};