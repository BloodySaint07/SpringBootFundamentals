package com.example.SpringMongoDB.Driver.pkg.service;


import com.example.SpringMongoDB.Driver.pkg.model.CustomException;
import com.example.SpringMongoDB.Driver.pkg.repository.ICustomExceptionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomExceptionService implements ICustomExceptionService {

    @Autowired
    private ICustomExceptionRepository customExceptionRepository;
    Logger LOGGER = LogManager.getLogger(CustomExceptionService.class);


    @Override
    public void saveException(CustomException customException) {

        LOGGER.info("*** Inside saveException in " + this.getClass().getName() + " Class");
        try {
            customExceptionRepository.save(customException);
        }catch(Exception e){
            LOGGER.info("*** SERVICE :Failed to Persist Exception in DB *** "+e.getMessage());

        }

    }

}