package com.ssw.restohub.service;

import com.ssw.restohub.data.Restaurant;
import com.ssw.restohub.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantServiceImpl implements RestaurantService {

    private RestaurantRepository restaurantRepository;

    @Autowired
    RestaurantServiceImpl(RestaurantRepository restaurantRepository){
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> getRestaurantsByZipCode(String zipCode) {
        return restaurantRepository.findByZipCode(zipCode);
    }
}
