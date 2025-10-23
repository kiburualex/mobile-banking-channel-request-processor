package io.firogence.mobile_banking_channel_request_processor.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * @author Alex Kiburu
 */
@Configuration
public class BeansConfiguration {

    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }
}
