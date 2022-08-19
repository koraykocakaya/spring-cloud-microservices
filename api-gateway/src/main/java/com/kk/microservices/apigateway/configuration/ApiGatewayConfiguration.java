package com.kk.microservices.apigateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		
		return builder.routes()
					.route(p -> p.path("/currencyExchange/**").uri("lb://currency-exchange"))
					.route(p -> p.path("/currencyConversion-feign/**").uri("lb://currency-conversion"))
					.route(p -> p.path("/currencyConversion/**")
							.filters(f -> f.rewritePath("/currencyConversion/(?<segment>.*)", "/currencyConversion-feign/${segment}"))
							.uri("lb://currency-conversion"))
					.build();
		
	}
}
