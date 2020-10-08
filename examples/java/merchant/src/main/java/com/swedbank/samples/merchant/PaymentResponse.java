package com.swedbank.samples.merchant;

public class PaymentResponse {
    private Payment payment;
    
    public static class Payment {
        private String id;
        private Integer number;
        private String instrument;
        private String created;
        private String updated;
        private String state;
        private String operation;
        private String intent;
        private String currency;
        private int amount;
        private int remainingCaptureAmount;
        private int remainingCancellationAmount;
        private int remainingReversalAmount;
        private String description;
        private String payerReference;
        private String initiatingSystemUserAgent;
        private String userAgent;
        private String language;
        private IdContainer prices;
        private IdContainer transactions;
        private IdContainer authorizations;
        private IdContainer captures;
        private IdContainer reversals;
        private IdContainer cancellations;
        private IdContainer urls;
        private IdContainer payeeInfo;
        private IdContainer settings;
        public static class IdContainer {
            private String id;
            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

        public String getInstrument() {
            return instrument;
        }

        public void setInstrument(String instrument) {
            this.instrument = instrument;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
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

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getRemainingCaptureAmount() {
            return remainingCaptureAmount;
        }

        public void setRemainingCaptureAmount(int remainingCaptureAmount) {
            this.remainingCaptureAmount = remainingCaptureAmount;
        }

        public int getRemainingCancellationAmount() {
            return remainingCancellationAmount;
        }

        public void setRemainingCancellationAmount(int remainingCancellationAmount) {
            this.remainingCancellationAmount = remainingCancellationAmount;
        }

        public int getRemainingReversalAmount() {
            return remainingReversalAmount;
        }

        public void setRemainingReversalAmount(int remainingReversalAmount) {
            this.remainingReversalAmount = remainingReversalAmount;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPayerReference() {
            return payerReference;
        }

        public void setPayerReference(String payerReference) {
            this.payerReference = payerReference;
        }

        public String getInitiatingSystemUserAgent() {
            return initiatingSystemUserAgent;
        }

        public void setInitiatingSystemUserAgent(String initiatingSystemUserAgent) {
            this.initiatingSystemUserAgent = initiatingSystemUserAgent;
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

        public IdContainer getPrices() {
            return prices;
        }

        public void setPrices(IdContainer prices) {
            this.prices = prices;
        }

        public IdContainer getTransactions() {
            return transactions;
        }

        public void setTransactions(IdContainer transactions) {
            this.transactions = transactions;
        }

        public IdContainer getAuthorizations() {
            return authorizations;
        }

        public void setAuthorizations(IdContainer authorizations) {
            this.authorizations = authorizations;
        }

        public IdContainer getCaptures() {
            return captures;
        }

        public void setCaptures(IdContainer captures) {
            this.captures = captures;
        }

        public IdContainer getReversals() {
            return reversals;
        }

        public void setReversals(IdContainer reversals) {
            this.reversals = reversals;
        }

        public IdContainer getCancellations() {
            return cancellations;
        }

        public void setCancellations(IdContainer cancellations) {
            this.cancellations = cancellations;
        }

        public IdContainer getUrls() {
            return urls;
        }

        public void setUrls(IdContainer urls) {
            this.urls = urls;
        }

        public IdContainer getPayeeInfo() {
            return payeeInfo;
        }

        public void setPayeeInfo(IdContainer payeeInfo) {
            this.payeeInfo = payeeInfo;
        }

        public IdContainer getSettings() {
            return settings;
        }

        public void setSettings(IdContainer settings) {
            this.settings = settings;
        }
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
