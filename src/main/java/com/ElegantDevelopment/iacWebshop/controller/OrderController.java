package com.ElegantDevelopment.iacWebshop.controller;


import com.ElegantDevelopment.iacWebshop.exception.ResourceNotFoundException;
import com.ElegantDevelopment.iacWebshop.model.Order;
import com.ElegantDevelopment.iacWebshop.model.Order;
import com.ElegantDevelopment.iacWebshop.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/rest/order")
public class OrderController {

    @Autowired
    OrderRepo orderRepo;

    // Get All Notes
    @GetMapping("")
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable(value = "id") Long orderId) {
        return orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
    }

    @PostMapping("")
    public Order createOrder(@Valid @
            RequestBody Order order) {
        return orderRepo.save(order);
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable(value = "id") Long orderId,
                                   @Valid @RequestBody Order orderDetails) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        order.setDeliveryAddress(orderDetails.getDeliveryAddress());

        Order updatedOrder = orderRepo.save(order);
        return updatedOrder;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable(value = "id") Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        orderRepo.delete(order);

        return ResponseEntity.ok().build();
    }


}
