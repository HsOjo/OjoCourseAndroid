package com.hsojo.ojocourse.services.ojocourse.request;

public class UserBindRequest {
    public String username;
    public String password;

    public UserBindRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
