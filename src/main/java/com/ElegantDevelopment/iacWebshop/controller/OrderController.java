package com.ElegantDevelopment.iacWebshop.controller;

import com.ElegantDevelopment.iacWebshop.exception.ResourceNotFoundException;
import com.ElegantDevelopment.iacWebshop.model.Address;
import com.ElegantDevelopment.iacWebshop.model.Customer;
import com.ElegantDevelopment.iacWebshop.model.Order;
import com.ElegantDevelopment.iacWebshop.model.User;
import com.ElegantDevelopment.iacWebshop.repository.OrderRepo;
import com.ElegantDevelopment.iacWebshop.util.JwtUtil;
import com.sun.xml.internal.ws.api.addressing.AddressingPropertySet;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/rest/order")
public class OrderController {

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    private JwtUtil jwtUtil;

    // Get All Notes
    @GetMapping("")
    public List<Order> getAllCategories() {
        System.out.println(orderRepo.findAll());
        return orderRepo.findAll();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable(value = "id") Long orderId) {
        return orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
    }

    @PostMapping("")
    public Order createOrder(@Valid @RequestBody Order order, @RequestHeader("Authorization") String token) throws UnsupportedEncodingException {

        User user = jwtUtil.parseJWT(token);
        String customerName = user.getCustomer().getFirstName() + " " + user.getCustomer().getLastName();
        Address customerAddress = user.getCustomer().getHomeAddress();
        double customerAmount = order.getAmount();

//        call method specified in uniqueCode (in seperate Thread?)

        return orderRepo.save(order);
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable(value = "id") Long orderId,
                                   @Valid @RequestBody Order orderDetails) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        order.setDeliveryAddress(orderDetails.getDeliveryAddress());

        return orderRepo.save(order);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable(value = "id") Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        orderRepo.delete(order);

        return ResponseEntity.ok().build();

    }
}
