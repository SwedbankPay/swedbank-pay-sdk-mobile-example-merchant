package com.swedbank.samples.merchant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SslWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Value("${base-url}")
    private String baseUrl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (baseUrl.startsWith("https://")) {
            http.requiresChannel().regexMatchers("^((?!/healthcheck).)*$").requiresSecure();
        }
        http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
    }
}
