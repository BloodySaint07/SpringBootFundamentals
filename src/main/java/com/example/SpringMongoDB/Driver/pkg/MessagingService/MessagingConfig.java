package com.example.SpringMongoDB.Driver.pkg.MessagingService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import javax.jms.ConnectionFactory;

@Configuration
@EnableJms
public class MessagingConfig {

    /**
     * LOGGER
     */
    Logger LOGGER = LogManager.getLogger(MessagingConfig.class);

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(
            ConnectionFactory connectionFactory) {

        LOGGER.info(" ***  Connection Factory *** "+connectionFactory.toString());

        DefaultJmsListenerContainerFactory factory
                = new DefaultJmsListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrency("5-10");

        return factory;
    }

}
