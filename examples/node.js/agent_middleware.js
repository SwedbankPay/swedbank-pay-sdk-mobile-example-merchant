'use strict';

/**
 * User-agent middleware. We need to supply the SDK version to the system in every request. 
 *
 * @param {object} req our Express request object
 * @param {object} res our Express response object
 * @param {object} next our next Express route handler
 */
const agent = (req, res, next) => {

    const userAgent = req.headers["user-agent"] || req.headers["User-Agent"] || req.headers["User-agent"];
    if (userAgent && userAgent.includes("SwedbankPaySDK")) {
        // always give information on which SDK is used, just forward it from the client to Swedbank.
        //console.log(`userAgent: ${userAgent}`);
        global.userAgent = userAgent;
    }
    next();
};

module.exports = agent;
