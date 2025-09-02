package com.reliaquest.api.config;

import com.reliaquest.api.constant.APIConstants;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(WebClientConfig.class);

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(APIConstants.BASE_URL)
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Accept", "application/json")
                .filter((request, next) -> {
                    logger.debug("WebClient Request: {} {}", request.method(), request.url());
                    return next.exchange(request);
                })
                .build();
    }
}
