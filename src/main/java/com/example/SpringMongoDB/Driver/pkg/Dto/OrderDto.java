package com.example.SpringMongoDB.Driver.pkg.Dto;

import com.example.SpringMongoDB.Driver.pkg.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private String name;
    private String userId;
    private String orderTime;

    public Order convertToOrder()
    {
        return Order.builder()
                .orderName(name)
                .userId(userId)
                .orderTime(orderTime)
                .build();
    }
}
