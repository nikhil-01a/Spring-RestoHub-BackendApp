package com.ssw.restohub.pojo;

import com.ssw.restohub.data.Order;
import com.ssw.restohub.data.OrderItem;
import com.ssw.restohub.enums.OrderStatus;
import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FinalOrderInfo {

    private Long orderId;
    private String reservationCode;
    private Date orderDateTime;
    private OrderStatus orderStatus;
    private List<OrderItem> orderItems;
    private String instructions;
    private double totalOrderAmount;

    public FinalOrderInfo(Order order){
        this.orderId = order.getOrderId();
        this.reservationCode = order.getReservationCode();
        this.orderDateTime = order.getOrderDateTime();
        this.orderStatus = order.getOrderStatus();
        this.orderItems = order.getOrderItems();
        this.instructions = order.getInstructions();
        this.totalOrderAmount = order.getTotalOrderAmount();
    }

    public String getFormattedOrderDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(this.orderDateTime);
    }
}
