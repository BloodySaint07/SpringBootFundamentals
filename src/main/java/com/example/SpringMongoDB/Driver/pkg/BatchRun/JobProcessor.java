package com.example.SpringMongoDB.Driver.pkg.BatchRun;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

public class JobProcessor implements ItemProcessor<String,String> {

    /** LOGGER */
    Logger LOGGER = LogManager.getLogger(JobProcessor.class);
    @Override
    public String process(String item) throws Exception {

        LOGGER.info("*** Inside process() in " + this.getClass().getName() + " Class");

        return "PROCESSED " + item.toUpperCase();
    }
}
