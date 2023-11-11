package com.ssw.restohub.repositories;

import com.ssw.restohub.data.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
