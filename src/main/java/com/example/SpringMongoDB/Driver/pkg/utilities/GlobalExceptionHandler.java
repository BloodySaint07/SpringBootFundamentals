package com.example.SpringMongoDB.Driver.pkg.utilities;

import com.example.SpringMongoDB.Driver.pkg.model.CustomException;
import com.example.SpringMongoDB.Driver.pkg.service.ICustomExceptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler {

    /** Logger */
    Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

    /** Exception Service calls for Persistence */
    @Autowired
    private  ICustomExceptionService customExceptionService;



    @ExceptionHandler(value={ApiRequestHandler.class})
    @ResponseStatus(value=HttpStatus.BAD_REQUEST,reason="Invalid Input. Please provide all the mandatory details in correct format")
    public ResponseEntity<Object> handleAllApiRequestException(ApiRequestHandler ce){

        CustomException customException=new CustomException(ce.getMessage());
        HttpStatus status =HttpStatus.BAD_REQUEST;

        LOGGER.info("**** LOGGING EXCEPTION : API EXCEPTION  **** "+ ce.getMessage());
        String errorMessage=ce.getLocalizedMessage();
        CustomException customException1 =new CustomException(ce.getMessage().toString());
        customExceptionService.saveException(customException1);

        return new ResponseEntity<>(customException1,status) ;

    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundHandling(ResourceNotFoundException exception, WebRequest request){

        LOGGER.info("**** LOGGING EXCEPTION : RESOURCE NOT FOUND  **** "+ exception.getMessage());
        String errorMessage=exception.getMessage();
        CustomException customException =new CustomException(errorMessage);
        customExceptionService.saveException(customException);

        return new ResponseEntity<>(customException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({Exception.class, SQLIntegrityConstraintViolationException.class})
    @ResponseStatus(value=HttpStatus.BAD_REQUEST,reason=" FAILED to Persist Details.Please check Error Table or check Logs")
    public ResponseEntity<CustomException> globalExceptionHandling(Exception exception, WebRequest request){

        LOGGER.info("**** LOGGING EXCEPTION : GLOBAL **** "+ exception.getLocalizedMessage());
        String errorMessage=exception.getMessage();
        CustomException customException =new CustomException(errorMessage);
        customExceptionService.saveException(customException);
        return new ResponseEntity<CustomException>(customException, HttpStatus.BAD_REQUEST);
    }



}
