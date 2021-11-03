package com.example.SpringMongoDB.Driver.pkg.BatchRun;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class JobWriter implements ItemWriter<String> {
    /** LOGGER */
    Logger LOGGER = LogManager.getLogger(JobWriter.class);
    @Override
    public void write(List<? extends String> items) throws Exception {
        LOGGER.info(" *** Inside write() in " + this.getClass().getName() + " Class");
        LOGGER.info(" *** Writing items: " + items);

    }
}
