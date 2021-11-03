package com.example.SpringMongoDB.Driver.pkg.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_ERR_MASTER")
@Entity
public class CustomException  {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int errorId;
    @Column
    @NotNull
    private String errorMessage;
    @Column
    private LocalDateTime errorTimestamp;

    // Custom Constructor
    public CustomException(String errorMessage){
        this.errorMessage= errorMessage;
        this.errorTimestamp=LocalDateTime.now();
    }


}
