'use strict';

const constants = require('./util/constants.js');

/**
 * Authentication middleware. Checks the presence and correctness of 
 * our API key.
 * 
 * @param {object} req our Express request object
 * @param {object} res our Express response object
 * @param {object} next our next Express route handler
 */
const auth = (req, res, next) => {
    console.log('Running authentication middleware.');

    const apiKey = req.headers[constants.apiKeyHeaderName];

    if (global.config.apiKey !== apiKey) {
        console.log('Request has a missing or incorrect API key.');
        res.status(401).send('Unauthorized').end();
        return;
    }

    const accessToken = req.headers[constants.accessTokenHeaderName];

    if (!accessToken) {
        console.log('Request is missing the access token.');
        res.status(401).send('Unauthorized').end();
        return;
    }

    // Expect an arbitrary minimum length for the authentication token; 
    // this can be used for generating a different kind of 401 error for testing
    if (accessToken.length < 5) {
        res.status(401).send({ message: 'access token too short' }).end();
        return;
    }

    // Here we would normally validate our access token and use if
    // to find out user ID value. For demo purposes, we'll just use the
    // access token as our user ID.
    req.userId = accessToken;

    next();
};

module.exports = auth;
