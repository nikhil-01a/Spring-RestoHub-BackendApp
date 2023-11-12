package com.ssw.restohub.repositories;

import com.ssw.restohub.data.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {

    // USING JPQL SAME THINGS:
    // @Query("SELECT menuItem FROM MenuItem menuItem WHERE menuItem.restaurant.id = :restaurantId")
    // List<MenuItem> findAllByRestaurantId(@Param("restaurantId") Long restaurantId);

    List<MenuItem> findAllByRestaurantId(Long restaurantId);

    Optional<MenuItem> findMenuItemById(Long menuItemId);

    Optional<MenuItem> findMenuItemsByNameAndRestaurantId(String menuItemName,Long restaurantId);
}
