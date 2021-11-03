package com.example.SpringMongoDB.Driver.pkg.repository;

import com.example.SpringMongoDB.Driver.pkg.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

    // Get All Usernames against Name JPQL
    @Query(value = "SELECT USERNAME FROM T_USER_MASTER WHERE NAME=?1", nativeQuery = true)
    String findUserNamesByName(@Param("name") String name);


}
