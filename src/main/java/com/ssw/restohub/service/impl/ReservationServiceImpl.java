package com.ssw.restohub.service.impl;

import com.ssw.restohub.data.Reservation;
import com.ssw.restohub.repositories.ReservationRepository;
import com.ssw.restohub.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservationServiceImpl implements ReservationService {
    ReservationRepository reservationRepository;

    @Autowired
    ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservation> getUnavailableReservations(Long restaurantId, Integer partySize) {
        return reservationRepository.findByRestaurantId(restaurantId);
    }
}
