package org.launchcode.BingeBuddy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APIConfiguration {
    @Value("${external.api.key}")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}
