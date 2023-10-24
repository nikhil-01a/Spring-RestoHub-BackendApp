package com.ssw.restohub.service;

import com.ssw.restohub.data.Reservation;
import com.ssw.restohub.data.Restaurant;
import com.ssw.restohub.repositories.ReservationRepository;
import com.ssw.restohub.service.impl.ReservationServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTests {
    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private Reservation reservation;
    private Restaurant restaurant;

    public void setup() {
        reservation = Reservation.builder()
                .id(1L)
                .restaurant(restaurant)
                .reservationDate(new Date())
                .createTime(new Date())
                .partySize(4)
                .build();
    }

    @Test
    @Disabled("disabled until reservation service is complete")
    public void whenReservationIsCreated_thenRestaurantIsNotNull() {
        return;
    }
}
