package com.softmill.orderexpert.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import com.softmill.orderexpert.config.RedisConfig;
import com.softmill.orderexpert.service.listener.ExpertBookingAcceptedEventMessageListener;

@SpringBootApplication
@Import({RedisConfig.class})
public class ExpertServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpertServiceApplication.class, args);
	}

	@Bean
	public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, ExpertBookingAcceptedEventMessageListener expertBookingAcceptedEventMessageListener) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(expertBookingAcceptedEventMessageListener, new PatternTopic(RedisConfig.ACCEPTED_EVENT_CHANNEL));
		return container;
	}

}
