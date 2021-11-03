package com.example.SpringMongoDB.Driver.pkg.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Author:Soumyajit
 * Date:Oct 2021
 * Purpose: Spring Boot CRUD
 * */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Table(name="address")
@Entity
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private int id;

    @OneToOne(mappedBy = "address", fetch = FetchType.EAGER)
    @JsonBackReference
    private User user;


    @Size(max = 100)
    private String addressLine1;


    @Size(max = 100)
    private String addressLine2;


    @Size(max = 100)
    @Nullable
    private String city;


    @Size(max = 100)
    @Nullable
    private String state;


    @Size(max = 6)
    @Nullable
    private String zipCode;


}
