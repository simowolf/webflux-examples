package com.demo.training.gatewayreservationservice.repository;

import com.demo.training.gatewayreservationservice.entity.Reservation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReservationRepository extends ReactiveCrudRepository<Reservation, Integer> {
}
