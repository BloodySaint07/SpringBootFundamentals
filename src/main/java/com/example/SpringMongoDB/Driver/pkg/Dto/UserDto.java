package com.example.SpringMongoDB.Driver.pkg.Dto;

import com.example.SpringMongoDB.Driver.pkg.model.User;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String name;
    private String country;
    private String username;
    private AddressDto address;


    public User convertToUser()
    {
        return User.builder()
                .country(country)
                .createTime(LocalDateTime.now())
                .name(name)
                .username(username)
                .address(address == null ? null : address.convertToAddress())
                .build();
    }



}
