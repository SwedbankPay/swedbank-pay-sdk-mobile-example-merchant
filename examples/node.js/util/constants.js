'use strict';

// Name of our HTTP header for our API key
const apiKeyHeaderName = "x-payex-sample-apikey";

// Name of our HTTP header for our access token 
const accessTokenHeaderName = "x-payex-sample-access-token";

// Problem (RFC7807) type for backend timeouts
const problemGatewayTimeout = 'https://api.payex.com/psp/errordetail/mobilesdk/gatewaytimeout';

// Problem type for bogus responses from backend
const problemBadGateway = 'https://api.payex.com/psp/errordetail/mobilesdk/badgateway';

module.exports.apiKeyHeaderName = apiKeyHeaderName;
module.exports.accessTokenHeaderName = accessTokenHeaderName;
module.exports.problemGatewayTimeout = problemGatewayTimeout;
module.exports.problemBadGateway = problemBadGateway;