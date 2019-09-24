package com.payex.samples.merchant.controllers;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    private final static class Response {
        @JsonProperty("consumers")
        private String consumers;

        @JsonProperty("paymentorders")
        private String paymentorders;

        /**
         * @param consumers the consumers to set
         */
        public void setConsumers(String consumers) {
            this.consumers = consumers;
        }

        /**
         * @param paymentorders the paymentorders to set
         */
        public void setPaymentorders(String paymentorders) {
            this.paymentorders = paymentorders;
        }
    }

    @GetMapping("/")
    public Response hello(HttpServletResponse res) {
        res.addHeader("Cache-Control", "max-age=31536000");

        final Response response = new Response();
        response.setConsumers("/consumers");
        response.setPaymentorders("/paymentorders");

        return response;
    }
}
