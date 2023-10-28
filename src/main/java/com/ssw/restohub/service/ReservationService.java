package com.ssw.restohub.service;

import com.ssw.restohub.data.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReservationService {
    List<Reservation> getUnavailableReservations(Long restaurantId, Integer partySize);
}
