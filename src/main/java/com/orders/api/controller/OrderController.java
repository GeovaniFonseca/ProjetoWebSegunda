package com.orders.api.controller;

import com.orders.api.entity.Customer;
import com.orders.api.entity.Product;
import com.orders.api.entity.dto.OrderRequest;
import com.orders.api.entity.dto.OrderResponse;
import com.orders.api.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
@SecurityRequirement(name = "Bearer Authentication")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> orders = orderService.getAllOrders();
        if (!orders.isEmpty()) {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getCustomerByName(@PathVariable String name) {
        List<Customer> customers = orderService.getCustomerByName(name);
        if (!customers.isEmpty()) {
            return ResponseEntity.ok(customers);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No customers found with the given name");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getOrderById(@PathVariable("id") int id) {
        Optional<Customer> order = orderService.getOrderById(id);
        return order.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Customer> createOrder(@RequestBody Customer customer) {
        Customer createdOrder = orderService.createOrder(customer);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateOrder(@PathVariable("id") int id, @RequestBody Customer updatedOrder) {
        Optional<Customer> order = orderService.updateOrder(id, updatedOrder);
        return order.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") int id) {
        boolean deleted = orderService.deleteOrder(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
