package com.swedbank.samples.merchant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

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
                "}";
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
        WebClient client = WebClient
                .builder()
                    .baseUrl(payexBaseUrl)
                    .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + merchantToken)
                .build();
        PaymentOrderResponse payexResponse = client.post()
                .uri("/psp/paymentorders")
                .body(BodyInserters.fromObject(paymentOrderRequest))
                .retrieve()
                .bodyToMono(PaymentOrderResponse.class) //PaymentOrderResponse.class)
                .block();
        return payexResponse;
    }
}
