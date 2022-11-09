'use strict';

module.exports = class Database {
    constructor() {
        
        // Contains our "database" as an in-memory map ID => purchaseData
        this.db = { pspId: {} };
    }
    

    /**
     * Inserts a new purchase into the database.
     *
     * @param purchase {object} the purchase data
     * @returns {number} ID allocated for the new purchase
     */
    insertPurchase(purchase) {
        // Get an ID for the purchase by generating a random ID. 
        const id = Math.floor(Math.random() * Number.MAX_SAFE_INTEGER);

        this.db[id] = purchase;

        return id;
    }

    /**
     * Inserts a mapping between our purchase id and PSP purchase id.
     *
     * @param purchaseId {number} our purchase id
     * @param pspPurchaseId {string} PSP purchase id
     */
    insertPurchaseIdMapping(purchaseId, pspPurchaseId) {
        this.db[purchaseId].pspPurchaseId = pspPurchaseId;
        this.db.pspId[pspPurchaseId] = purchaseId;
    }

    /**
     * Returns a purchase order by its purchase Id.
     *
     * @param purchaseId {number} purchase ID by which to find the purchase order
     * @returns {object} the purchase order, or undefined if not found
     */
    findPurchase(purchaseId) {
        return this.db[purchaseId];
    }

    findPspPurchaseId(someId) {

        let purchase = this.db.pspId[someId];
        if (purchase) {
            return purchase;
        }
        return this.db[someId].pspPurchaseId;
    }
};
