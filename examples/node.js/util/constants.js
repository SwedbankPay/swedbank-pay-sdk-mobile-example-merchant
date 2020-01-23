'use strict';

// Name of our HTTP header for our API key
const apiKeyHeaderName = "x-payex-sample-apikey";

// Name of our HTTP header for our access token
const accessTokenHeaderName = "x-payex-sample-access-token";

// Problem (RFC7807) type for backend timeouts
const problemGatewayTimeout = 'https://api.payex.com/psp/errordetail/mobilesdk/gatewaytimeout';

// Problem type for bogus responses from backend
const problemBadGateway = 'https://api.payex.com/psp/errordetail/mobilesdk/badgateway';

// Problem type for requests with invalid credentials from client
const problemUnauthorized = 'https://api.payex.com/psp/errordetail/mobilesdk/unauthorized';

// Problem type for bogus requests from client
const problemBadRequest = 'https://api.payex.com/psp/errordetail/mobilesdk/badrequest';

const appleAppSiteAssociationPath = '/.well-known/apple-app-site-association';

const assetLinksPath = '/.well-known/assetlinks.json';

const sdkCallbackReloadPath = '/sdk-callback/reload';

module.exports.apiKeyHeaderName = apiKeyHeaderName;
module.exports.accessTokenHeaderName = accessTokenHeaderName;
module.exports.problemGatewayTimeout = problemGatewayTimeout;
module.exports.problemBadGateway = problemBadGateway;
module.exports.problemUnauthorized = problemUnauthorized;
module.exports.problemBadRequest = problemBadRequest;
module.exports.appleAppSiteAssociationPath = appleAppSiteAssociationPath;
module.exports.assetLinksPath = assetLinksPath;
module.exports.sdkCallbackReloadPath = sdkCallbackReloadPath;
