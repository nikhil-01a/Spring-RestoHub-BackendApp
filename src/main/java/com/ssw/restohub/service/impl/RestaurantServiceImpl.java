package com.ssw.restohub.service.impl;

import com.ssw.restohub.data.Restaurant;
import com.ssw.restohub.repositories.RestaurantRepository;
import com.ssw.restohub.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
    public Optional<Restaurant> getRestaurantById(Long id) {
        return restaurantRepository.findById(id);
    }

    @Override
    public List<Restaurant> getRestaurantsByZipCode(String zipCode) {
        return restaurantRepository.findByZipCode(zipCode);
    }

    @Override
    public Optional<Restaurant> getRestaurantByManager(String email){
        return restaurantRepository.findRestaurantByManagerEmail(email);
    }
}
