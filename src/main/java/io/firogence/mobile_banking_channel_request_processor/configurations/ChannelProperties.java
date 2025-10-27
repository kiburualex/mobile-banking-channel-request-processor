package io.firogence.mobile_banking_channel_request_processor.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Alex Kiburu
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "channel")
public class ChannelProperties {
    private Credentials credentials;
    private List<String> allowedIps;
    private Urls urls;

    @Data
    public static class Credentials {
        private String username;
        private String password;
    }

    @Data
    public static class Urls {
        private String baseUrl;
        private String depositUrl;
        private String transferUrl;
        private String withdrawUrl;
        private String balanceUrl;
        private String accountLookupUrl;
        private String miniStatementUrl;
        private String fullStatementUrl;
        private String mpesaCheckoutUrl;
        private String mpesaB2cUrl;
        private String mpesaB2bUrl;
    }
}
