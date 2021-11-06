package com.example.SpringMongoDB.Driver.pkg.BatchRun;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Configuration
public class BatchConfig {

    /**
     * LOGGER
     */
    Logger LOGGER = LogManager.getLogger(BatchConfig.class);

    /** Class Dependencies */
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("Username_JOB_1")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("Steps")
                .<String, String>chunk(1)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }


    @Bean
    public JobReader reader() {
        return new JobReader();
    }

    @Bean
    public JobWriter writer() {
        return new JobWriter();
    }

    @Bean
    public JobProcessor processor() {
        return new JobProcessor();
    }

    @Bean
    public JobListener listener() {
        return new JobListener();
    }


}
