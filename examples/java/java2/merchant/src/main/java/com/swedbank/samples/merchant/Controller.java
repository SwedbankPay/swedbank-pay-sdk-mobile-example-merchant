package com.swedbank.samples.merchant;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityTemplate;
import org.apache.hc.core5.http.io.entity.HttpEntities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
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

    @RequestMapping(path = "/healthcheck", method = RequestMethod.GET)
    public String healthCheck() {
        return "Healthcheck OK\n";
    }

    @RequestMapping(path = "/", method = RequestMethod.GET, produces = "application/json")
    public String emptyJson(HttpServletResponse servletResponse) {
        return "{\n" +
                "  \"consumers\": \"/consumers\",\n" +
                "  \"paymentorders\": \"/paymentorders\"\n" +
                "}";
    }

    @RequestMapping(path = "/consumers", method = RequestMethod.POST, produces = "application/json")
    public HttpServletResponse initiateConsumerSession(HttpServletResponse servletResponse, HttpServletRequest servletRequest) throws IOException {
        System.out.println(servletRequest.toString());
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(payexBaseUrl + "/psp/consumers");
        httpPost.addHeader("Authorization", servletRequest.getHeader("Authorization"));
        httpPost.setEntity(HttpEntities.create(servletRequest.getInputStream().toString()));
        System.out.println(httpPost.toString());
        CloseableHttpResponse payexResponse = httpClient.execute(httpPost);
        System.out.println(payexResponse.toString());
        servletResponse.getOutputStream().write(payexResponse.getEntity().getContent().read());
        return servletResponse;
    }

}
