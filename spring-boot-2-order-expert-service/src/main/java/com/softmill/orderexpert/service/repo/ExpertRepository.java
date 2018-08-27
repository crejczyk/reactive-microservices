package com.softmill.orderexpert.service.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.softmill.orderexpert.service.model.Expert;

@Repository
public interface ExpertRepository extends CrudRepository<Expert, String> {

}
