package com.payex.samples.merchant.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class PaymentOrderPayer {
    @JsonProperty("consumerProfileRef")
    private String consumerProfileRef;

    /**
     * @return the consumerProfileRef
     */
    public String getConsumerProfileRef() {
        return consumerProfileRef;
    }

    /**
     * @param consumerProfileRef the consumerProfileRef to set
     */
    public void setConsumerProfileRef(String consumerProfileRef) {
        this.consumerProfileRef = consumerProfileRef;
    }
}
