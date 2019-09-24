'use strict';

module.exports.route = (req, res) => {
    const payload = {
        consumers: '/consumers',
        paymentorders: '/paymentorders'
    };

    res.status(200).set('Cache-Control', 'max-age=31536000').send(payload).end();
};
