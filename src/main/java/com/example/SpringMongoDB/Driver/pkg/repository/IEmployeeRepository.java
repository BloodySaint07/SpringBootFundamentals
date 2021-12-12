package com.example.SpringMongoDB.Driver.pkg.repository;

import com.example.SpringMongoDB.Driver.pkg.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {

    // Get All Usernames against Name JPQL
    @Query(value = "SELECT USERNAME FROM T_PTY_EMPLOYEE WHERE USERNAME=?1", nativeQuery = true)
    String findUserNameByUserName(@Param("username") String username);

    @Query(value = "SELECT PASSWORD FROM T_PTY_EMPLOYEE WHERE USERNAME=?1", nativeQuery = true)
    String findPasswordByUsername(@Param("username") String username);

}
