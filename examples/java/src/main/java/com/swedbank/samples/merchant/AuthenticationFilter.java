package com.swedbank.samples.merchant;

import static com.swedbank.samples.merchant.Constants.ACCESS_TOKEN_HEADER;
import static com.swedbank.samples.merchant.Constants.API_KEY_HEADER;
import static com.swedbank.samples.merchant.Constants.USER_ID_ATTRIBUTE;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class AuthenticationFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Value("${apiKey}")
    private String apiKey;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getContextPath() + req.getServletPath();
        if (req.getPathInfo() != null) {
            path += req.getPathInfo();
        }

        log.info("Running authentication filter for {} {}", req.getMethod(), path);

        // Validate our API key
        String apikeyHeader = req.getHeader(API_KEY_HEADER);
        if (apikeyHeader == null) {
            log.error("Missing or invalid API key header");
            res.setStatus(HttpStatus.BAD_REQUEST.value());
            return;
        }
        if (!apikeyHeader.equals(apiKey)) {
            log.error("API key mismatch.");
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        // Get our user's access token and do a fake user lookup and
        // then attach the user ID into the request. For demo purposes,
        // however, we'll just use the 'access token' as the user ID.
        String accessTokenHeader = req.getHeader(ACCESS_TOKEN_HEADER);
        if (accessTokenHeader == null) {
            log.error("Missing access token header");
            res.setStatus(HttpStatus.BAD_REQUEST.value());
            return;
        }

        req.setAttribute(USER_ID_ATTRIBUTE, accessTokenHeader);

        chain.doFilter(request, response);
    }
}
