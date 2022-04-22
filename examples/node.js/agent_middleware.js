'use strict';

/**
 * User-agent middleware. We need to supply the SDK version to the system in every request.
 * More info here: https://developer.stage.swedbankpay.com/introduction#user-agent
 *
 * @param {object} req our Express request object
 * @param {object} res our Express response object
 * @param {object} next our next Express route handler
 */
const agent = (req, res, next) => {

    const userAgent = req.headers["user-agent"] || req.headers["User-Agent"] || req.headers["User-agent"];
    global.clientUserAgent = userAgent;
    const position = userAgent.lastIndexOf("/");
    if (userAgent && position > 0 && userAgent.includes("SwedbankPaySDK")) {
        // always give information on which SDK is used, forward it from the client to Swedbank, but make sure they know it's from the backend.
        const sdkVersion = userAgent.substring(position + 1);
        global.userAgent = "SwedbankPaySDK-Node/" + sdkVersion;
    }
    next();
};

module.exports = agent;
