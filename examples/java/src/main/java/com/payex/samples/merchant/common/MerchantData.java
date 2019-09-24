
package com.payex.samples.merchant.common;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class MerchantData {
    @NotEmpty(message = "basketId is a mandatory parameter")
    @JsonProperty("basketId")
    private String basketId;

    @NotEmpty(message = "currency is a mandatory parameter")
    @JsonProperty("currency")
    private String currency;

    @NotEmpty(message = "languageCode is a mandatory parameter")
    @JsonProperty("languageCode")
    private String languageCode;

    @NotEmpty(message = "items is a mandatory parameter")
    @JsonProperty("items")
    private List<MerchantDataItem> items;

    @JsonIgnore
    private String pspPurchaseId;

    /**
     * @return the basketId
     */
    public String getBasketId() {
        return basketId;
    }

    /**
     * @param basketId the basketId to set
     */
    public void setBasketId(String basketId) {
        this.basketId = basketId;
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
     * @return the languageCode
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * @param languageCode the languageCode to set
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * @return the items
     */
    public List<MerchantDataItem> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<MerchantDataItem> items) {
        this.items = items;
    }

    public static class MerchantDataItem {
        @JsonProperty("itemId")
        private String itemId;

        @JsonProperty("quantity")
        private String quantity;

        @JsonProperty("price")
        private int price;

        @JsonProperty("vat")
        private int vat;

        /**
         * @return the itemId
         */
        public String getItemId() {
            return itemId;
        }

        /**
         * @param itemId the itemId to set
         */
        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        /**
         * @return the quantity
         */
        public String getQuantity() {
            return quantity;
        }

        /**
         * @param quantity the quantity to set
         */
        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        /**
         * @return the price
         */
        public int getPrice() {
            return price;
        }

        /**
         * @param price the price to set
         */
        public void setPrice(int price) {
            this.price = price;
        }

        /**
         * @return the vat
         */
        public int getVat() {
            return vat;
        }

        /**
         * @param vat the vat to set
         */
        public void setVat(int vat) {
            this.vat = vat;
        }
    }

    /**
     * @return the pspPurchaseId
     */
    public String getPspPurchaseId() {
        return pspPurchaseId;
    }

    /**
     * @param pspPurchaseId the pspPurchaseId to set
     */
    public void setPspPurchaseId(String pspPurchaseId) {
        this.pspPurchaseId = pspPurchaseId;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString() {
        return "MerchantData [basketId=" + basketId + ", currency=" + currency + ", items=" + items + ", languageCode="
                + languageCode + ", pspPurchaseId=" + pspPurchaseId + "]";
    }
}