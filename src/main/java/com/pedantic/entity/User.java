package com.pedantic.entity;

import javax.persistence.*;
import javax.ws.rs.FormParam;
import java.io.Serializable;

@Entity
@Table(name = "application_user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @FormParam("name")
    private String nameOfUser;
    @FormParam("email")
    private String email;
    @FormParam("password")
    private String password;


    public String getNameOfUser() {
        return nameOfUser;
    }

    public void setNameOfUser(String nameOfUser) {
        this.nameOfUser = nameOfUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
