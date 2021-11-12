package com.example.SpringMongoDB.Driver.pkg.MessagingService;

import com.example.SpringMongoDB.Driver.pkg.Constants.IErrorConstants;
import com.example.SpringMongoDB.Driver.pkg.model.CustomException;
import com.example.SpringMongoDB.Driver.pkg.model.SystemMessage;
import com.example.SpringMongoDB.Driver.pkg.repository.ISystemMessageRepository;
import com.example.SpringMongoDB.Driver.pkg.service.ICustomExceptionService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public  class MessageReceiver implements MessageListener {

    /**
     * LOGGER
     */
    Logger LOGGER = LogManager.getLogger(MessageReceiver.class);

    @Autowired
    private static ICustomExceptionService customExceptionService;
    @Autowired
    private ISystemMessageRepository systemMessageRepository;


    @JmsListener(destination = "infoQueue")
    public void messageListener(SystemMessage systemMessage) {
        LOGGER.info(" *** Inside infoQueue receive() in " + this.getClass().getName() + " Class ");
        try {
            LOGGER.info(" *** Message Received ====> " + systemMessage + "  *** ");
            LOGGER.info("Message received! {}", systemMessage);
            systemMessageRepository.save(systemMessage);
        } catch (Exception ex) {
            CustomException customException = new CustomException(IErrorConstants.MESSAGINGERROR + " " + ex.getMessage().toString());
            customExceptionService.saveException(customException);
        }
    }


    @JmsListener(destination = "ActiveMQInputQueue")
    public void messageListener2(String message) {
        LOGGER.info("*** Inside messageListener2  in " + getClass().getName() + " Class ");
        try {
            boolean isRead = true;
            LOGGER.info(" *** Message Received ====> " + message + "  *** ");
            LOGGER.info("Message Body [", message.toString() + "]");
            ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection con = factory.createConnection();
            Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("tmp_queue2");
            MessageConsumer consumer = session.createConsumer(queue);
            LOGGER.info("****** Session Created ****** " + session);
            LOGGER.info("****** Queue Initialised ****** " + queue);
            con.start();
            while (true) {
                if (message != null && isRead) {
                    String tm = message;
                    isRead = false;
                    LOGGER.info("****** Text Message ****** " + tm);
                } else {
                    System.out.println("*** No more Messages is Queue *** ");
                    LOGGER.info(" *** Queue Empty *** ");
                    /**  Stop Connection and Break Loop Disabled */
                    con.stop();
                    break;

                }
            }
        } catch (Exception ex) {
            CustomException customException = new CustomException(IErrorConstants.MESSAGINGERROR + " " + ex.getMessage().toString());
            customExceptionService.saveException(customException);
        }
    }


    @JmsListener(destination = "tmp_queue2")
    public void onMessage(Message message) {
        LOGGER.info("*** Inside tmp_queue2 receive() in " + this.getClass().getName() + " Class ");
        try {
            LOGGER.info(" *** Message Received ====> " + message + "  *** ");
            LOGGER.info("Message Body [", message.toString() + "]");


            ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection con = factory.createConnection();
            Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("tmp_queue2");
            MessageConsumer consumer = session.createConsumer(queue);
            LOGGER.info("****** Session Created ****** " + session);
            LOGGER.info("****** Queue Initialised ****** " + queue);
            con.start();
            while (true) {
                Message msg = consumer.receive(5000);
                LOGGER.info("****** Un-Processed Message  ****** " + msg);
                LOGGER.info("****** Message Class  ****** " + msg.getClass().toString());
                if (msg instanceof TextMessage) {
                    TextMessage tm = (TextMessage) msg;
                    LOGGER.info("****** Text Message ****** " + tm.getText());
                    System.out.println(tm.getText());
                } else {
                    System.out.println("*** No more Messages is Queue *** ");
                    LOGGER.info(" *** Queue Empty *** ");
                    /**  Stop Connection and Break Loop Disabled */
                    // con.stop();
                    // break;

                }
            }
        } catch (Exception ex) {
            CustomException customException = new CustomException(IErrorConstants.MESSAGINGERROR + " " + ex.getMessage().toString());
            customExceptionService.saveException(customException);
        }

    }
}