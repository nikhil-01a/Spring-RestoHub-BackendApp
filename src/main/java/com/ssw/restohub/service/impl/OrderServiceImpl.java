package com.ssw.restohub.service.impl;

import com.ssw.restohub.data.MenuItem;
import com.ssw.restohub.data.Order;
import com.ssw.restohub.data.OrderItem;
import com.ssw.restohub.data.Restaurant;
import com.ssw.restohub.enums.OrderStatus;
import com.ssw.restohub.pojo.FinalOrderInfo;
import com.ssw.restohub.pojo.OrderRequest;
import com.ssw.restohub.repositories.OrderItemRepository;
import com.ssw.restohub.repositories.OrderRepository;
import com.ssw.restohub.service.MenuItemService;
import com.ssw.restohub.service.OrderService;
import com.ssw.restohub.service.RestaurantService;
import jakarta.servlet.http.PushBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderServiceImpl implements OrderService {
    private MenuItemService menuItemService;
    private RestaurantService restaurantService;

    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;

    @Autowired
    public OrderServiceImpl(MenuItemService menuItemService,RestaurantService restaurantService,OrderRepository orderRepository, OrderItemRepository orderItemRepository){
        this.menuItemService = menuItemService;
        this.restaurantService = restaurantService;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }
    @Override
    public Order createOrder(OrderRequest orderRequest) {
        Order newOrder = new Order();
        Restaurant attachedRestaurant = restaurantService.getRestaurantById(orderRequest.getRestaurantId()).orElseThrow(() -> new NoSuchElementException("Restaurant not found!"));
        newOrder.setReservationCode(orderRequest.getReservationCode());
        newOrder.setOrderStatus(orderRequest.getOrderStatus());
        newOrder.setInstructions(orderRequest.getInstructions());
        newOrder.setRestaurant(attachedRestaurant);
        orderRepository.save(newOrder);
        newOrder.setOrderItems(createOrderItems(orderRequest, newOrder));
        orderItemRepository.saveAll(newOrder.getOrderItems());
        newOrder.setTotalOrderAmount(calcTotalOrderAmount(newOrder.getOrderItems()));
        orderRepository.save(newOrder);
        return newOrder;
    }

    @Override
    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    private List<OrderItem> createOrderItems(OrderRequest orderRequest, Order newOrder){
        List<OrderItem> orderItemList = new ArrayList<>();
        orderRequest.getOrderItems().forEach(orderItem -> {
            double perItemPrice = menuItemService.getMenuItemPrice(orderItem.getMenuItem().getId());
            orderItem.setPricePerItem(perItemPrice);
            orderItem.setSubTotal(perItemPrice*(orderItem.getQuantity()));
            orderItem.setOrder(newOrder);
            orderItemList.add(orderItem);
        });
        return orderItemList;
    }

    private double calcTotalOrderAmount(List<OrderItem> orderItemList){
        double finalAmount = 0 ;
        for (OrderItem orderItem:orderItemList){
            finalAmount = orderItem.getSubTotal()+ finalAmount;
        }
        return finalAmount;
    }

    @Override
    public FinalOrderInfo createFinalOrderInfo(String reservationCode){
        return new FinalOrderInfo(orderRepository.findByReservationCode(reservationCode));

    }

    @Override
    public Order getOrder(String reservationCode){
        return orderRepository.findByReservationCode(reservationCode);
    }

    @Override
    public Order updateOrderStatus(String reservationCode, OrderStatus orderStatus){
        Order order = orderRepository.findByReservationCode(reservationCode);
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        return order;
    }

    @Override
    public String deleteOrder(String reservationCode){
        Order orderToDeleted = orderRepository.findByReservationCode(reservationCode);
        if(orderToDeleted.getOrderStatus().equals(OrderStatus.COMPLETED)){
            orderRepository.delete(orderToDeleted);
            return orderToDeleted.getOrderId().toString();
        }else {
            throw new IllegalArgumentException("Order status not 'COMPLETED' yet!");
        }
    }

}
