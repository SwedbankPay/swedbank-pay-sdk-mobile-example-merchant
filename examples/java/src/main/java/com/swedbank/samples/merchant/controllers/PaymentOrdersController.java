package com.swedbank.samples.merchant.controllers;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.swedbank.samples.merchant.common.MerchantData;
import com.swedbank.samples.merchant.common.Operation;
import com.swedbank.samples.merchant.common.PaymentOrder;
import com.swedbank.samples.merchant.common.PaymentOrderPayeeInfo;
import com.swedbank.samples.merchant.common.PaymentOrderPayer;
import com.swedbank.samples.merchant.common.PaymentOrderUrls;
import com.swedbank.samples.merchant.database.Database;
import com.swedbank.samples.merchant.common.PaymentOrderResponse;
import com.swedbank.samples.merchant.networking.RestClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

final class PaymentOrderBody {
    @JsonProperty("customerProfileRef")
    private String customerProfileRef;

    @JsonProperty("merchantData")
    private MerchantData merchantData;

    /**
     * @return the customerProfileRef
     */
    public String getCustomerProfileRef() {
        return customerProfileRef;
    }

    /**
     * @param customerProfileRef the customerProfileRef to set
     */
    public void setCustomerProfileRef(String customerProfileRef) {
        this.customerProfileRef = customerProfileRef;
    }

    /**
     * @return the merchantData
     */
    public MerchantData getMerchantData() {
        return merchantData;
    }

    /**
     * @param merchantData the merchantData to set
     */
    public void setMerchantData(MerchantData merchantData) {
        this.merchantData = merchantData;
    }
}

final class PaymentOrdersPspRequest {
    @JsonProperty("paymentOrder")
    private PaymentOrder paymentOrder;

    /**
     * @return the paymentOrder
     */
    public PaymentOrder getPaymentOrder() {
        return paymentOrder;
    }

    /**
     * @param paymentOrder the paymentOrder to set
     */
    public void setPaymentOrder(PaymentOrder paymentOrder) {
        this.paymentOrder = paymentOrder;
    }
}

final class PaymentOrdersPspResponse {
    @JsonProperty("paymentOrder")
    private PaymentOrder paymentOrder;

    @JsonProperty("operations")
    private List<Operation> operations;

    /**
     * @return the paymentOrder
     */
    public PaymentOrder getPaymentOrder() {
        return paymentOrder;
    }

    /**
     * @param paymentOrder the paymentOrder to set
     */
    public void setPaymentOrder(PaymentOrder paymentOrder) {
        this.paymentOrder = paymentOrder;
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

@RestController
@RequestMapping("/")
public class PaymentOrdersController {
    private static final Logger log = LoggerFactory.getLogger(PaymentOrdersController.class);

    @Value("${payexBaseUrl}")
    private String payexBaseUrl;

    @Value("${merchantToken}")
    private String merchantToken;

    @Value("${currency}")
    private String currency;

    @Value("${merchantId}")
    private String merchantId;

    private PaymentOrder createPaymentOrder(PaymentOrderBody requestBody, HttpServletRequest req) {
        final MerchantData merchantData = requestBody.getMerchantData();

        final String purchaseId = Database.sharedInstance.insertPurchase(merchantData);

        // Calculate the total sum and total vat from the item list
        final int totalPrice = merchantData.getItems().stream().reduce(0,
                (subtotal, element) -> subtotal + element.getPrice(), Integer::sum);
        final int totalVat = merchantData.getItems().stream().reduce(0,
                (subtotal, element) -> subtotal + element.getVat(), Integer::sum);

        final String baseUrl = String.format("https://%s", req.getHeader("host"));

        final PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setOperation("Purchase");
        paymentOrder.setCurrency(merchantData.getCurrency());
        paymentOrder.setAmount(totalPrice);
        paymentOrder.setVatAmount(totalVat);
        paymentOrder.setDescription(merchantData.getBasketId());
        paymentOrder.setUserAgent(req.getHeader("User-Agent"));
        paymentOrder.setLanguage(merchantData.getLanguageCode());
        paymentOrder.setGenerateRecurrenceToken(false);
        paymentOrder.setDisablePaymentMenu(false);

        final PaymentOrderUrls urls = new PaymentOrderUrls();
        urls.setHostUrls(Arrays.asList(new String[] { baseUrl }));
        urls.setCompleteUrl(String.format("%s/complete", baseUrl));
        urls.setCancelUrl(String.format("%s/cancel", baseUrl));
        urls.setTermsOfServiceUrl(String.format("%s/tos", baseUrl));
        paymentOrder.setUrls(urls);

        final PaymentOrderPayeeInfo payeeInfo = new PaymentOrderPayeeInfo();
        payeeInfo.setPayeeId(merchantId);
        payeeInfo.setPayeeReference(purchaseId);
        paymentOrder.setPayeeInfo(payeeInfo);

        if (requestBody.getCustomerProfileRef() != null) {
            final PaymentOrderPayer payer = new PaymentOrderPayer();
            payer.setConsumerProfileRef(requestBody.getCustomerProfileRef());
            paymentOrder.setPayer(payer);
        }

        return paymentOrder;
    }

    /**
     * Calls the PSP endpoint to create a new Payment Order.
     * 
     * The following format is used for our 'merchant data':
     * 
     * {
     *   "basketId": "123",
     *   "currency": "SEK",
     *   "languageCode": "sv-SE",
     *   "items": [
     *     {
     *      "itemId": "1",
     *       "quantity": 1,
     *       "price": 1200,
     *       "vat": 300
     *     },
     *     {
     *       "itemId": "2",
     *       "quantity": 2,
     *      "price": 400,
     *      "vat": 75
     *     }
     *   ]
     * }
     *
     * @see https://developer.payex.com/xwiki/wiki/developer/view/Main/ecommerce/technical-reference/payment-orders-resource/#HCreatingapaymentorder
     * @param requestBody Object representing our request body
     */
    @PostMapping("/paymentorders")
    public PaymentOrderResponse paymentOrders(@RequestBody PaymentOrderBody requestBody, HttpServletRequest req) {
        final RestClient restClient = new RestClient(payexBaseUrl, merchantToken);

        final PaymentOrder paymentOrder = createPaymentOrder(requestBody, req);
        final PaymentOrdersPspRequest pspRequest = new PaymentOrdersPspRequest();
        pspRequest.setPaymentOrder(paymentOrder);

        final String responseJson = restClient.post("/psp/paymentorders", pspRequest);
        final PaymentOrdersPspResponse pspResponse = new Gson().fromJson(responseJson, PaymentOrdersPspResponse.class);

        Database.sharedInstance.insertPurchaseIdMapping(paymentOrder.getPayeeInfo().getPayeeReference(),
                pspResponse.getPaymentOrder().getId());

        final String url = String.format("/paymentorder/%s", paymentOrder.getPayeeInfo().getPayeeReference());
        final PaymentOrderResponse response = new PaymentOrderResponse();
        response.setUrl(url);
        response.setState(pspResponse.getPaymentOrder().getState());
        response.setOperations(pspResponse.getOperations());

        return response;
    }
}
