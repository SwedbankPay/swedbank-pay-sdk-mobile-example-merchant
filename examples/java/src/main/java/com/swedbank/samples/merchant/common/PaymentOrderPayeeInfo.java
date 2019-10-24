package com.swedbank.samples.merchant.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class PaymentOrderPayeeInfo {
    @JsonProperty("payeeId")
    private String payeeId;

    @JsonProperty("payeeReference")
    private String payeeReference;

    /**
     * @return the payeeId
     */
    public String getPayeeId() {
        return payeeId;
    }

    /**
     * @param payeeId the payeeId to set
     */
    public void setPayeeId(String payeeId) {
        this.payeeId = payeeId;
    }

    /**
     * @return the payeeReference
     */
    public String getPayeeReference() {
        return payeeReference;
    }

    /**
     * @param payeeReference the payeeReference to set
     */
    public void setPayeeReference(String payeeReference) {
        this.payeeReference = payeeReference;
    }
}