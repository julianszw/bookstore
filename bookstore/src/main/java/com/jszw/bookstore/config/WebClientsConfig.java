package com.jszw.bookstore.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientsConfig {

    @Bean
    @ConfigurationProperties(prefix = "external.isbndb")
    public ApiProps isbndbProps() { return new ApiProps(); }

    @Bean
    @Qualifier("isbndbWebClient")
    public WebClient isbndbWebClient(ApiProps isbndbProps) {
        return WebClient.builder()
                .baseUrl(isbndbProps.getBaseUrl())
                .defaultHeader("x-api-key", isbndbProps.getApiKey())
                .build();
    }

    @Bean
    @ConfigurationProperties(prefix = "external.google")
    public ApiProps googleProps() { return new ApiProps(); }

    @Bean
    @Qualifier("googleWebClient")
    public WebClient googleWebClient(ApiProps googleProps) {
        return WebClient.builder()
                .baseUrl(googleProps.getBaseUrl())
                .build();
    }

    // -----

    public static class ApiProps {
        private String baseUrl;
        private String apiKey;

        public String getBaseUrl() { return baseUrl; }
        public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }

        public String getApiKey() { return apiKey; }
        public void setApiKey(String apiKey) { this.apiKey = apiKey; }
    }
}
