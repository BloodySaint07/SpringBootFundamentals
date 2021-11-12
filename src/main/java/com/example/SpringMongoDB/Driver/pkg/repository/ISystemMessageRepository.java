package com.example.SpringMongoDB.Driver.pkg.repository;

import com.example.SpringMongoDB.Driver.pkg.model.SystemMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISystemMessageRepository extends JpaRepository<SystemMessage, Integer> {
}
