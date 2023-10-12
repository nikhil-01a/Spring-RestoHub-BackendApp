package com.ssw.restohub.service;

import com.ssw.restohub.data.Restaurant;
import com.ssw.restohub.repositories.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.BDDMockito.given;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTests {
    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

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
    }

    @Test
    public void whenAllRestaurantsCalled_thenReturnRestaurantList_notNull() {
        Restaurant restaurant2 = Restaurant.builder()
                .id(2L)
                .name("Some Restaurant")
                .streetAddress1("2 Nowhere Road")
                .city("Nowhere")
                .state("YZ")
                .zipCode("33333")
                .capacity(20)
                .createTime(new Date())
                .build();

        given(restaurantRepository.findAll()).willReturn(List.of(restaurant, restaurant2));

        List<Restaurant> restaurantList = restaurantService.getAllRestaurants();
        assertThat(restaurantList).isNotNull();
        assertThat(restaurantList.size()).isEqualTo(2);
    }
}