package com.example.SpringMongoDB.Driver.pkg.service;

import com.example.SpringMongoDB.Driver.pkg.model.Employee;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IEmployeeService extends UserDetailsService {
    void saveEmployee(Employee employee);

    Employee findByUserName(String username);

    List<Employee> getAllEmployees();

}


