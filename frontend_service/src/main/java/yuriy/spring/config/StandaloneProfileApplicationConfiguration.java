package yuriy.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestClient;
import yuriy.spring.client.AuthenticationClient;
import yuriy.spring.client.TaskClient;
import yuriy.spring.client.impl.AuthenticationClientImpl;
import yuriy.spring.client.impl.TaskClientImpl;

@Configuration
@Profile("standalone")
public class StandaloneProfileApplicationConfiguration {

    @Bean
    public AuthenticationClient authenticationClient(
            @Value("${service.config.base_auth_url}") String baseUrl
    ) {
        return new AuthenticationClientImpl(RestClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    @Bean
    public TaskClient taskClient(
            @Value("${service.config.base_task_url}") String baseUrl
    ) {
        return new TaskClientImpl(RestClient.builder()
                .baseUrl(baseUrl)
                .build());
    }
}
