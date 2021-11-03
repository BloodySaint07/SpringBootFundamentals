package com.example.SpringMongoDB.Driver.pkg.service;


import com.example.SpringMongoDB.Driver.pkg.model.CustomException;
import com.example.SpringMongoDB.Driver.pkg.model.Order;
import com.example.SpringMongoDB.Driver.pkg.repository.IOrderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrderService implements IOrderService {

    @Autowired
    private  IOrderRepository orderRepository;
    @Autowired
    private CustomExceptionService customExceptionService;


    /** LOGGER */
    Logger LOGGER = LogManager.getLogger(UserService.class);


    @Override
    public void saveOrder(Order order) {
        LOGGER.info("*** Inside saveOrder in "+ this.getClass().getName()+" Class");
        CustomException customException=new CustomException();
        try {
            orderRepository.save(order);
        }catch(Exception ex1){
            customException.setErrorMessage(ex1.getMessage());
            customExceptionService.saveException(customException);
        }
    }



}
