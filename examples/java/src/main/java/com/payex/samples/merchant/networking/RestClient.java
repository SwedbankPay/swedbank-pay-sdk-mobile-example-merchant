package com.payex.samples.merchant.networking;

import static com.payex.samples.merchant.Constants.ENV_VAR_SERVER_BASEURL;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestClient {
    private static final Logger log = LoggerFactory.getLogger(RestClient.class);

    private String payexBaseUrl;
    private String merchantToken;

    enum HttpMethod {
        GET, POST
    }

    /**
     * HTTP client.
     */
    private OkHttpClient client = new OkHttpClient();

    /**
     * Returns the server address; either the default valuen from 
     * the configuration file or the value specified by environment variable 
     * PAYEX_SERVER_BASE_URL if defined.
     * 
     * @return server base url (eg. https://example.com)
     */
    private String getServerBaseUrl() {
        final String envValue = System.getenv(ENV_VAR_SERVER_BASEURL);
        if (envValue != null) {
            return envValue;
        } else {
            return payexBaseUrl;
        }
    }

    /**
     * Performs a HTTP request to the server and returns the response.
     * 
     * @param path     the resource path (eg. /consumers)
     * @param method   the HTTP method to use
     * @param bodyJson the body of the request as a JSON string
     * @return the server response JSON as a parsed Map
     */
    private String request(String path, HttpMethod method, String bodyJson) {
        String url = getServerBaseUrl() + path;

        String authHeader = "Bearer " + merchantToken;
        Request.Builder builder = new Request.Builder().url(url).addHeader("Accept", "application/json")
                .addHeader("Authorization", authHeader).addHeader("Accept-Encoding", "identity");

        if (method == HttpMethod.GET) {
            builder = builder.get();
        } else {
            try {
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                        bodyJson.getBytes("utf-8"));
                builder = builder.post(requestBody);
            } catch (UnsupportedEncodingException e) {
                // This would be quite highly unlikely
                log.error("utf-8 encoding failed!");
            }
        }

        Request request = builder.build();
        Call call = client.newCall(request);
        Response response = null;

        try {
            log.debug("Making a HTTP request: {} {}", method.toString(), url);
            response = call.execute();
        } catch (IOException e) {
            log.error("Caught exception while making HTTP request: {}", e);
            throw new RuntimeException("Error in http request");
        }

        if ((response.code() < 200) || (response.code() >= 300)) {
            log.error("HTTP request failed with code {}", response.code());
            throw new RuntimeException(String.format("Request failed with code: %d", response.code()));
        }

        try {
            return response.body().string();
        } catch (IOException e) {
            log.error("Caught exception while reading HTTP response: {}", e);
            throw new RuntimeException("Error in http request");
        }
    }

    /**
     * Performs a HTTP GET request to the server and returns the response.
     * 
     * @param path the resource path (eg. /consumers)
     * @return the server response JSON string
     */
    public String get(String path) {
        return request(path, HttpMethod.GET, null);
    }

    /**
     * Performs a HTTP POST request to the server and returns the response.
     * 
     * @param path the resource path (eg. /consumers)
     * @param body the body of the request as an Object. It will be encoded as JSON.
     * @return the server response JSON string
     */
    public String post(String path, Object body) {
        String gsonString = new Gson().toJson(body);

        return request(path, HttpMethod.POST, gsonString);
    }

    /**
     * Constructs a new REST client with configured parameters.
     * 
     * @param payexBaseUrl  server base URL (eg. https://service.domain/path)
     * @param merchantToken secret merchant token for authorizateion
     */
    public RestClient(String payexBaseUrl, String merchantToken) {
        this.payexBaseUrl = payexBaseUrl;
        this.merchantToken = merchantToken;
    }
}
