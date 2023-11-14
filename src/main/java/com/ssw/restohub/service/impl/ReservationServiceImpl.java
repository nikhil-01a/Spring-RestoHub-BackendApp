package com.ssw.restohub.service.impl;

import com.ssw.restohub.data.Reservation;
import com.ssw.restohub.data.Restaurant;
import com.ssw.restohub.pojo.ReservationBean;
import com.ssw.restohub.projection.UnavailableReservationTime;
import com.ssw.restohub.repositories.ReservationRepository;
import com.ssw.restohub.repositories.RestaurantRepository;
import com.ssw.restohub.service.EmailService;
import com.ssw.restohub.service.ReservationService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
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
    EmailService emailService;

    @Autowired
    ReservationServiceImpl(ReservationRepository reservationRepository, RestaurantRepository restaurantRepository, EmailService emailService) {
        this.reservationRepository = reservationRepository;
        this.restaurantRepository = restaurantRepository;
        this.emailService = emailService;
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
        String randomCode = RandomStringUtils.randomAlphanumeric(6).toUpperCase();
        Optional<Reservation> reservation1 = reservationRepository.findByReservationCode(randomCode);
        while (reservation1.isPresent()){
            randomCode = RandomStringUtils.randomAlphanumeric(6).toUpperCase();
            reservation1 = reservationRepository.findByReservationCode(randomCode);
        }
        reservation.setReservationCode(randomCode);
        reservationRepository.save(reservation);
        emailService.sendEmail(reservationBean.getEmailAddress(),"Your reservation is confirmed","Here's your reservation code: "+randomCode);
        return reservation;
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

    @Override
    public List<Reservation> getReservationForRestaurantAndTimeFrame(Long restaurantId, String startDate, String endDate) throws Exception {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
        if (restaurantOptional.isEmpty()) {
            throw new Exception("Cannot fetch Reservations for invalid Restaurant");
        }
        Restaurant restaurant = restaurantOptional.get();

        if (StringUtils.isEmpty(startDate)) {
            throw new Exception(String.format(
                    "Please provide a valid Start Date to fetch Reservations for Restaurant: %s", restaurant.getName()));
        }

        if (StringUtils.isEmpty(endDate)) {
            throw new Exception(String.format(
                    "Please provide a valid End Date to fetch Reservations for Restaurant: %s", restaurant.getName()));
        }

        DateFormat frontEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat backend = new SimpleDateFormat("yyyy-MM-dd");

        startDate = backend.format(frontEnd.parse(startDate));
        endDate = backend.format(frontEnd.parse(endDate));

        return reservationRepository.getReservationsForRestaurantAndTimeFrame(restaurant.getId(), startDate, endDate);
    }

    @Override
    public Reservation customerReservationCheckIn(Long reservationId) throws Exception {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if (reservationOptional.isEmpty()) {
            throw new Exception("Cannot fetch Reservation for invalid Reservation ID");
        }

        Reservation reservation = reservationOptional.get();
        reservation.setCheckedIn(true);
        return reservationRepository.save(reservation);
    }
}
