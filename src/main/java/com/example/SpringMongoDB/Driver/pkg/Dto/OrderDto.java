package com.example.SpringMongoDB.Driver.pkg.Dto;

import com.example.SpringMongoDB.Driver.pkg.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private String name;
    private String userId;

    public Order convertToOrder()
    {
        return Order.builder()
                .orderName(name)
                .userId(userId)
                .orderTime(LocalDateTime.now())
                .build();
    }
}
