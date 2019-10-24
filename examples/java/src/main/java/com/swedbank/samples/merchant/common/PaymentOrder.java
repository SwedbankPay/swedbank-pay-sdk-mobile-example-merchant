package com.swedbank.samples.merchant.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class PaymentOrder {
    @JsonProperty("id")
    private String id;

    @JsonProperty("operation")
    private String operation;

    @JsonProperty("state")
    private String state;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("amount")
    private int amount;

    @JsonProperty("vatAmount")
    private int vatAmount;

    @JsonProperty("description")
    private String description;

    @JsonProperty("userAgent")
    private String userAgent;

    @JsonProperty("language")
    private String language;

    @JsonProperty("generateRecurrenceToken")
    private boolean generateRecurrenceToken;

    @JsonProperty("disablePaymentMenu")
    private boolean disablePaymentMenu;

    @JsonProperty("urls")
    private PaymentOrderUrls urls;

    @JsonProperty("payeeInfo")
    private PaymentOrderPayeeInfo payeeInfo;

    @JsonProperty("payer")
    private PaymentOrderPayer payer;

    /**
     * @return the operation
     */
    public String getOperation() {
        return operation;
    }

    /**
     * @param operation the operation to set
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return the vatAmount
     */
    public int getVatAmount() {
        return vatAmount;
    }

    /**
     * @param vatAmount the vatAmount to set
     */
    public void setVatAmount(int vatAmount) {
        this.vatAmount = vatAmount;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the userAgent
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * @param userAgent the userAgent to set
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the generateRecurrenceToken
     */
    public boolean isGenerateRecurrenceToken() {
        return generateRecurrenceToken;
    }

    /**
     * @param generateRecurrenceToken the generateRecurrenceToken to set
     */
    public void setGenerateRecurrenceToken(boolean generateRecurrenceToken) {
        this.generateRecurrenceToken = generateRecurrenceToken;
    }

    /**
     * @return the disablePaymentMenu
     */
    public boolean isDisablePaymentMenu() {
        return disablePaymentMenu;
    }

    /**
     * @param disablePaymentMenu the disablePaymentMenu to set
     */
    public void setDisablePaymentMenu(boolean disablePaymentMenu) {
        this.disablePaymentMenu = disablePaymentMenu;
    }

    /**
     * @return the urls
     */
    public PaymentOrderUrls getUrls() {
        return urls;
    }

    /**
     * @param urls the urls to set
     */
    public void setUrls(PaymentOrderUrls urls) {
        this.urls = urls;
    }

    /**
     * @return the payeeInfo
     */
    public PaymentOrderPayeeInfo getPayeeInfo() {
        return payeeInfo;
    }

    /**
     * @param payeeInfo the payeeInfo to set
     */
    public void setPayeeInfo(PaymentOrderPayeeInfo payeeInfo) {
        this.payeeInfo = payeeInfo;
    }

    /**
     * @return the payer
     */
    public PaymentOrderPayer getPayer() {
        return payer;
    }

    /**
     * @param payer the payer to set
     */
    public void setPayer(PaymentOrderPayer payer) {
        this.payer = payer;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
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
}