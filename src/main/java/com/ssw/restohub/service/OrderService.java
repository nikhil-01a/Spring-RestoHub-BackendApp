package com.ssw.restohub.service;

import com.ssw.restohub.data.Order;
import com.ssw.restohub.enums.OrderStatus;
import com.ssw.restohub.pojo.FinalOrderInfo;
import com.ssw.restohub.pojo.OrderRequest;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    Order createOrder(OrderRequest orderRequest);

    Order updateOrder(Order order);
    FinalOrderInfo createFinalOrderInfo(String reservationCode);
    Order updateOrderStatus(String reservationCode, OrderStatus orderStatus);

    String deleteOrder(String reservationCode);

    Order getOrder(String reservationCode);
}
