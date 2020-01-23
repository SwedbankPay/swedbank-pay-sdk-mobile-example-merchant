'use strict';

const { Joi } = require('celebrate');
const { post, sendError } = require('../util/networking.js');

module.exports.schema = {
    scheme: Joi.string().required(),
    token: Joi.string().required()
};

function buildCallbackHtml(scheme, token) {
    const callbackUrl = `${scheme}:///reload?token=${encodeURIComponent(token)}`;
    return `<html>
<head>
<title>Swedbank Pay Payment Completion</title>
<meta http-equiv="Refresh" content="0; url=${callbackUrl}" />
</head>
<body>
<a href="${callbackUrl}"><h1>Click here to continue</h1></a>
</body>
</html>`;
}

module.exports.route = (req, res) => {
    const scheme = req.query.scheme;
    const token = req.query.token;
    const html = buildCallbackHtml(scheme, token);
    res.status(200).send(html).end();
};
