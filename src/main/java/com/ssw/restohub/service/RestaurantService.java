package com.ssw.restohub.service;

import com.ssw.restohub.data.Restaurant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestaurantService {
    List<Restaurant> getAllRestaurants();
    List<Restaurant> getRestaurantsByZipCode(String zipCode);
}
