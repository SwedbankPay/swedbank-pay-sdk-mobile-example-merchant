'use strict';

const { Joi } = require('celebrate');

module.exports.schema = Joi.object().keys({
    scheme: Joi.string().required(),
    language: Joi.string().required(),
    app: Joi.string(),
    fallback: Joi.boolean()
}).unknown(true);

function buildTrampolineUrl(query, url) {
    const endpoint = 'https://trampoline-endpoint-here.invalid/';
    // URLSeachParams does not encode queries in accordance
    // with RFC 3986, so we cannot use it here :(
    let trampolineUrl = `${endpoint}?target=${encodeURIComponent(url)}&language=${encodeURIComponent(query.language)}`;
    const app = query.app;
    if (app) trampolineUrl += `&app=${encodeURIComponent(app)}`
    return trampolineUrl;
}

module.exports.route = (req, res) => {
    const query = req.query;
    const fallback = query.fallback;
    // If we are invoked as fallback, we want to redirect
    // to the same url but witht the custom scheme.
    // Otherwise, we want to redirect to the trampoline.
    const scheme = fallback ? query.scheme : req.protocol;
    // Url is either the url to redirect to (if fallback),
    // or the one to use as the trampoline target.
    const url = new URL(req.originalUrl, `${scheme}://${req.headers.host}`);
    if (!fallback) {
        // i.e. trampoline target; it should be the fallback version

        // N.B! URLSeachParams is bad and encodes space as +
        // This is NOT compliant with RFC 3986 which we require.
        // Therefore we cannot use url.searchParams.append here :(
        // We always have a query here (per validation schema)
        // so we know that the & is needed.
        url.search += '&fallback=true';
    }
    const urlString = url.href;
    // If fallback, redirect immediately, otherwise through the trampoline.
    const location = fallback ? urlString : buildTrampolineUrl(query, urlString);
    res.status(301).set('Location', location).send().end();
};
