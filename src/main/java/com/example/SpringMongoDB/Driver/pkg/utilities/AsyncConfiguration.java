package com.example.SpringMongoDB.Driver.pkg.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfiguration  extends AsyncConfigurerSupport {

    @Autowired
    private AsyncExceptionHandler asyncExceptionHandler;

    /** LOGGER */
    Logger LOGGER = LogManager.getLogger(AsyncConfiguration.class);

    @Override
    @Bean("threadPoolTaskExecutor")
    public Executor getAsyncExecutor() {

        LOGGER.info("Thread Test 6 :"+Thread.currentThread().getName());
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(20);
        taskExecutor.setQueueCapacity(500);
        taskExecutor.setThreadNamePrefix("Async Thread - ");
        taskExecutor.initialize();
        LOGGER.info("Thread Test 7 :"+Thread.currentThread().getName());
        return taskExecutor;


    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return asyncExceptionHandler;
    }
}
