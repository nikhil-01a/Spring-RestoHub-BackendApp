package com.ssw.restohub.repositories;

import com.ssw.restohub.data.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
