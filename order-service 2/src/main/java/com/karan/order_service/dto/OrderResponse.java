package com.karan.order_service.dto;

import com.karan.order_service.model.OrderLineItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderResponse {
    private Long id;
    private String orderNumber;
    private List<OrderLineItems> orderLineItems;
}
