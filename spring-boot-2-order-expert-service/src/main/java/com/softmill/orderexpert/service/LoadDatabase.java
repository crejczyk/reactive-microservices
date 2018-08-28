package com.softmill.orderexpert.service;

import java.util.UUID;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import com.softmill.orderexpert.model.enums.ExpertStatus;
import com.softmill.orderexpert.model.enums.ExpertType;
import com.softmill.orderexpert.model.util.LocationGenerator;
import com.softmill.orderexpert.service.model.Expert;
import com.softmill.orderexpert.service.repo.ExpertRepository;
import com.softmill.orderexpert.service.service.ExpertService;

@Configuration
public class LoadDatabase {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LoadDatabase.class);

	private final ExpertRepository expertRepository;
	private final ExpertService expertService;

	public LoadDatabase(ExpertRepository expertRepository, ExpertService expertService) {
		this.expertRepository = expertRepository;
		this.expertService  = expertService;
	}

	@EventListener(value = ContextRefreshedEvent.class)
	void init() {
		initExpert();
	}

	private void initExpert() {
		log.info("Start data initialization ...");
		
		expertRepository.deleteAll();
		expertRepository.save(new Expert(UUID.randomUUID().toString(), ExpertType.ELECTRICIAN, ExpertStatus.AVAILABLE));
		expertRepository.save(new Expert(UUID.randomUUID().toString(), ExpertType.PAINTER, ExpertStatus.AVAILABLE));
		expertRepository.save(new Expert(UUID.randomUUID().toString(), ExpertType.PLUMBER, ExpertStatus.AVAILABLE));
		
		Iterable<Expert> experts = expertRepository.findAll();
		experts.forEach(t -> {
			expertService.updateLocation(t.getExpertId(), LocationGenerator.getLocation(79.865072, 6.927610, 3000)).subscribe();
		});
	}
}