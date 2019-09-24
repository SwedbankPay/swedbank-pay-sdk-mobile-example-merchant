package com.payex.samples.merchant.database;

import java.util.HashMap;
import java.util.Map;

import com.payex.samples.merchant.common.MerchantData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Our naive in-memory "database".
 */
public class Database {
    private static final Logger log = LoggerFactory.getLogger(Database.class);

    /** Holds our shared instance. */
    public static final Database sharedInstance = new Database();

    /** Holds our ID sequcence. */
    private long idSequence = System.currentTimeMillis();

    /** Hoolds our data. */
    private Map<String, MerchantData> db = new HashMap<String, MerchantData>();

    /**
     * Inserts a PaymentOrder into the database.
     */
    public String insertPurchase(MerchantData merchantData) {
        final String purchaseId = Long.toString(idSequence++);

        db.put(purchaseId, merchantData);

        return purchaseId;
    }

    /**
    * Inserts a mapping between our purchase id and PSP purchase id. This 
    * mapping can be used to fetch / refresh the purchase data from the 
    * PSP API.
    * 
    * @param purchaseId our purchase id
    * @param pspPurchaseId PSP purchase id
    */
    public void insertPurchaseIdMapping(String purchaseId, String pspPurchaseId) {
        final MerchantData merchantData = db.get(purchaseId);

        if (merchantData == null) {
            throw new IllegalArgumentException("No such purchase");
        }

        merchantData.setPspPurchaseId(pspPurchaseId);
        db.put(purchaseId, merchantData);
    }

    /**
     * Returns a purchase order by its purchase Id.
     * 
     * @param purchaseId {number} purchase ID by which to find the purchase order
     * @returns {object} the purchase order, or undefined if not found
     */
    public MerchantData findPurchase(String purchaseId) {
        return db.get(purchaseId);
    }
}
