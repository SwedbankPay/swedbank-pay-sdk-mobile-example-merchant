'use strict';

const fetch = require('node-fetch');

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
    const baseUrl = process.env.PAYEX_SERVER_BASE_URL ||
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

    const response = await fetch(url, opts);

    if (response.ok) {
        return await response.json();
    } else {
        console.log(`HTTP ${method} ${path} failed, code: ${response.status}`);
        console.log(`Status text: ${response.statusText}`);

        const bodyText = await response.text();
        console.log(`Response body: ${bodyText}`);

        throw Error(`HTTP ${method} ${path} failed with status code ` +
            `${response.status}: ${response.statusText}`);
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
