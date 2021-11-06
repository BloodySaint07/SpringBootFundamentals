package com.example.SpringMongoDB.Driver.pkg.MessagingService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

    /**
     * LOGGER
     */
    Logger LOGGER = LogManager.getLogger(MessageReceiver.class);

    @JmsListener(destination = "${springjms.myQueue}")
    public void receive(String message) {
        LOGGER.info("*** Inside receive() in " + this.getClass().getName() + " Class ");
        LOGGER.info(" *** Message Received ====> " + message + "  *** ");

    }
}
