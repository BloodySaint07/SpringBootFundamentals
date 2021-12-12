package com.example.SpringMongoDB.Driver.pkg.Dto;

import com.example.SpringMongoDB.Driver.pkg.Authentication.PasswordUtils;
import com.example.SpringMongoDB.Driver.pkg.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Optional;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int length=8;

    /**
     * Logger & Class Dependencies
     */
    Logger LOGGER = LogManager.getLogger(EmployeeDto.class);
    Timestamp getTimestamp = new Timestamp(System.currentTimeMillis());

    @Autowired
    PasswordUtils passwordUtils;

    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String activity;
    private String recordupdated;
    private String department;
    private Timestamp timestamp;



    public Employee convertToEmployee() throws UnsupportedEncodingException {

        LOGGER.info("*** Inside convertToEmployee in " + this.getClass().getName() + " Class");

        // To Get Static Salt
        // Optional<String> salt=passwordUtils.generateSalt(length);
        Optional<String> salt = passwordUtils.generateSaltWithSecretKey();
        Optional<String> hashThePlainTextPassword = passwordUtils.hashThePlainTextPassword(password, salt.toString());


        return Employee.builder()
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .password(hashThePlainTextPassword.toString())
                .activity("Y")
                .recordupdated("N")
                .department(department)
                .timestamp(getTimestamp)
                .build();

    }


}
