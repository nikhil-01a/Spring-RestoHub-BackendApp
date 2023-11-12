package com.ssw.restohub.service;

import com.ssw.restohub.data.Order;
import com.ssw.restohub.enums.OrderStatus;
import com.ssw.restohub.pojo.FinalOrderInfo;
import com.ssw.restohub.pojo.OrderRequest;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    Order createOrder(OrderRequest orderRequest);
    FinalOrderInfo createFinalOrderInfo(Long orderId);
    Order updateOrderStatus(Long orderId, OrderStatus orderStatus);

    String deleteOrder(Long orderId);

    Order getOrder(Long orderId);
}
