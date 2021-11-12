package com.example.SpringMongoDB.Driver.pkg.MessagingService;

import com.example.SpringMongoDB.Driver.pkg.model.SystemMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {
    /**
     * LOGGER
     */
    Logger LOGGER = LogManager.getLogger(MessageSender.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    // @Value("${springjms.myQueue}")
    @Value("infoQueue")
    private String queue;

    public void send(SystemMessage systemMessage) {
        LOGGER.info(" *** Inside send() in " + this.getClass().getName() + " Class ");
        LOGGER.info(" *** Message Sent Successfully *** ");
        jmsTemplate.convertAndSend(queue, systemMessage);
    }

}
