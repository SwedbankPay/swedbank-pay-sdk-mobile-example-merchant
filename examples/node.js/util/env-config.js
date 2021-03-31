'use strict';

function identity(value) {
    return value;
}

function parseStringArray(value) {
    return value
        .split(',')
        .map(s => s.trim());
}

const propertyParsers = {
    apiKey: identity,
    merchantToken: identity,
    merchantId: identity,
    payexBaseUrl: identity,
    iosAppIds: parseStringArray,
    allowUsePaymentToken: Boolean,
    allowGetPayerOwnedPaymentTokens: Boolean,
    allowDeletePayerOwnedPaymentToken: Boolean
};

module.exports = function(config) {
    const env = process.env;
    for (const name in propertyParsers) {
        const envName = `SWBPAY_${name}`;
        const envValue = env[envName];
        if (envValue != null) {
            const parser = propertyParsers[name];
            config[name] = parser(envValue);
        }
    }
}
