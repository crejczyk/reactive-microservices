package com.softmill.orderexpert.model.dto.request;

import java.util.UUID;

import com.softmill.orderexpert.model.enums.ExpertType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpertRegisterEventDTO {
    private String expertId = UUID.randomUUID().toString();

    private ExpertType expertType;
}
