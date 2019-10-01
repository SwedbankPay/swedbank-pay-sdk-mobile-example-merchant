'use strict';

const constants = require('./constants.js');
const { isCelebrate } = require('celebrate');
const problemJson = require('problem-json');

/**
 * Sends a problem+json response, using the status code in the problem.
 * @param {object} res an express response object awaiting input
 * @param {object} the problem object to send
 */
function sendProblem(res, problem) {
    return res.status(problem.status).set('Content-Type', 'application/problem+json').send(problem);
}

/**
 * Express middleware that reports Celebrate validation
 * errors as problem+json.
 */
function celebrateProblems(err, req, res, next) {
    if (!isCelebrate(err)) {
        return next(err);
    }

    const {
        joi,
        meta,
    } = err;

    const extension = new problemJson.Extension({
        source: meta.source,
        errors: joi.details
    });
    const problem = new problemJson.Document({
        type: constants.problemBadRequest,
        title: 'Bad Request',
        status: 400,
        detail: joi.message
    }, extension);

    return sendProblem(res, problem);
}

/**
 * Creates a problem-json Document representing an unexpected
 * response from the backend.
 * @param {object} response the unexpected response
 * @param {object} body the response body
 * @returns a problem-json Document representing the error
 */
function makeBadGatewayProblem(response, body) {
    const extension = new problemJson.Extension({
        body: body
    });
    return new problemJson.Document({
        type: constants.problemBadGateway,
        title: response.statusText,
        status: response.status
    }, extension);
}

/**
 * Creates a problem-json Document representing an unauthorized
 * request error.
 * @param {string} message an optional message to add to the problem
 * @returns a problem-json Document representing the error
 */
function makeUnauthorizedProblem(message) {
    return new problemJson.Document({
        type: constants.problemUnauthorized,
        title: 'Unauthorized',
        status: 401,
        detail: message
    });
}

/**
 * Creates a problem-json Document representing a node-fetch FetchError
 * @param {object} fetchError a FetchError thrown from node-fetch
 * @returns {object} a problem-json Document representing that error
 */
function wrapFetchError(fetchError) {
    const errorType = fetchError.type;
    const isTimeout = errorType == 'request-timeout' || errorType == 'body-timeout';

    const problemType = isTimeout ? constants.problemGatewayTimeout : constants.problemBadGateway;
    const title = isTimeout ? 'Gateway Timeout' : 'Bad Gateway';
    const status = isTimeout ? 504 : 502;
    const extension = new problemJson.Extension({
        fetchError: fetchError
    });
    return new problemJson.Document({
        type: problemType,
        title: title,
        status: status,
        detail: fetchError.message
    }, extension);
};

module.exports = {
    celebrateProblems,
    makeBadGatewayProblem,
    makeUnauthorizedProblem,
    sendProblem,
    wrapFetchError
};
