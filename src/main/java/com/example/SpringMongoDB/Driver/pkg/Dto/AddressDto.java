package com.example.SpringMongoDB.Driver.pkg.Dto;

import com.example.SpringMongoDB.Driver.pkg.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto implements Serializable  {

    private static final long serialVersionUID = 1L;

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipCode;

    public Address convertToAddress()
    {
        return Address.builder()
                .addressLine1(addressLine1)
                .addressLine2(addressLine2)
                .city(city)
                .state(state)
                .zipCode(zipCode)
                .build();
    }
}
