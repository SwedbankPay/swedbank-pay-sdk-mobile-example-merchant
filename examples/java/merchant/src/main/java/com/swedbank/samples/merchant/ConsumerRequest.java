package com.swedbank.samples.merchant;

import java.util.List;

public class ConsumerRequest {
    private String operation;
    private String language;
    private List<String> shippingAddressRestrictedToCountryCodes;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getShippingAddressRestrictedToCountryCodes() {
        return shippingAddressRestrictedToCountryCodes;
    }

    public void setShippingAddressRestrictedToCountryCodes(List<String> shippingAddressRestrictedToCountryCodes) {
        this.shippingAddressRestrictedToCountryCodes = shippingAddressRestrictedToCountryCodes;
    }
}
