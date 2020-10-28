package com.demo.grillyapp.api.models.request;

public class LoginCredentials {

    private String email;
    private String password;

    public LoginCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
