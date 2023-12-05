package com.ssw.restohub.controllers;

import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;
import com.ssw.restohub.data.MenuItem;
import com.ssw.restohub.data.Order;
import com.ssw.restohub.data.OrderItem;
import com.ssw.restohub.enums.OrderStatus;
import com.ssw.restohub.pojo.FinalOrderInfo;
import com.ssw.restohub.pojo.OrderRequest;
import com.ssw.restohub.service.HtmlToPdfService;
import com.ssw.restohub.service.MenuItemService;
import com.ssw.restohub.service.OrderService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xhtmlrenderer.util.IOUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin
public class StaffViewController {

    private MenuItemService menuItemService;
    private OrderService orderService;
    private HtmlToPdfService htmlToPdfService;

    @Autowired
    public StaffViewController(MenuItemService menuItemService, OrderService orderService,HtmlToPdfService htmlToPdfService){
        this.menuItemService = menuItemService;
        this.orderService = orderService;
        this.htmlToPdfService = htmlToPdfService;
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
    public ResponseEntity<Object> getFinalOrderInfo(@RequestParam(value = "reservationCode") String reservationCode){
        try {
            return new ResponseEntity<>(orderService.createFinalOrderInfo(reservationCode),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping(value = "/api/staff/getOrder")
    public ResponseEntity<Object> getOrder(@RequestParam(value = "reservationCode") String reservationCode){
        try {
            return new ResponseEntity<>(orderService.getOrder(reservationCode),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping(value = "/api/staff/updateOrder")
    public ResponseEntity<Object> updateOrder(@RequestBody Order order){
        try {
            Order updatedOrder = orderService.updateOrder(order);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }



    @PutMapping(value = "/api/staff/updateOrderStatus")
    public ResponseEntity<Object> updateOrderStatus(@RequestParam(value = "reservationCode")String reservationCode, @RequestParam(value = "orderStatus") OrderStatus orderStatus){
        try {
            orderService.updateOrderStatus(reservationCode,orderStatus);
            return new ResponseEntity<>("Order updated successfully to "+orderStatus+"!",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Couldn't update the order!",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/api/staff/deleteOrder")
    public ResponseEntity<Object> deleteOrder(@RequestParam(value = "reservationCode") String reservationCode){
        try {
            orderService.deleteOrder(reservationCode);
            return new ResponseEntity<>("Order deleted!",HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/api/staff/generatePdf")
    public ResponseEntity<String> generateFinalOrderPdf(@RequestParam(value = "reservationCode") String reservationCode, HttpServletResponse httpServletResponse) throws Exception {
        FinalOrderInfo finalOrderInfo = orderService.createFinalOrderInfo(reservationCode);
        if (finalOrderInfo.getOrderStatus().equals(OrderStatus.COMPLETED)) {
            ByteArrayInputStream byteArrayInputStream = htmlToPdfService.convertHtmlToPdf(finalOrderInfo,"finalOrder");
            httpServletResponse.setContentType("application/octet-stream");
            httpServletResponse.setHeader("Content-Disposition","attachment; filename-finalOrderInfo.pdf");
            IOUtils.copy(byteArrayInputStream,httpServletResponse.getOutputStream());
        } else {
            return new ResponseEntity<>("Order not complete yet!", HttpStatus.BAD_REQUEST);
        }
        return null;
    }


}
