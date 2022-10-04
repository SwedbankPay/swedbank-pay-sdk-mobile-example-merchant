'use strict';

const constants = require('./util/constants.js');
const problems = require('./util/problems.js');

/**
 * Authentication middleware. Checks the presence and correctness of
 * our API key.
 *
 * @param {object} req our Express request object
 * @param {object} res our Express response object
 * @param {object} next our next Express route handler
 */
const auth = (req, res, next) => {
    
    if (global.config.merchantToken == "1ea83be9fda7f77609ddb211e62e4124d0647183259f02e58e33d05dfa9dcb80") {
        console.log("merchantToken matches")
    } else {
        console.log("slice " + global.config.merchantToken.slice(0, 4) + " slice")
    }
    
    if (global.config.merchantId == "e485f88c-e176-4af4-8da0-cdc3739fdb8a") {
        console.log("merchantId matches")
    } else {
        console.log("slice " + global.config.merchantId.slice(0, 4) + " slice")
    }
    
    if (req.path == constants.appleAppSiteAssociationPath
       || req.path == constants.assetLinksPath) {
        console.log('Skipping authentication middleware for public metadata.');
        next();
        return;
    }
    if (req.path == constants.sdkCallbackReloadPath
       || req.path == constants.androidIntentCallbackPath
       || req.path == constants.iosUniversalLinkCallbackPath) {
        console.log('Skipping authentication middleware for callback page.');
        next();
        return;
    }

    console.log('Running authentication middleware.');

    const apiKey = req.headers[constants.apiKeyHeaderName];
    console.log(global.config.apiKey + " key " + apiKey)

    if (global.config.apiKey !== apiKey) {
        console.log('Request has a missing or incorrect API key.');
        problems.sendProblem(res, problems.makeUnauthorizedProblem());
        res.end();
        return;
    }

    const accessToken = req.headers[constants.accessTokenHeaderName];

    if (!accessToken) {
        console.log('Request is missing the access token.');
        problems.sendProblem(res, problems.makeUnauthorizedProblem());
        res.end();
        return;
    }

    // Expect an arbitrary minimum length for the authentication token;
    // this can be used for generating a different kind of 401 error for testing
    if (accessToken.length < 5) {
        problems.sendProblem(res, problems.makeUnauthorizedProblem('access token too short'));
        res.end();
        return;
    }

    // Here we would normally validate our access token and use if
    // to find out user ID value. For demo purposes, we'll just use the
    // access token as our user ID.
    req.userId = accessToken;

    next();
};

module.exports = auth;
