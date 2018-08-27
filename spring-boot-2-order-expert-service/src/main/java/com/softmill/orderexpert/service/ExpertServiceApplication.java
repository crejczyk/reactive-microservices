package com.softmill.orderexpert.service;

import com.softmill.orderexpert.config.RedisConfig;
import com.softmill.orderexpert.model.enums.ExpertStatus;
import com.softmill.orderexpert.model.enums.ExpertType;
import com.softmill.orderexpert.model.util.LocationGenerator;
import com.softmill.orderexpert.service.listener.ExpertBookingAcceptedEventMessageListener;
import com.softmill.orderexpert.service.model.Expert;
import com.softmill.orderexpert.service.repo.ExpertRepository;
import com.softmill.orderexpert.service.service.ExpertService;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.UUID;

@SpringBootApplication
@Import({RedisConfig.class})
public class ExpertServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpertServiceApplication.class, args);
	}

	@Bean
	public ApplicationRunner applicationRunner(ExpertRepository expertRepository, ExpertService expertService) {
		return args -> {
			expertRepository.deleteAll();

			expertRepository.save(new Expert(UUID.randomUUID().toString(), ExpertType.ELECTRICIAN, ExpertStatus.AVAILABLE));

			Iterable<Expert> experts = expertRepository.findAll();

			experts.forEach(t -> {
				expertService.updateLocation(t.getExpertId(), LocationGenerator.getLocation(79.865072, 6.927610, 3000)).subscribe();
			});
		};
	}

	@Bean
	public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, ExpertBookingAcceptedEventMessageListener expertBookingAcceptedEventMessageListener) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(expertBookingAcceptedEventMessageListener, new PatternTopic(RedisConfig.ACCEPTED_EVENT_CHANNEL));
		return container;
	}

}
