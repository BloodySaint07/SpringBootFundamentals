package com.example.SpringMongoDB.Driver.pkg.Controller;

import com.example.SpringMongoDB.Driver.pkg.Dto.OrderDto;
import com.example.SpringMongoDB.Driver.pkg.service.IOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/order")//pre-path
public class OrderController {

    @Autowired
    private IOrderService orderService;
    // LOGGER
    Logger LOGGER = LogManager.getLogger(UserController.class);


    @PostMapping("/createOrder")
    public ResponseEntity<?> saveOrder(@RequestBody OrderDto orderDto)
    {

        LOGGER.info("*** Inside saveOrder in "+ this.getClass().getName()+" Class");
        orderService.saveOrder(orderDto.convertToOrder());
        return new ResponseEntity<OrderDto>(orderDto, HttpStatus.CREATED);
    }


}
