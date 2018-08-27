package com.softmill.orderexpert.service.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import com.softmill.orderexpert.model.enums.ExpertStatus;
import com.softmill.orderexpert.model.enums.ExpertType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RedisHash("Expert")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expert implements Serializable {
    @Id
    private String expertId;

    private ExpertType expertType;

    private ExpertStatus expertStatus;
}
