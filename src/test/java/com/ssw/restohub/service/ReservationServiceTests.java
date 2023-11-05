package com.ssw.restohub.service;

import com.ssw.restohub.data.Reservation;
import com.ssw.restohub.data.Restaurant;
import com.ssw.restohub.repositories.ReservationRepository;
import com.ssw.restohub.repositories.RestaurantRepository;
import com.ssw.restohub.service.impl.ReservationServiceImpl;
import com.ssw.restohub.service.impl.RestaurantServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.given;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTests {
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;
    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    private Reservation reservation;
    private Restaurant restaurant;

    @BeforeEach
    public void setup() {
        restaurant = Restaurant.builder()
                .id(1L)
                .name("Testaurant")
                .streetAddress1("1 Nowhere Road")
                .city("Somewhere")
                .state("XY")
                .zipCode("99999")
                .capacity(24)
                .createTime(new Date())
                .build();

        reservation = Reservation.builder()
                .id(1L)
                .restaurant(restaurant)
                .reservationDate(new Date())
                .createTime(new Date())
                .partySize(4)
                .emailAddress("person@food.com")
                .firstName("Person")
                .lastName("Food")
                .build();
    }

    @Test
    public void whenReservationIsCreated_thenRestaurantIsNotNull() {
        assertThat(reservation.getRestaurant()).isNotNull();
    }

    @Test
    @Disabled("Parsing date issue")
    public void whenReservationSearchIsCalledWithCorrectParams_thenReservationListReturned() throws Exception {
        Reservation reservation2 = Reservation.builder()
                .id(2L)
                .restaurant(restaurant)
                .reservationDate(new Date())
                .createTime(new Date())
                .partySize(40)
                .emailAddress("someperson@email.biz")
                .firstName("Duck")
                .lastName("Kitten")
                .build();
        Long restaurantId = restaurant.getId();
        String startDate = "2023-11-01";
        String endDate = "2023-11-10";

        given(restaurantRepository.findById(restaurantId)).willReturn(java.util.Optional.ofNullable(restaurant));
        given(reservationRepository.getReservationsForRestaurantAndTimeFrame(
                restaurant.getId(),
                startDate,
                endDate)).willReturn(List.of(reservation, reservation2));

        List<Reservation> reservationList = reservationService.getReservationForRestaurantAndTimeFrame(restaurantId, startDate, endDate);
        assertThat(reservationList).isNotNull();
        assertThat(reservationList.size()).isEqualTo(2);
    }

    @Test
    public void whenReservationSearchIsCalledwithIncorrectRestuarant_thenReservationListReturned() throws Exception {
        Reservation reservation2 = Reservation.builder()
                .id(2L)
                .restaurant(restaurant)
                .reservationDate(new Date())
                .createTime(new Date())
                .partySize(40)
                .emailAddress("someperson@email.biz")
                .firstName("Duck")
                .lastName("Kitten")
                .build();
        Long restaurantId = null;
        String startDate = "";
        String endDate = "";

        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            reservationService.getReservationForRestaurantAndTimeFrame(restaurantId, startDate, endDate);
        }, "Cannot fetch Reservations for invalid Restaurant");

        assertThat("Cannot fetch Reservations for invalid Restaurant").isEqualTo(thrown.getMessage());
    }
}
