package com.swedbank.samples.merchant;

import java.util.List;

public class PaymentRequest {
    
    public Payment payment;

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public static class Payment {
        private String operation;
        private String intent;
        private String currency;
        private List<Price> prices;
        private String descriptions;
        private String userAgent;
        private String language;
        private Urls urls;
        private PayeeInfo payeeInfo;
        private Cardholder cardholder;
        private RiskIndicator riskIndicator;
                
        public static class Price {
            private String type;
            private int amount;
            private int vatAmount;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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
        }
        
        public static class Urls {
            private List<String> hostUrls;
            private String completeUrl;
            private String cancelUrl;
            private String paymentUrl;
            private String callbackUrl;
            private String logoUrl;
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

            public String getLogoUrl() {
                return logoUrl;
            }

            public void setLogoUrl(String logoUrl) {
                this.logoUrl = logoUrl;
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

            public void setPayeeReference(String payeeReference) {
                this.payeeReference = payeeReference;
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

        public static class Cardholder {
            private String firstName;
            private String lastName;
            private String email;
            private String msisdn;
            private String homePhoneNumber;
            private String workPhoneNumber;
            private ShippingAddress shippingAddress;

            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }

            public String getLastName() {
                return lastName;
            }

            public void setLastName(String lastName) {
                this.lastName = lastName;
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

            public String getHomePhoneNumber() {
                return homePhoneNumber;
            }

            public void setHomePhoneNumber(String homePhoneNumber) {
                this.homePhoneNumber = homePhoneNumber;
            }

            public String getWorkPhoneNumber() {
                return workPhoneNumber;
            }

            public void setWorkPhoneNumber(String workPhoneNumber) {
                this.workPhoneNumber = workPhoneNumber;
            }

            public ShippingAddress getShippingAddress() {
                return shippingAddress;
            }

            public void setShippingAddress(ShippingAddress shippingAddress) {
                this.shippingAddress = shippingAddress;
            }

            public static class ShippingAddress {
                private String firstName;
                private String lastName;
                private String email;
                private String msisdn;
                private String streetAddress;
                private String coAddress;
                private String city;
                private String zipCode;
                private String countryCode;

                public String getFirstName() {
                    return firstName;
                }

                public void setFirstName(String firstName) {
                    this.firstName = firstName;
                }

                public String getLastName() {
                    return lastName;
                }

                public void setLastName(String lastName) {
                    this.lastName = lastName;
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
        }
        
        public static class RiskIndicator {
            private String deliveryEmailAddress;
            private String deliveryTimeFrameIndicator;
            private String preOrderDate;
            private String preOrderPurchaseIndicator;
            private String shipIndicator;
            private boolean giftCardPurchase;
            private String reOrderPurchaseIndicator;
            private PickUpAddress pickUpAddress;

            public String getDeliveryEmailAddress() {
                return deliveryEmailAddress;
            }

            public void setDeliveryEmailAddress(String deliveryEmailAddress) {
                this.deliveryEmailAddress = deliveryEmailAddress;
            }

            public String getDeliveryTimeFrameIndicator() {
                return deliveryTimeFrameIndicator;
            }

            public void setDeliveryTimeFrameIndicator(String deliveryTimeFrameIndicator) {
                this.deliveryTimeFrameIndicator = deliveryTimeFrameIndicator;
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
        }

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public String getIntent() {
            return intent;
        }

        public void setIntent(String intent) {
            this.intent = intent;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public List<Price> getPrices() {
            return prices;
        }

        public void setPrices(List<Price> prices) {
            this.prices = prices;
        }

        public String getDescriptions() {
            return descriptions;
        }

        public void setDescriptions(String descriptions) {
            this.descriptions = descriptions;
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

        public Cardholder getCardholder() {
            return cardholder;
        }

        public void setCardholder(Cardholder cardholder) {
            this.cardholder = cardholder;
        }

        public RiskIndicator getRiskIndicator() {
            return riskIndicator;
        }

        public void setRiskIndicator(RiskIndicator riskIndicator) {
            this.riskIndicator = riskIndicator;
        }
    }
}
