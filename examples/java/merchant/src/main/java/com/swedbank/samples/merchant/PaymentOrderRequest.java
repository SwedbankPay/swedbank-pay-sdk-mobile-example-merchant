package com.swedbank.samples.merchant;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PaymentOrderRequest {

    public Paymentorder paymentorder;
    
    public static class Paymentorder {
        private String operation;
        private String currency;
        private int amount;
        private int vatAmount;
        private String description;
        private String userAgent;
        private String language;
        private boolean generateRecurrenceToken;
        private List<String> restrictedToInstruments;
        private Urls urls;
        private PayeeInfo payeeInfo;
        private Payer payer;
        private List<OrderItem> orderItems;
        private RiskIndicator riskIndicator;

        public static class Urls {
            private List<String> hostUrls;
            private String completeUrl;
            private String cancelUrl;
            private String paymentUrl;
            private String callbackUrl;
            private String termsOfServiceUrl;

            public List<String> getHostUrls() {
                return hostUrls;
            }

            public void setHostUrls(List<String> hostUrls) {
                this.hostUrls = hostUrls;
            }

            public String getCompleteUrl() {
                return completeUrl;
            }

            public void setCompleteUrl(String completeUrl) {
                this.completeUrl = completeUrl;
            }

            public String getCancelUrl() {
                return cancelUrl;
            }

            public void setCancelUrl(String cancelUrl) {
                this.cancelUrl = cancelUrl;
            }

            public String getPaymentUrl() {
                return paymentUrl;
            }

            public void setPaymentUrl(String paymentUrl) {
                this.paymentUrl = paymentUrl;
            }

            public String getCallbackUrl() {
                return callbackUrl;
            }

            public void setCallbackUrl(String callbackUrl) {
                this.callbackUrl = callbackUrl;
            }

            public String getTermsOfServiceUrl() {
                return termsOfServiceUrl;
            }

            public void setTermsOfServiceUrl(String termsOfServiceUrl) {
                this.termsOfServiceUrl = termsOfServiceUrl;
            }
        }

        public static class PayeeInfo {
            private String payeeId;
            private String payeeReference;
            private String payeeName;
            private String productCategory;
            private String orderReference;
            private String subsite;

            public String getPayeeId() {
                return payeeId;
            }

            public void setPayeeId(String payeeId) {
                this.payeeId = payeeId;
            }

            public String getPayeeReference() {
                return payeeReference;
            }

            public void setPayeeReference(String payeeReferece) {
                this.payeeReference = payeeReferece;
            }

            public String getPayeeName() {
                return payeeName;
            }

            public void setPayeeName(String payeeName) {
                this.payeeName = payeeName;
            }

            public String getProductCategory() {
                return productCategory;
            }

            public void setProductCategory(String productCategory) {
                this.productCategory = productCategory;
            }

            public String getOrderReference() {
                return orderReference;
            }

            public void setOrderReference(String orderReference) {
                this.orderReference = orderReference;
            }

            public String getSubsite() {
                return subsite;
            }

            public void setSubsite(String subsite) {
                this.subsite = subsite;
            }
        }

        public static class Payer {
            private String consumerProfileRef;
            private String email;
            private String msisdn;
            private String workPhoneNumber;
            private String homePhoneNumber;

            public String getConsumerProfileRef() {
                return consumerProfileRef;
            }

            public void setConsumerProfileRef(String consumerProfileRef) {
                this.consumerProfileRef = consumerProfileRef;
            }

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

            public String getWorkPhoneNumber() {
                return workPhoneNumber;
            }

            public void setWorkPhoneNumber(String workPhoneNumber) {
                this.workPhoneNumber = workPhoneNumber;
            }

            public String getHomePhoneNumber() {
                return homePhoneNumber;
            }

            public void setHomePhoneNumber(String homePhoneNumber) {
                this.homePhoneNumber = homePhoneNumber;
            }
        }

        public static class OrderItem {
            private String reference;
            private String name;
            private String type;
            private String clazz;
            private String itemUrl;
            private String imageUrl;
            private String description;
            private String discountDescription;
            private int quantity;
            private String quantityUnit;
            private int unitPrice;
            private int discountPrice;
            private int vatPercent;
            private int amount;
            private int vatAmount;
            private List<String> restrictedToInstruments;

            public String getReference() {
                return reference;
            }

            public void setReference(String reference) {
                this.reference = reference;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            @JsonProperty("class")
            public String getClazz() {
                return clazz;
            }

            @JsonProperty("class")
            public void setClazz(String clazz) {
                this.clazz = clazz;
            }

            public String getItemUrl() {
                return itemUrl;
            }

            public void setItemUrl(String itemUrl) {
                this.itemUrl = itemUrl;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getDiscountDescription() {
                return discountDescription;
            }

            public void setDiscountDescription(String discountDescription) {
                this.discountDescription = discountDescription;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public String getQuantityUnit() {
                return quantityUnit;
            }

            public void setQuantityUnit(String quantityUnit) {
                this.quantityUnit = quantityUnit;
            }

            public int getUnitPrice() {
                return unitPrice;
            }

            public void setUnitPrice(int unitPrice) {
                this.unitPrice = unitPrice;
            }

            public int getDiscountPrice() {
                return discountPrice;
            }

            public void setDiscountPrice(int discountPrice) {
                this.discountPrice = discountPrice;
            }

            public int getVatPercent() {
                return vatPercent;
            }

            public void setVatPercent(int vatPercent) {
                this.vatPercent = vatPercent;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public int getVatAmount() {
                return vatAmount;
            }

            public void setVatAmount(int vatAmount) {
                this.vatAmount = vatAmount;
            }

            public List<String> getRestrictedToInstruments() {
                return restrictedToInstruments;
            }

            public void setRestrictedToInstruments(List<String> restrictedToInstruments) {
                this.restrictedToInstruments = restrictedToInstruments;
            }
        }

        public static class RiskIndicator {
            private String deliveryEmailAddress;
            private String deliveryTimeframeIndicator;
            private String preOrderDate;
            private String preOrderPurchaseIndicator;
            private String shipIndicator;
            private boolean giftCardPurchase;
            private String reOrderPurchaseIndicator;
            private PickUpAddress pickUpAddress;

            public static class PickUpAddress {
                private String name;
                private String streetAddress;
                private String coAddress;
                private String city;
                private String zipCode;
                private String countryCode;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getStreetAddress() {
                    return streetAddress;
                }

                public void setStreetAddress(String streetAddress) {
                    this.streetAddress = streetAddress;
                }

                public String getCoAddress() {
                    return coAddress;
                }

                public void setCoAddress(String coAddress) {
                    this.coAddress = coAddress;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public String getZipCode() {
                    return zipCode;
                }

                public void setZipCode(String zipCode) {
                    this.zipCode = zipCode;
                }

                public String getCountryCode() {
                    return countryCode;
                }

                public void setCountryCode(String countryCode) {
                    this.countryCode = countryCode;
                }
            }

            public String getDeliveryEmailAddress() {
                return deliveryEmailAddress;
            }

            public void setDeliveryEmailAddress(String deliveryEmailAddress) {
                this.deliveryEmailAddress = deliveryEmailAddress;
            }

            public String getDeliveryTimeframeIndicator() {
                return deliveryTimeframeIndicator;
            }

            public void setDeliveryTimeframeIndicator(String deliveryTimeframeIndicator) {
                this.deliveryTimeframeIndicator = deliveryTimeframeIndicator;
            }

            public String getPreOrderDate() {
                return preOrderDate;
            }

            public void setPreOrderDate(String preOrderDate) {
                this.preOrderDate = preOrderDate;
            }

            public String getPreOrderPurchaseIndicator() {
                return preOrderPurchaseIndicator;
            }

            public void setPreOrderPurchaseIndicator(String preOrderPurchaseIndicator) {
                this.preOrderPurchaseIndicator = preOrderPurchaseIndicator;
            }

            public String getShipIndicator() {
                return shipIndicator;
            }

            public void setShipIndicator(String shipIndicator) {
                this.shipIndicator = shipIndicator;
            }

            public boolean isGiftCardPurchase() {
                return giftCardPurchase;
            }

            public void setGiftCardPurchase(boolean giftCardPurchase) {
                this.giftCardPurchase = giftCardPurchase;
            }

            public String getReOrderPurchaseIndicator() {
                return reOrderPurchaseIndicator;
            }

            public void setReOrderPurchaseIndicator(String reOrderPurchaseIndicator) {
                this.reOrderPurchaseIndicator = reOrderPurchaseIndicator;
            }

            public PickUpAddress getPickUpAddress() {
                return pickUpAddress;
            }

            public void setPickUpAddress(PickUpAddress pickUpAddress) {
                this.pickUpAddress = pickUpAddress;
            }
        }

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getVatAmount() {
            return vatAmount;
        }

        public void setVatAmount(int vatAmount) {
            this.vatAmount = vatAmount;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUserAgent() {
            return userAgent;
        }

        public void setUserAgent(String userAgent) {
            this.userAgent = userAgent;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public boolean isGenerateRecurrenceToken() {
            return generateRecurrenceToken;
        }

        public void setGenerateRecurrenceToken(boolean generateRecurrenceToken) {
            this.generateRecurrenceToken = generateRecurrenceToken;
        }

        public List<String> getRestrictedToInstruments() {
            return restrictedToInstruments;
        }

        public void setRestrictedToInstruments(List<String> restrictedToInstruments) {
            this.restrictedToInstruments = restrictedToInstruments;
        }

        public Urls getUrls() {
            return urls;
        }

        public void setUrls(Urls urls) {
            this.urls = urls;
        }

        public PayeeInfo getPayeeInfo() {
            return payeeInfo;
        }

        public void setPayeeInfo(PayeeInfo payeeInfo) {
            this.payeeInfo = payeeInfo;
        }

        public Payer getPayer() {
            return payer;
        }

        public void setPayer(Payer payer) {
            this.payer = payer;
        }

        public List<OrderItem> getOrderItems() {
            return orderItems;
        }

        public void setOrderItems(List<OrderItem> orderItems) {
            this.orderItems = orderItems;
        }

        public RiskIndicator getRiskIndicator() {
            return riskIndicator;
        }

        public void setRiskIndicator(RiskIndicator riskIndicator) {
            this.riskIndicator = riskIndicator;
        }
    }

    public Paymentorder getPaymentorder() {
        return paymentorder;
    }

    public void setPaymentorder(Paymentorder paymentorder) {
        this.paymentorder = paymentorder;
    }
}
