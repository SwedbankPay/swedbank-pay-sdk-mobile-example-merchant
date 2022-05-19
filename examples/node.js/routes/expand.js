'use strict';

const { Joi } = require('celebrate');
const { get, sendError } = require('../util/networking.js');
const { makeUnauthorizedProblem } = require('../util/problems.js');

/**
This is a generic way of expanding resources.
*/

module.exports.schema = Joi.object().keys({
    resource: Joi.string()
        .required(),
    expand: Joi.array().items(Joi.string())
        .required()
});

async function authorize(req) {
    // To allow, add "allowExpandFullResource": true to appconfig.json
    // A real implementation must do access control to only allow a user to access their own data.
    if (!global.config.allowExpandFullResource) {
        throw makeUnauthorizedProblem();
    }
}

const expand = async (req, res) => {
    //The expand function is available to others. See purchase-tokens.js for an example. Note that it is usually never needed to send this data back to the client, this is only to illustrate functionality.
    
    await authorize(req);
    
    const resource = req.body.resource;
    const expand = req.body.expand;
    
    return await get(`${resource}?$expand=${expand}`);
};
module.exports.expand = expand;

module.exports.route = async (req, res) => {
    try {

        // you must specifically allow 
        const response = await expand(req, res);
        
        // Most likely you shouldn't ever send any of this info back to the mobile client. This is an example of how it works, and from this information you can store the tokens, or anything else needed. 
        res.status(200).send(response).end();
    } catch (e) {
        console.log('Failed to expand resource');
        console.log(e);
        sendError(res, e);
    }
};