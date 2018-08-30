package com.softmill.orderexpert.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

	public SecurityConfig() {
	}

	@Bean
	public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {

		ServerHttpSecurity httpSecurity = http.authorizeExchange().pathMatchers("/v2/api-docs/**")
				.permitAll().and()
				.authorizeExchange().pathMatchers("/swagger-ui.html/**")
				.permitAll().and().authorizeExchange()
				.pathMatchers("/swagger-resources/**")
				.permitAll().and().authorizeExchange().pathMatchers("/webjars/**")
				.permitAll().anyExchange().authenticated().and();

		return httpSecurity.build();
	}

}