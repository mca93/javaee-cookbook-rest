package com.pedantic.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.FormParam;
import java.io.Serializable;

@Entity
@Table(name = "application_user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


//    @FormParam("name")
    @NotEmpty(message = "Name of user must be set")
    private String nameOfUser;


//    @FormParam("email")
    @NotEmpty(message = "Email must be set")
    @Email(message = "Email must be in the form username@domain.com")
    private String email;


//    @FormParam("password")
    @NotEmpty(message = "Password must be set.")
    @Pattern(regexp = "[a-zA-Z][a-zA-Z_0-9]*", message = "Password must start with an alphabet ...")
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
