package com.example.SpringMongoDB.Driver.pkg.BatchRun;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class JobListener implements JobExecutionListener {

    /** LOGGER */
    Logger LOGGER = LogManager.getLogger(JobListener.class);
    @Override
    public void beforeJob(JobExecution jobExecution) {

        LOGGER.info(" *** Inside beforeJob() in " + this.getClass().getName() + " Class");
        LOGGER.info(" *** Job Started *** "+jobExecution.getStatus().getBatchStatus().toString());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        LOGGER.info(" *** Inside afterJob() in " + this.getClass().getName() + " Class");
        LOGGER.info(" *** Job Ended *** "+jobExecution.getStatus().toString());

    }
}
