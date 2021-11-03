package com.example.SpringMongoDB.Driver.pkg.BatchRun;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;


public class JobReader implements ItemReader<String> {
    private String[] courses={"Java EE & Spring Boot","Angular 8 Essentials","Introduction to MongoDB"};
    private int count;

    /** LOGGER */
    Logger LOGGER = LogManager.getLogger(JobReader.class);
    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        LOGGER.info("*** Inside read() in "+ this.getClass().getName()+" Class");

        if (count < courses.length) {

            return courses[count++];
        } else {
            count = 0;
        }
        return null;
    }
}
