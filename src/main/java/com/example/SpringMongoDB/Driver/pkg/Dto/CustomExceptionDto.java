package com.example.SpringMongoDB.Driver.pkg.Dto;

import com.example.SpringMongoDB.Driver.pkg.model.CustomException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomExceptionDto {


    private String errorMessage;

    public CustomException covertToCustomException()
    {
        return CustomException.builder()
                .errorMessage(errorMessage)
                .errorTimestamp(LocalDateTime.now())
                .build();
    }


}
