package com.ssw.restohub.service.impl;

import com.ssw.restohub.data.Reservation;
import com.ssw.restohub.data.Restaurant;
import com.ssw.restohub.pojo.ReservationBean;
import com.ssw.restohub.projection.UnavailableReservationTime;
import com.ssw.restohub.repositories.ReservationRepository;
import com.ssw.restohub.repositories.RestaurantRepository;
import com.ssw.restohub.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class ReservationServiceImpl implements ReservationService {
    ReservationRepository reservationRepository;
    RestaurantRepository restaurantRepository;

    @Autowired
    ReservationServiceImpl(ReservationRepository reservationRepository, RestaurantRepository restaurantRepository) {
        this.reservationRepository = reservationRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Optional<Restaurant> getRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId);
    }

    @Override
    public Reservation saveReservation(ReservationBean reservationBean) throws Exception {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(reservationBean.getRestaurantId());
        if (restaurantOptional.isEmpty()) {
            throw new Exception("Cannot create Reservation for invalid Restaurant");
        }
        Restaurant restaurant = restaurantOptional.get();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Reservation reservation = new Reservation();
        reservation.setRestaurant(restaurant);
        reservation.setReservationDate(dateFormat.parse(reservationBean.getReservationDate()));
        reservation.setEmailAddress(reservationBean.getEmailAddress());
        reservation.setFirstName(reservationBean.getFirstName());
        reservation.setLastName(reservationBean.getLastName());
        reservation.setPartySize(reservationBean.getPartySize());
        return reservationRepository.save(reservation);
    }

    @Override
    public List<UnavailableReservationTime> getUnavailableReservations(Long restaurantId, Integer partySize)
            throws Exception {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
        if (restaurantOptional.isEmpty()) {
            throw new Exception("Cannot fetch unavailable reservations for invalid Restaurant");
        }
        return reservationRepository.getAllUnavailableTimes(restaurantId, partySize);
    }
}
