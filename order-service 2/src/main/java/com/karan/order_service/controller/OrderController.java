package com.karan.order_service.controller;

import com.karan.order_service.dto.OrderRequest;
import com.karan.order_service.dto.OrderResponse;
import com.karan.order_service.service.OrderService;
import lombok.AllArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;
    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest){
       String response = orderService.placeOrder(orderRequest);
       return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<OrderResponse>getAllOrders(){
        return orderService.getAllOrders();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public OrderResponse getOrderById(@PathVariable Long id){
        return orderService.getOrderById(id);
    }

}
