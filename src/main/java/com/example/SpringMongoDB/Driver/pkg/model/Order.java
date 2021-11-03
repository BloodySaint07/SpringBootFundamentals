package com.example.SpringMongoDB.Driver.pkg.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_ORDER_MASTER")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="orderId")
    private Integer id;
    @Column
    private String userId;
    @Column
    private String orderName;
    @Column
    private LocalDateTime orderTime;

}
