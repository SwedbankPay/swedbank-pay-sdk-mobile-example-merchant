package com.swedbank.samples.merchant;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.HttpEntities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @RequestMapping(path = "/paymentorders", method = RequestMethod.POST, produces = "application/json")
    public String paymentOrder(HttpServletResponse servletResponse, HttpServletRequest servletRequest) throws IOException, ParseException {
        String postBody = "{\n" +
                "    \"paymentorder\": {\n" +
                "        \"operation\": \"Purchase\",\n" +
                "        \"currency\": \"SEK\",\n" +
                "        \"amount\": 1000,\n" +
                "        \"vatAmount\": 250,\n" +
                "        \"description\": \"Test Purchase\",\n" +
                "        \"userAgent\": \"Mozilla/5.0...\",\n" +
                "        \"language\": \"sv-SE\",\n" +
                "        \"generateRecurrenceToken\": false,\n" +
                "        \"restrictedToInstruments\": [\"CreditCard\", \"Invoice\"],\n" +
                "        \"urls\": {\n" +
                "            \"hostUrls\": [ \"https://example.com\", \"https://example.net\" ],\n" +
                "            \"completeUrl\": \"https://example.com/payment-completed\",\n" +
                "            \"cancelUrl\": \"https://example.com/payment-canceled\",\n" +
                "            \"paymentUrl\": \"https://example.com/perform-payment\",\n" +
                "            \"callbackUrl\": \"https://api.example.com/payment-callback\",\n" +
                "            \"termsOfServiceUrl\": \"https://example.com/termsandconditoons.pdf\"\n" +
                "        },\n" +
                "        \"payeeInfo\": {\n" +
                "            \"payeeId\": \"" + merchantId + "\",\n" +
                "            \"payeeReference\": \"AB" + Integer.toString((int) (Math.random() * ((999 - 100) + 1)) + 100) + "\",\n" +
                "            \"payeeName\": \"Merchant1\",\n" +
                "            \"productCategory\": \"A123\",\n" +
                "            \"orderReference\": \"or-123456\",\n" +
                "            \"subsite\": \"MySubsite\"\n" +
                "        },\n" +
                "        \"payer\": {\n" +
                "            \"consumerProfileRef\": \"\",\n" +
                "            \"email\": \"olivia.nyhuus@payex.com\",\n" +
                "            \"msisdn\": \"+4798765432\",\n" +
                "            \"workPhoneNumber\" : \"+4787654321\",\n" +
                "            \"homePhoneNumber\" : \"+4776543210\"\n" +
                "        },\n" +
                "        \"orderItems\": [\n" +
                "            {\n" +
                "                \"reference\": \"P1\",\n" +
                "                \"name\": \"Product1\",\n" +
                "                \"type\": \"PRODUCT\",\n" +
                "                \"class\": \"ProductGroup1\",\n" +
                "                \"itemUrl\": \"https://example.com/products/123\",\n" +
                "                \"imageUrl\": \"https://example.com/product123.jpg\",\n" +
                "                \"description\": \"Product 1 description\",\n" +
                "                \"discountDescription\": \"Volume discount\",\n" +
                "                \"quantity\": 4,\n" +
                "                \"quantityUnit\": \"pcs\",\n" +
                "                \"unitPrice\": 300,\n" +
                "                \"discountPrice\": 200,\n" +
                "                \"vatPercent\": 2500,\n" +
                "                \"amount\": 1000,\n" +
                "                \"vatAmount\": 250\n" +
                "            },\n" +
                "            {\n" +
                "                \"reference\": \"I1\",\n" +
                "                \"name\": \"InvoiceFee\",\n" +
                "                \"type\": \"PAYMENT_FEE\",\n" +
                "                \"class\": \"Fees\",\n" +
                "                \"description\": \"Fee for paying with Invoice\",\n" +
                "                \"quantity\": 1,\n" +
                "                \"quantityUnit\": \"pcs\",\n" +
                "                \"unitPrice\": 1900,\n" +
                "                \"vatPercent\": 0,\n" +
                "                \"amount\": 1900,\n" +
                "                \"vatAmount\": 0,\n" +
                "                \"restrictedToInstruments\": [\n" +
                "                    \"Invoice-PayExFinancingSe\", \"Invoice-CampaignInvoiceSe\"\n" +
                "                ]\n" +
                "            }\n" +
                "        ],\n" +
                "        \"riskIndicator\": {\n" +
                "            \"deliveryEmailAddress\": \"olivia.nyhuus@payex.com\",\n" +
                "            \"deliveryTimeFrameIndicator\": \"01\",\n" +
                "            \"preOrderDate\": \"19801231\",\n" +
                "            \"preOrderPurchaseIndicator\": \"01\",\n" +
                "            \"shipIndicator\": \"01\",\n" +
                "            \"giftCardPurchase\": false,\n" +
                "            \"reOrderPurchaseIndicator\": \"01\",\n" +
                "            \"pickUpAddress\": {\n" +
                "                \"name\": \"Olivia Nyhus\",\n" +
                "                \"streetAddress\": \"Saltnestoppen 43\",\n" +
                "                \"coAddress\": \"\",\n" +
                "                \"city\": \"Saltnes\",\n" +
                "                \"zipCode\": \"1642\",\n" +
                "                \"countryCode\": \"NO\"\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(payexBaseUrl + "/psp/paymentorders");
        httpPost.addHeader("Authorization", "Bearer " + merchantToken);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.setEntity(HttpEntities.create(postBody));
        CloseableHttpResponse payexResponse = httpClient.execute(httpPost);
        HttpEntity entity = payexResponse.getEntity();
        return EntityUtils.toString(entity);
    }

}
