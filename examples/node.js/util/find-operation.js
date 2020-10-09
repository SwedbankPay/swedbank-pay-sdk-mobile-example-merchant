'use strict';

module.exports = function (response, rel) {
    const operations = response ? response.operations : null;
    if (Array.isArray(operations)) {
        return operations.find((op) => {
            return op.rel == rel;
        });
    } else {
        return null;
    }
};
