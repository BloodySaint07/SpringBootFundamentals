package com.example.SpringMongoDB.Driver.pkg.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="T_PTY_EMPLOYEE" )
@Entity
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "EMPLOYEE_SEQ_NAME", sequenceName = "EMPLOYEE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "EID")
    private long eid;
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Column(name = "LASTNAME")
    private String lastname;
    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;
    @Column(name = "PASSWORD", nullable = false)
    private String password;
    @Column(name = "ACTIVITY")
    private String activity;
    @Column(name = "RECORD_UPDATED")
    private String recordupdated;
    @Column(name = "DEPARTMENT")
    private String department;
    @Column(name = "COMPANY_START_TIMESTAMP")
    private Timestamp timestamp;

    public Employee(String firstname, String lastname, String username, String password, String activity, String recordupdated, String department, Timestamp timestamp) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.activity = activity;
        this.recordupdated = recordupdated;
        this.department = department;
        this.timestamp = timestamp;
    }
}
