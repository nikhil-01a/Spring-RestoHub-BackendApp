package com.ssw.restohub.pojo;

import com.ssw.restohub.data.MenuItem;
import com.ssw.restohub.data.OrderItem;
import com.ssw.restohub.data.Restaurant;
import com.ssw.restohub.enums.OrderStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private String reservationCode;
    private OrderStatus orderStatus;
    private String instructions;
    private Long restaurantId;
    private List<OrderItem> orderItems;
}
