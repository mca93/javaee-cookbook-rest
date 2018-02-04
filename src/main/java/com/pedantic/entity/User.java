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
@NamedQuery(name = User.FIND_USER_BY_CREDENTIALS, query = "select  u from User u where u.email = :email and u.password = :password")
public class User implements Serializable {

    public static final String FIND_USER_BY_CREDENTIALS = "findUserByEmailPassword";
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
//    @Pattern(regexp = "[a-zA-Z][a-zA-Z_0-9]*", message = "Password must start with an alphabet ...")
    private String password;

    private String fileName;

    @Lob
    private byte[] picture;

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

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
