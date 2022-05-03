package com.example.SpringMongoDB.Driver.pkg.service;

import com.example.SpringMongoDB.Driver.pkg.Constants.IErrorConstants;
import com.example.SpringMongoDB.Driver.pkg.model.CustomException;
import com.example.SpringMongoDB.Driver.pkg.model.Employee;
import com.example.SpringMongoDB.Driver.pkg.repository.IEmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
@Transactional
@Slf4j
public class EmployeeService implements  IEmployeeService, UserDetailsService {

    /**
     * Logger & Class Dependencies
     */
    Logger LOGGER = LogManager.getLogger(EmployeeService.class);

    @Autowired
    IEmployeeRepository employeeRepository;
    @Autowired
    private CustomExceptionService customExceptionService;

    /** Executor */
    Executor executor= Executors.newFixedThreadPool(30);

    /*@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Employee employee2 = new Employee();
        String id = employeeRepository.findIdByUsername(username);
        Long eid = Long.parseLong(id);
        Optional<Employee> employeeFetch=null;
        Employee employee=null;

        //List<Employee> employee1 = employeeRepository.findAllById(Collections.singleton(eid));
        if(employeeRepository.findById(eid) !=null){
             employeeFetch=employeeRepository.findById(eid);
        }


        //Employee employee = employee1.get(0);
        if(employeeFetch!=null) {
            employee = employeeFetch.get();
        }


        if (employee == null) {
            log.error("*** Employee Object is Null *** ");
            throw new UsernameNotFoundException(" Failed to get Employee Data inside loadUserByUsername() ");
        } else {
            log.info("*** Employee Object not Null *** ");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(employee.getDepartment()));
        authorities.add(new SimpleGrantedAuthority("Model"));

        return new org.springframework.security.core.userdetails.User(employee.getUsername(), employee.getPassword(), authorities);
    }*/


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = this.findByUserName(username);
        return new org.springframework.security.core.userdetails.User(employee.getUsername(), employee.getPassword(), new ArrayList<>());
    }




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

    @Override
    public Employee findByUserName(String username) {

        String id = employeeRepository.findIdByUsername(username);
        Long eid = Long.parseLong(id);
        return employeeRepository.getOne(eid);
    }
    @Override
   // @Async
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
        //return allEmployees;
    }


}
