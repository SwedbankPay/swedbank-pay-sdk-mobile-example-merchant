'use strict';

const constants = require('../util/constants.js');
const chai = require('chai');
const env = process.env;
const baseUrl = process.env.SWEDBANKPAY_SERVER_BASE_URL || global.config.payexBaseUrl

const headers = { 
	"User-Agent": "SwedbankPaySDK-NodeTestSuite/3.0",
	Accept: 'application/json', 
	[constants.apiKeyHeaderName]: [env["API_KEY"]],
	[constants.accessTokenHeaderName]: "doot_doot",
};

function checkCredentials(res, shouldBeSucceeding = true) {
	chai.assert(res.status != 401, "Getting 401, is the credentials missing?\n" + res.text);
	if (res.status != 200 && shouldBeSucceeding) {
		console.log("baseUrl: " + baseUrl)
		printResult(res)
	}
}

function printResult(res) {
	console.log(JSON.stringify(JSON.parse(res.text), null, 4));
}

module.exports = {
	headers,
    printResult,
    checkCredentials,
    printResult,
};