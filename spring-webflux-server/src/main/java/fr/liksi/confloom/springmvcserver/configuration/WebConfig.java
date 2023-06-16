package fr.liksi.confloom.springmvcserver.configuration;

import fr.liksi.confloom.springmvcserver.service.GreatApiClient;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.server.HttpServer;

import java.time.Duration;

@Configuration
public class WebConfig {


    @Bean
    public EventLoopGroup eventLoopGroup() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);
        eventLoopGroup.register(new NioServerSocketChannel());
        return eventLoopGroup;
    }

    @Bean
    public NettyReactiveWebServerFactory nettyReactiveWebServerFactory() {
        NettyReactiveWebServerFactory webServerFactory = new NettyReactiveWebServerFactory();
        webServerFactory.addServerCustomizers(new EventLoopNettyCustomizer(eventLoopGroup()));
        return webServerFactory;
    }

    @Bean
    GreatApiClient postClient(@Value("${ext.great-api.baseUrl}") String baseUrl) {
        final var httpClient = HttpClient.create().runOn(eventLoopGroup());

        final var webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(baseUrl)
                .build();

        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.builder(WebClientAdapter
                                .forClient(webClient))
                                .blockTimeout(Duration.ofSeconds(100))
                        .build();
        return httpServiceProxyFactory.createClient(GreatApiClient.class);
    }

    private record EventLoopNettyCustomizer(EventLoopGroup eventExecutors) implements NettyServerCustomizer {

        @Override
        public HttpServer apply(HttpServer httpServer) {
            return httpServer.runOn(eventExecutors);
        }
    }
}
