package com.payex.samples.merchant.controllers;

import com.google.gson.Gson;
import com.payex.samples.merchant.common.MerchantData;
import com.payex.samples.merchant.common.PaymentOrderResponse;
import com.payex.samples.merchant.database.Database;
import com.payex.samples.merchant.exceptions.NotFoundException;
import com.payex.samples.merchant.networking.RestClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PaymentOrderController {
    private static final Logger log = LoggerFactory.getLogger(PaymentOrderController.class);

    @Value("${payexBaseUrl}")
    private String payexBaseUrl;

    @Value("${merchantToken}")
    private String merchantToken;

    /**
     * Retrtieves a single payment order.
     */
    @GetMapping("/paymentorder/{id}")
    public PaymentOrderResponse paymentOrder(@PathVariable("id") String id) {
        log.debug("Getting payment order for id {}", id);

        final MerchantData merchantData = Database.sharedInstance.findPurchase(id);
        if ((merchantData == null) || (merchantData.getPspPurchaseId() == null)) {
            log.error("Could not find proper merchant data: {}", merchantData);
            throw new NotFoundException();
        }

        final RestClient restClient = new RestClient(payexBaseUrl, merchantToken);

        final String responseJson = restClient.get(merchantData.getPspPurchaseId());
        final PaymentOrdersPspResponse pspResponse = new Gson().fromJson(responseJson, PaymentOrdersPspResponse.class);

        final PaymentOrderResponse response = new PaymentOrderResponse();
        response.setUrl(pspResponse.getPaymentOrder().getId());
        response.setState(pspResponse.getPaymentOrder().getState());
        response.setOperations(pspResponse.getOperations());

        return response;
    }
}
