package com.karan.order_service.service;

import com.karan.order_service.Config.WebClientConfig;
import com.karan.order_service.dto.OrderRequest;
import com.karan.order_service.dto.OrderResponse;
import com.karan.order_service.model.Order;
import com.karan.order_service.model.OrderLineItems;
import com.karan.order_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClientConfig webClientConfig;
    private final WebClient webClient;

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(dto -> new OrderLineItems(dto.getSkuCode(), dto.getPrice(), dto.getQuantity()))
                .collect(Collectors.toList());

        order.setOrderLineItems(orderLineItems);
        List<String> skuCodes = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(item -> item.getSkuCode())
                .toList();
// calling inventory service from order service before placing order
        Boolean inStock = webClient.get()
                .uri("http://localhost:8081/api/inventory", uri -> uri.queryParam("skuCodes", String.join(",", skuCodes)).build())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if (Boolean.TRUE.equals(inStock)) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock, please try again later.");
        }

        log.info("Order {} placed successfully!", order.getOrderNumber());

        return "Order placed successfully with Order Number: " + order.getOrderNumber();
    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(order -> new OrderResponse(
                        order.getId(),
                        order.getOrderNumber(),
                        order.getOrderLineItems()))
                .collect(Collectors.toList());
    }
    // get order by id
    public OrderResponse getOrderById(Long id){
        Order order = orderRepository.findById(id).orElseThrow(()->new RuntimeException("Order not found for given id"+id));
        log.info("Order {} found for given id", order.getId());

        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getOrderLineItems()

        );
    }
}
