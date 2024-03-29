'use strict';

const express = require('express');
const app = express();
const { celebrate } = require('celebrate');
const { celebrateProblems } = require('./util/problems.js');
const constants = require('./util/constants.js');
const setConfigFromEnv = require('./util/env-config.js');

// Read our global configuration from disk
global.config = require('./appconfig.json');
// Patch it from environment variables
setConfigFromEnv(global.config);

// Instantiate our Database
const Database = require('./database/database.js');
global.database = new Database();

// Import middlewares
const auth = require('./auth_middleware.js');
app.use(auth);
const agent = require('./agent_middleware.js');
app.use(agent);

// Enable parsing request bodies
app.use(express.json());

// Import route handlers
const index = require('./routes/index.js');
const consumers = require('./routes/consumers.js');
const paymentorders = require('./routes/paymentorders.js');
const paymentorder = require('./routes/paymentorder.js');
const setInstrument = require('./routes/set-instrument.js');
const appleAssoc = require('./routes/apple-app-site-association.js');
const callbackReload = require('./routes/sdk-callback-reload.js');
const androidIntentCallback = require('./routes/android-intent-callback.js');
const iosUniversalLinkCallback = require('./routes/ios-universal-link-callback.js');
const payerTokens = require('./routes/payer-tokens.js');
const patchPayerToken = require('./routes/patch-payer-token.js');

const expandResource = require('./routes/expand.js');
const patchResource = require('./routes/patch.js');
const purchaseTokens = require('./routes/purchase-tokens.js');

// Specify our routes
app.get('/', index.route);
app.post('/consumers', celebrate({ body: consumers.schema }),
  consumers.route);
app.post('/paymentorders', celebrate({ body: paymentorders.schema }),
  paymentorders.route);
app.patch('/paymentorders/:id/setInstrument', celebrate({ body: setInstrument.schema }),
  setInstrument.route);
app.get('/payers/:ref/paymentTokens', payerTokens.route);
app.patch('/payers/:ref/paymentTokens/:token', celebrate({ body: patchPayerToken.schema }),
  patchPayerToken.route);
app.get(constants.appleAppSiteAssociationPath, appleAssoc.route);
app.get(constants.sdkCallbackReloadPath, celebrate({ query: callbackReload.schema }),
  callbackReload.route);
app.get(constants.androidIntentCallbackPath, celebrate({ query: androidIntentCallback.schema }),
  androidIntentCallback.route);
app.get(constants.iosUniversalLinkCallbackPath, celebrate({ query: iosUniversalLinkCallback.schema }),
  iosUniversalLinkCallback.route);

app.post('/expand', celebrate({ body: expandResource.schema }), expandResource.route);
app.patch('/patch',  celebrate({ body: patchResource.schema }), patchResource.route);
app.post('/tokens',  celebrate({ body: purchaseTokens.schema }), purchaseTokens.route);

// Handle the errors from Celebrate. Must be defined after the routes.
app.use(celebrateProblems);

if (process.env.SWEDBANKPAY_SERVER_BASE_URL) {
  console.log(`Overriding server address from environment: `
    + `${process.env.SWEDBANKPAY_SERVER_BASE_URL}`);
}

// Use X-Forwarded-Proto (TLS is terminated at the load balancer).
app.set('trust proxy', true);

// Start the server
const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
  console.log(`Node.js Merchant sample backend listening on port ${PORT}`);
  console.log('Press Ctrl+C to quit.');
});

module.exports = app;
