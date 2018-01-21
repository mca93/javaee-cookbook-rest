package com.pedantic.entity;

import javax.ws.rs.FormParam;

public class User {

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
}
