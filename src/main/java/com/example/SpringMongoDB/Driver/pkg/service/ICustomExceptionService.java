package com.example.SpringMongoDB.Driver.pkg.service;

import com.example.SpringMongoDB.Driver.pkg.model.CustomException;


public interface ICustomExceptionService {

    void saveException(CustomException customException);
}
