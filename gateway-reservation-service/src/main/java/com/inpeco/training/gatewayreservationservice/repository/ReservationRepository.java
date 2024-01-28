package com.inpeco.training.gatewayreservationservice.repository;

import com.inpeco.training.gatewayreservationservice.entity.Reservation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReservationRepository extends ReactiveCrudRepository<Reservation, Integer> {
}
