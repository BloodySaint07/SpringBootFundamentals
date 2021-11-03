package com.example.SpringMongoDB.Driver.pkg.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApiRequestHandler extends RuntimeException{

    public ApiRequestHandler(String message){
        super(message);
    }
    public ApiRequestHandler(String message,Throwable cause){
        super(message,cause);
    }


}
