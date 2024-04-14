package com.example.UserAuthenticationService.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User
{
    @Id
    private String emailId;
    private String password;
    public User() {
    }

    public User(String emailId, String password) {
        this.emailId = emailId;
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
