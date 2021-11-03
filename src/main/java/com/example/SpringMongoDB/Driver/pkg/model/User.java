package com.example.SpringMongoDB.Driver.pkg.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * Author:Soumyajit
 * Date:Oct 2021
 * */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="T_USER_MASTER" )
@Entity
public class User  implements Serializable {

    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "USER_SEQ_NAME", sequenceName = "USER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    //  @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "Id")
    private int Id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column
    private String name;
    @Column
    private String country;
    @Column(nullable = false)
    private LocalDateTime createTime;

    // @Embedded
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", referencedColumnName = "Id")
    @AttributeOverrides(value = {
            @AttributeOverride(name = "addressLine1", column = @Column(name = "house_number")),
            @AttributeOverride(name = "addressLine2", column = @Column(name = "street"))
    })
    @Nullable
    @JsonManagedReference
    private Address address;


    public User(String username, String name, String country, Address address) {
        this.username = username;
        this.name = name;
        this.country = country;
        this.createTime = LocalDateTime.now();
        this.address = address;

    }
}
