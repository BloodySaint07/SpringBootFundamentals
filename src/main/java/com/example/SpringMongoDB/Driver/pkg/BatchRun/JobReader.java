package com.example.SpringMongoDB.Driver.pkg.BatchRun;


import com.example.SpringMongoDB.Driver.pkg.Constants.IErrorConstants;
import com.example.SpringMongoDB.Driver.pkg.model.CustomException;
import com.example.SpringMongoDB.Driver.pkg.repository.IUserRepository;
import com.example.SpringMongoDB.Driver.pkg.service.ICustomExceptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class JobReader implements ItemReader<String> {


    /**
     * LOGGER
     */
    Logger LOGGER = LogManager.getLogger(JobReader.class);

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ICustomExceptionService customExceptionService;
    private int count;
    private int recordCount;
    private static String tempStringHolder;
    private static List<String> userNameList;
    private static String[] usernameArr;
    private String[] getDataToBeProcessed;
    /** Sample Array to Test Batch */
    private String[] courses = {"Java EE & Spring Boot", "Angular 8 Essentials", "Introduction to MongoDB"};

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        LOGGER.info("*** Inside read() in " + this.getClass().getName() + " Class ");

        try {
            getDataToBeProcessed = getDataToBeProcessedFromDB();
        } catch (NullPointerException ex4) {
            LOGGER.info("*** DID NOT GET ANY DATA *** ");
            CustomException customException = new CustomException(ex4.getMessage().toString().trim());
            customExceptionService.saveException(customException);
        }

        if (count < getDataToBeProcessed.length) {

            LOGGER.info("*** Data Counter *** " + count);
            return getDataToBeProcessed[count++];
        } else {
            count = 0;
        }
        return null;
    }

    public String[] getDataToBeProcessedFromDB() {

        LOGGER.info("*** Inside getDataToBeProcessedFromDB() in " + this.getClass().getName() + " Class ");

        try {

            userNameList = userRepository.findAllUserNames();
            usernameArr = userNameList.toArray(new String[userNameList.size()]);
            LOGGER.info("*** DATA EXTRACTED FROM DB  [ " + usernameArr + " ]  *** ");
            return usernameArr;
        } catch (Exception ex3) {
            LOGGER.info("*** FAILED TO READ DATA FROM DB *** ");
            CustomException customException = new CustomException(IErrorConstants.BATCFAILEDTORUN + " " + ex3.getMessage().toString().trim());
            customExceptionService.saveException(customException);
            return null;
        }


    }


}