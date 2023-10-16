package com.ssw.restohub.repositories;

import com.ssw.restohub.data.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findAll();
    @Override Optional<Restaurant> findById(Long id);
    List<Restaurant> findByZipCode(String zipCode);
}
