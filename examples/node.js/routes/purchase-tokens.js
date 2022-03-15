'use strict';

const { Joi } = require('celebrate');
const { get, sendError } = require('../util/networking.js');

/*
Purchase tokens are recurrenceToken and unscheduledToken, that can be created by first verifying or completing a purchase with the corresponding "generate" property set. See documentation on verify (https://developer.swedbankpay.com/checkout-v3/payments-only/features/optional/verify), recur (https://developer.swedbankpay.com/checkout-v3/payments-only/features/optional/recur) or unscheduled (https://developer.swedbankpay.com/checkout-v3/payments-only/features/optional/unscheduled) for more info.

Note that these are server-server tokens and should never leave the server. Here we just check if they exist and reply with a status for testing purposes. 
They are returned as part of a paid expansion result, and looks like this:
"tokens": [
    {
        "type": "recurrence",
        "token": "a7d7d780-98ba-4466-befe-e5428f716c30",
        "name": "458109******3517",
        "expiryDate": "12/2030"
    },
    {
        "type": "unscheduled",
        "token": "0c43b168-dcd5-45d1-b9c4-1fb8e273c799",
        "name": "458109******3517",
        "expiryDate": "12/2030"
    }
],
*/

// Getting token status requires a get on the  
module.exports.schema = Joi.object().keys({
    href: Joi.string().required(),
});

module.exports.route = async (req, res) => {
    try {

        // to get the tokens you need to expand the /paid resource of the payment.
        const orderID = req.body.href;
        const response = await get(orderID);

        let tokens = response && response.paid && response.paid.tokens;
        if (!tokens || tokens.length == 0) {
            res.status(500).send({ error: "No recurrence or unscheduled token in response", paymentOrder: response.paymentOrder }).end();
            return;
        }
        // Note: Normally you would only do this server-to-server and not involve the client at all - this is just an example to show how to retreive the tokens after the user has verified the purchase/subscription.
        var tokenResponse = {};
        tokens.forEach((token) => {
            if (token.type == "recurrence") {
                tokenResponse.recurrence = true;
            }
            else if (token.type == "unscheduled") {
                tokenResponse.unscheduled = true;
            }
        });

        res.status(200).send(tokenResponse).end();
    } catch (e) {
        console.log('Failed to get payer owned tokens');
        console.log(e);
        sendError(res, e);
    }
};
