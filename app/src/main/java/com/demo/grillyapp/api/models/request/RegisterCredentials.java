package com.demo.grillyapp.api.models.request;

public class RegisterCredentials {

    private String name;
    private String email;
    private String password;

    public RegisterCredentials(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
