package com.ssw.restohub.controllers;

import com.ssw.restohub.data.MenuItem;
import com.ssw.restohub.data.Order;
import com.ssw.restohub.data.OrderItem;
import com.ssw.restohub.enums.OrderStatus;
import com.ssw.restohub.pojo.FinalOrderInfo;
import com.ssw.restohub.pojo.OrderRequest;
import com.ssw.restohub.service.MenuItemService;
import com.ssw.restohub.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class StaffViewController {

    private MenuItemService menuItemService;
    private OrderService orderService;

    @Autowired
    public StaffViewController(MenuItemService menuItemService, OrderService orderService){
        this.menuItemService = menuItemService;
        this.orderService = orderService;
    }


    @GetMapping(value = "/api/staff/menuItems")
    public ResponseEntity<Object> getAllMenuItems(@RequestParam(value = "restaurantId") Long restaurantId){
        return ResponseEntity.ok(menuItemService.getAllMenuItems(restaurantId));
    }

    @GetMapping(value = "/api/staff/menuItem")
    public ResponseEntity<MenuItem> getMenuItem(@RequestParam(value ="menuItemId") Long menuItemId){
        return ResponseEntity.ok(menuItemService.getMenuItem(menuItemId));
    }

    @PostMapping(value = "/api/staff/createOrder")
    public ResponseEntity<Object> createOrder(@RequestBody OrderRequest orderRequest){
        try {
            Order order = orderService.createOrder(orderRequest);
            return new ResponseEntity<>(order, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/api/staff/finalOrderInfo")
    public ResponseEntity<Object> getFinalOrderInfo(@RequestParam(value = "orderId") Long orderId){
        try {
            return new ResponseEntity<>(orderService.createFinalOrderInfo(orderId),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);

        }
    }

    @PutMapping(value = "/api/staff/updateOrderStatus")
    public ResponseEntity<Object> updateOrderStatus(@RequestParam(value = "orderId")Long orderId, @RequestParam(value = "orderStatus") OrderStatus orderStatus){
        try {
            orderService.updateOrderStatus(orderId,orderStatus);
            return new ResponseEntity<>("Order updated successfully to "+orderStatus+"!",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Couldn't update the order!",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/api/staff/deleteOrder")
    public ResponseEntity<Object> deleteOrder(@RequestParam(value = "orderId") Long orderId){
        try {
            orderService.deleteOrder(orderId);
            return new ResponseEntity<>("Order deleted!",HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Order couldn't be deleted!",HttpStatus.BAD_REQUEST);
        }
    }


}
