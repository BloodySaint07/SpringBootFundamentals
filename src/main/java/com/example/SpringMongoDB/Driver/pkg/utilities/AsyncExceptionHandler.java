package com.example.SpringMongoDB.Driver.pkg.utilities;

import com.example.SpringMongoDB.Driver.pkg.model.CustomException;
import com.example.SpringMongoDB.Driver.pkg.service.CustomExceptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {


    @Autowired
    private CustomExceptionService customExceptionService;

    /** LOGGER */
    Logger LOGGER = LogManager.getLogger(AsyncExceptionHandler.class);

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {

        LOGGER.info("*** Async Call Failed "+ this.getClass().getName()+" Class");
        CustomException customException=new CustomException();
        customException.setErrorMessage(ex.getMessage()+" in Method: "+method.getName()+""+ex.getStackTrace().toString());
        customExceptionService.saveException(customException);

    }
}
