package com.payex.samples.merchant.controllers;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.payex.samples.merchant.common.Operation;
import com.payex.samples.merchant.networking.RestClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

final class NationalIdentifier {
    @JsonProperty("socialSecurityNumber")
    private String socialSecurityNumber;

    @JsonProperty("countryCode")
    private String countryCode;

    /**
     * @return the socialSecurityNumber
     */
    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    /**
     * @param socialSecurityNumber the socialSecurityNumber to set
     */
    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    /**
     * @return the countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @param countryCode the countryCode to set
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /*
     * Returns the object description.
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "NationalIdentifier [countryCode=" + countryCode + ", socialSecurityNumber=" + socialSecurityNumber
                + "]";
    }
}

final class CustomerBody {
    @JsonProperty("operation")
    private String operation;

    @JsonProperty("consumerCountryCode")
    private String consumerCountryCode;

    @JsonProperty("email")
    private String email;

    @JsonProperty("msisdn")
    private String msisdn;

    @JsonProperty("nationalIdentifier")
    private NationalIdentifier nationalIdentifier;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    /**
     * @return the consumerCountryCode
     */
    public String getConsumerCountryCode() {
        return consumerCountryCode;
    }

    /**
     * @param consumerCountryCode the consumerCountryCode to set
     */
    public void setConsumerCountryCode(String consumerCountryCode) {
        this.consumerCountryCode = consumerCountryCode;
    }

    /**
     * @return the nationalIdentifier
     */
    public NationalIdentifier getNationalIdentifier() {
        return nationalIdentifier;
    }

    /**
     * @param nationalIdentifier the nationalIdentifier to set
     */
    public void setNationalIdentifier(NationalIdentifier nationalIdentifier) {
        this.nationalIdentifier = nationalIdentifier;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString() {
        return "CustomerBody [consumerCountryCode=" + consumerCountryCode + ", email=" + email + ", msisdn=" + msisdn
                + ", nationalIdentifier=" + nationalIdentifier + ", operation=" + operation + "]";
    }

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
}

final class PspResponse {
    @JsonProperty("operations")
    private List<Operation> operations;

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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString() {
        return "PspResponse []";
    }
}

@RestController
@RequestMapping("/")
public class ConsumersController {
    private static final Logger log = LoggerFactory.getLogger(ConsumersController.class);

    @Value("${payexBaseUrl}")
    private String payexBaseUrl;

    @Value("${merchantToken}")
    private String merchantToken;

    @PostMapping("/consumers")
    public PaymentOrdersPspResponse consumers(@RequestBody CustomerBody requestBody) {
        requestBody.setOperation("initiate-consumer-session");

        RestClient restClient = new RestClient(payexBaseUrl, merchantToken);

        String responseJson = restClient.post("/psp/consumers", requestBody);
        PaymentOrdersPspResponse pspResponse = new Gson().fromJson(responseJson, PaymentOrdersPspResponse.class);

        return pspResponse;
    }
}
