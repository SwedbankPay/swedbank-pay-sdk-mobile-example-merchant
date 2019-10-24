'use strict';

const express = require('express');
const app = express();
const { celebrate } = require('celebrate');
const { celebrateProblems } = require('./util/problems.js');

// Read our global configuration from disk
global.config = require('./appconfig.json');

// Instantiate our Database
const Database = require('./database/database.js');
global.database = new Database();

// Import middlewares
const auth = require('./auth_middleware.js');
app.use(auth);

// Enable parsing request bodies
app.use(express.json());

// Import route handlers
const index = require('./routes/index.js');
const consumers = require('./routes/consumers.js');
const paymentorders = require('./routes/paymentorders.js');
const paymentorder = require('./routes/paymentorder.js');

// Specify our routes
app.get('/', index.route);
app.post('/consumers', celebrate({ body: consumers.schema }),
  consumers.route);
app.post('/paymentorders', celebrate({ body: paymentorders.schema }),
  paymentorders.route);
app.get('/paymentorder/:id', paymentorder.route);

// Handle the errors from Celebrate. Must be defined after the routes.
app.use(celebrateProblems);

if (process.env.SWEDBANKPAY_SERVER_BASE_URL) {
  console.log(`Overriding server address from environment: `
    + `${process.env.SWEDBANKPAY_SERVER_BASE_URL}`);
}

// Start the server
const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
  console.log(`Node.js Merchant sample backend listening on port ${PORT}`);
  console.log('Press Ctrl+C to quit.');
});

module.exports = app;
