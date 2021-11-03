package com.example.SpringMongoDB.Driver.pkg.repository;

import com.example.SpringMongoDB.Driver.pkg.model.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public  interface ICustomExceptionRepository extends JpaRepository<CustomException,Integer> {

}
