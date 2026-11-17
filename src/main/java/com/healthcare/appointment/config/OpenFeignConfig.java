
package com.healthcare.appointment.config;

import feign.Logger;
import feign.RequestInterceptor;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class OpenFeignConfig {

    // Forward incoming JWT token to downstream services
    @Bean
    public RequestInterceptor bearerTokenRelayInterceptor() {

        return requestTemplate -> {

            RequestAttributes attributes =
                    RequestContextHolder.getRequestAttributes();

            if (attributes instanceof ServletRequestAttributes servletAttributes) {

                HttpServletRequest request =
                        servletAttributes.getRequest();

                String authorizationHeader =
                        request.getHeader(HttpHeaders.AUTHORIZATION);

                if (authorizationHeader != null
                        && !authorizationHeader.isBlank()) {

                    requestTemplate.header(
                            HttpHeaders.AUTHORIZATION,
                            authorizationHeader
                    );
                }
            }
        };
    }

    // Configure Feign logging
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }
}
