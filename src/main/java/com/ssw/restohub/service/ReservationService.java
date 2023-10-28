package com.ssw.restohub.service;

import com.ssw.restohub.data.Reservation;
import com.ssw.restohub.pojo.ReservationBean;
import com.ssw.restohub.projection.UnavailableReservationTime;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReservationService {
    List<UnavailableReservationTime> getUnavailableReservations(Long restaurantId, Integer partySize) throws Exception;
    Reservation saveReservation(ReservationBean reservationBean) throws Exception;
}
