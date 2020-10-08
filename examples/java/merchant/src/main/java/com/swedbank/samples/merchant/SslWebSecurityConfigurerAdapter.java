package com.swedbank.samples.merchant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Configuration
@EnableWebSecurity
public class SslWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Value("${base-url}")
    private String baseUrl;

    @Value("x-payex-sample-apikey")
    private String principalRequestHeader;

    @Value("${api-key}")
    private String principalRequestValue;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/healthcheck");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ApiKeyAuthFilter filter = new ApiKeyAuthFilter(principalRequestHeader);

        filter.setAuthenticationManager(new AuthenticationManager() {

            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String principal = (String) authentication.getPrincipal();
                if (!principalRequestValue.equals(principal))
                {
                    throw new BadCredentialsException("The API key was not found or not the expected value.");
                }
                authentication.setAuthenticated(true);
                return authentication;
            }
        });

        if (baseUrl.startsWith("https://")) {
            http.requiresChannel().regexMatchers("^((?!/healthcheck).)*$").requiresSecure();
        }
        http.addFilter(filter).authorizeRequests().anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();
        }
}
