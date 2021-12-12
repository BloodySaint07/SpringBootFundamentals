package com.example.SpringMongoDB.Driver.pkg.service;

import com.example.SpringMongoDB.Driver.pkg.Constants.IErrorConstants;
import com.example.SpringMongoDB.Driver.pkg.model.CustomException;
import com.example.SpringMongoDB.Driver.pkg.model.Employee;
import com.example.SpringMongoDB.Driver.pkg.repository.IEmployeeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class EmployeeService implements  IEmployeeService {

    /**
     * Logger & Class Dependencies
     */
    Logger LOGGER = LogManager.getLogger(EmployeeService.class);

    @Autowired
    IEmployeeRepository employeeRepository;
    @Autowired
    private CustomExceptionService customExceptionService;

    @Override
    public void saveEmployee(Employee employee) {
        LOGGER.info("*** Inside saveEmployee in " + this.getClass().getName() + " Class");
        CustomException customException = new CustomException();
        try {
            employeeRepository.save(employee);
        } catch (Exception ex) {
            LOGGER.info("*** Failed to Save Employee Details " + this.getClass().getName() + " Class");
            customException.setErrorMessage(IErrorConstants.INVALIDREQUEST + " " + ex.getStackTrace().toString());
            customExceptionService.saveException(customException);
        }

    }

}
