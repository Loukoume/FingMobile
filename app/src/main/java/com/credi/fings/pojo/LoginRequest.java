package com.credi.fings.pojo;

import java.io.Serializable;

// LoginRequest.java
public class LoginRequest implements Serializable {
    private String username;
    private String password;

    // Constructeur
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters et setters (nécessaires pour Gson)
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

