package com.example.SpringMongoDB.Driver.pkg.Health;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class AppHealthIndicator implements HealthIndicator {

    /** LOGGER */
    Logger LOGGER = LogManager.getLogger(AppHealthIndicator.class);
    @Override
    public Health health() {
        LOGGER.info("*** Inside health() in "+ this.getClass().getName()+" Class");
        boolean error=false;
        if(error){

            return Health.down().withDetail("Error Key",123).build();
        }
        return Health.up().build();
    }
}
