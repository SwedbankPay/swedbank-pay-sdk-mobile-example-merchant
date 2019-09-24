package com.payex.samples.merchant.common;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class PaymentOrderResponse {
    @JsonProperty("url")
    private String url;

    @JsonProperty("state")
    private String state;

    @JsonProperty("failureReason")
    private String failureReason;

    @JsonProperty("operations")
    private List<Operation> operations;

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the failureReason
     */
    public String getFailureReason() {
        return failureReason;
    }

    /**
     * @param failureReason the failureReason to set
     */
    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    /**
     * @return the operations
     */
    public List<Operation> getOperations() {
        return operations;
    }

    /**
     * @param operations the operations to set
     */
    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}