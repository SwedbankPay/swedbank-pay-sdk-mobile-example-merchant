package com.swedbank.samples.merchant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestController
public class Controller {

    @Value("${payex-base-url}")
    private String payexBaseUrl;

    @Value("${merchant-id}")
    private String merchantId;

    @Value("${merchant-token}")
    private String merchantToken;

    @RequestMapping(path = "/healthcheck", method = RequestMethod.GET)
    public String healthCheck() {
        return "Healthcheck OK\n";
    }

    @RequestMapping(path = "/", method = RequestMethod.GET, produces = "application/json")
    public String hateoasListing() {
        return "{\n" +
                "  \"consumers\": \"/consumers\",\n" +
                "  \"paymentorders\": \"/paymentorders\"\n" +
                "}\n";
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<String> handleWebClientResponseException(WebClientResponseException ex) {
        System.out.println(ex.getResponseBodyAsString());
        return ResponseEntity.status(ex.getRawStatusCode()).body(ex.getResponseBodyAsString());
    }

    @RequestMapping(path = "/paymentorders", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public PaymentOrderResponse paymentOrder(@RequestBody PaymentOrderRequest paymentOrderRequest) {
        paymentOrderRequest
                .getPaymentorder()
                .getPayeeInfo()
                .setPayeeId(merchantId);
        paymentOrderRequest
                .getPaymentorder()
                .getPayeeInfo()
                .setPayeeReference("AB" + Integer.toString((int) (Math.random() * (1000))));
        WebClient client = WebClient
                .builder()
                .baseUrl(payexBaseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + merchantToken)
                .build();
        PaymentOrderResponse payexResponse = client.post()
                .uri("/psp/paymentorders")
                .body(BodyInserters.fromObject(paymentOrderRequest))
                .retrieve()
                .bodyToMono(PaymentOrderResponse.class)
                .block();
        return payexResponse;
    }

    @RequestMapping(path = "/payments", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public PaymentResponse payment(@RequestBody PaymentRequest paymentRequest) {
        paymentRequest
                .getPayment()
                .getPayeeInfo()
                .setPayeeReference("AB" + Integer.toString((int) (Math.random() * (1000))));
        WebClient client = WebClient
                .builder()
                .baseUrl(payexBaseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + merchantToken)
                .build();
        PaymentResponse payexResponse = client.post()
                .uri("/psp/creditcard/payments")
                .body(BodyInserters.fromObject(paymentRequest))
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .block();
        return payexResponse;
    }

    @RequestMapping(path = "/consumers", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ConsumerResponse consumer(@RequestBody ConsumerRequest consumerRequest) {
        WebClient client = WebClient
                .builder()
                .baseUrl(payexBaseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + merchantToken)
                .build();
        ConsumerResponse payexResponse = client.post()
                .uri("/psp/consumers")
                .body(BodyInserters.fromObject(consumerRequest))
                .retrieve()
                .bodyToMono(ConsumerResponse.class)
                .block();
        return payexResponse;
    }
}
