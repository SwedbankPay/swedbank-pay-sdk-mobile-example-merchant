'use strict';

const constants = require('./constants.js');
const contentTypeParser = require('content-type-parser');
const fetch = require('node-fetch');
const problemJson = require('problem-json');
const problems = require('./problems.js');

/**
 * Attempts to read a json response.
 * @param {object} response a node-fetch Response
 * @returns {object} the json read from the response
 * @throws {object} a problem-json Document if the response contained one, or the Content-Type was unexpected
 */
const getJsonOrThrowProblem = async (response) => {
    const contentType = contentTypeParser(response.headers.get('Content-Type'));
    const type = contentType.type;
    const subtype = contentType.subtype;
    const isJson = type == 'application' && (subtype == 'json' || subtype.endsWith('+json'));
    if (isJson) {
        const body = await response.json();
        if (response.ok) {
            return body;
        } else if (subtype == 'problem+json' || subtype == 'json') {
            if (!Number.isInteger(body.status)) {
                body.status = response.status;
            }
            throw body;
        } else {
            throw problems.makeBadGatewayProblem(response, body);
        }
    } else {
        const bodyText = await response.text();
        throw problems.makeBadGatewayProblem(response, bodyText);
    }
};

/**
 * Performs a HTTP request.
 *
 * @param {string} method the HTTP method to use.
 * @param {string} path the path to the requested resource; eg /path
 * @param {object} body the POST body as an object
 * @returns {object} Promise whose then() gets a single parameter that is the
 * server response as a parsed object representing the JSON response content.
 */
const request = async (method, path, body) => {
    const baseUrl = process.env.SWEDBANKPAY_SERVER_BASE_URL ||
        global.config.payexBaseUrl;
    const url = `${baseUrl}${path}`;
    const opts = {
        method: method,
        compress: false,
        headers: {
            'Accept': 'application/json',
            'Authorization': `Bearer ${global.config.merchantToken}`
        }
    };

    if (body) {
        opts.headers['Content-Type'] = 'application/json';
        opts.body = JSON.stringify(body);
    }

    try {
        const response = await fetch(url, opts);
        return await getJsonOrThrowProblem(response);
    } catch (e) {
        if (Number.isInteger(e.status)) {
            throw e;
        } else {
            console.log(e);
            if (e.name == 'FetchError') {
                throw problems.wrapFetchError(e);
            } else {
                // This should not happen.
                throw e;
            }
        }
    }
};

/**
 * Performs a HTTP POST request.
 *
 * @param {string} path the path to the requested resource; eg /path
 * @param {object} body the POST body as an object
 * @returns {object} Promise whose then() gets a single parameter that is the
 * server response as a parsed object representing the JSON response content.
 */
module.exports.post = async (path, body) => {
    return await request('post', path, body);
};

/**
 * Performs a HTTP GET request.
 * 
 * @param {string} path the path to the requested resource; eg /path
 * @returns {object} Promise whose then() gets a single parameter that is the 
 * server response as a parsed object representing the JSON response content.
 */
module.exports.get = async (path) => {
    return await request('get', path);
};

/**
 * Outputs an error throw from this module to an express response
 * @param {object} res an express response awaiting input
 * @param {object} error an error thrown from this module
 */
module.exports.sendError = function (res, error) {
    const problem = Number.isInteger(error.status)
        ? error
        : new problemJson.Document({ status: 500 });
    problems.sendProblem(res, problem);
    res.end();
};
