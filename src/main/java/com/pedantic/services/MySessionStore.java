package com.pedantic.services;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@SessionScoped
public class MySessionStore implements Serializable{

    private String encodedPassword;
    private String email;

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
