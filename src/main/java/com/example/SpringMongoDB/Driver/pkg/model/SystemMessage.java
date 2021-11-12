package com.example.SpringMongoDB.Driver.pkg.model;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_MESSAGE_MASTER")
public class SystemMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MID")
    private int id;
    @Column(name = "MESSAGE_SOURCE")
    private String source;
    @Column(name = "MESSAGE")
    private String message;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SystemMessage{" +
                "source='" + source + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
