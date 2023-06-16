package fr.liksi.confloom.springmvcserver.configuration;

import fr.liksi.confloom.springmvcserver.service.GreatApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
public class WebConfig {

    @Bean
    GreatApiClient postClient(@Value("${ext.great-api.baseUrl}") String baseUrl) {
        final var webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.builder(WebClientAdapter
                                .forClient(webClient))
                                .blockTimeout(Duration.ofSeconds(100))
                        .build();
        return httpServiceProxyFactory.createClient(GreatApiClient.class);
    }
}
