package com.softmill.orderexpert.model.dto.response;

import com.softmill.orderexpert.model.enums.ExpertStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpertStatusDTO {
    private String expertId;

    private ExpertStatus status;
}
