package com.softmill.orderexpert.booking.service.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.softmill.orderexpert.booking.service.model.ExpertBooking;

@Repository
public interface ExpertBookingRepository extends CrudRepository<ExpertBooking, String> {

}
