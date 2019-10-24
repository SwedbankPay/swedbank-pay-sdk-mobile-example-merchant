package com.swedbank.samples.merchant.common;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class PaymentOrderUrls {
    @JsonProperty("hostUrls")
    private List<String> hostUrls;

    @JsonProperty("completeUrl")
    private String completeUrl;

    @JsonProperty("cancelUrl")
    private String cancelUrl;

    @JsonProperty("termsOfServiceUrl")
    private String termsOfServiceUrl;

    /**
     * @return the hostUrls
     */
    public List<String> getHostUrls() {
        return hostUrls;
    }

    /**
     * @param hostUrls the hostUrls to set
     */
    public void setHostUrls(List<String> hostUrls) {
        this.hostUrls = hostUrls;
    }

    /**
     * @return the completeUrl
     */
    public String getCompleteUrl() {
        return completeUrl;
    }

    /**
     * @param completeUrl the completeUrl to set
     */
    public void setCompleteUrl(String completeUrl) {
        this.completeUrl = completeUrl;
    }

    /**
     * @return the cancelUrl
     */
    public String getCancelUrl() {
        return cancelUrl;
    }

    /**
     * @param cancelUrl the cancelUrl to set
     */
    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

    /**
     * @return the termsOfServiceUrl
     */
    public String getTermsOfServiceUrl() {
        return termsOfServiceUrl;
    }

    /**
     * @param termsOfServiceUrl the termsOfServiceUrl to set
     */
    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }
}