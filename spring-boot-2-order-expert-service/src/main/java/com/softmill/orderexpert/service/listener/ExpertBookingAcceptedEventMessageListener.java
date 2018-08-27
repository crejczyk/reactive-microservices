package com.softmill.orderexpert.service.listener;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softmill.orderexpert.model.dto.request.ExpertBookingAcceptedEventDTO;
import com.softmill.orderexpert.model.enums.ExpertStatus;
import com.softmill.orderexpert.service.service.ExpertService;

@Component
public class ExpertBookingAcceptedEventMessageListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpertBookingAcceptedEventMessageListener.class);

    private final ExpertService expertService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ExpertBookingAcceptedEventMessageListener(ExpertService expertService) {
        this.expertService = expertService;
    }

    @Override
    public void onMessage(Message message, @Nullable byte[] bytes) {
        try {
            ExpertBookingAcceptedEventDTO expertBookingAcceptedEventDTO = objectMapper.readValue(new String(message.getBody()), ExpertBookingAcceptedEventDTO.class);
            LOGGER.info("Accepted Event {}", expertBookingAcceptedEventDTO);
            expertService.updateExpertStatus(expertBookingAcceptedEventDTO.getExpertId(), ExpertStatus.OCCUPIED);
        } catch (IOException e) {
            LOGGER.error("Error while updating expert status", e);
        }
    }
}
