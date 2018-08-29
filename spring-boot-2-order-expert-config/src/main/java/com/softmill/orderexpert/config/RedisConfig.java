package com.softmill.orderexpert.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
public class RedisConfig {

    public static final String ACCEPTED_EVENT_CHANNEL = "accepted_event_channel";

	@Value("${spring.redis.host}")
	private String redisHost;

	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(redisHost, 6379);
	}

    @Bean
    public ReactiveRedisTemplate<String, String> reactiveRedisTemplate(ReactiveRedisConnectionFactory connectionFactory) {
        return new ReactiveRedisTemplate<>(connectionFactory, RedisSerializationContext.string());
    }

}
