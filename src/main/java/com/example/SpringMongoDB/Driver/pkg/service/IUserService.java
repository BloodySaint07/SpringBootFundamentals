package com.example.SpringMongoDB.Driver.pkg.service;

import com.example.SpringMongoDB.Driver.pkg.model.User;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;


public interface IUserService {
    void saveUser(User user) ;
    String checkStat();
    List<String> getAllUsernames() throws SQLException, SQLDataException;
    List<User> getAllUsers();
}
