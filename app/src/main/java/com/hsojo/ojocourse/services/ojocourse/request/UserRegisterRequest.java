package com.hsojo.ojocourse.services.ojocourse.request;

public class UserRegisterRequest {
    public String username;
    public String password;
    public String number;

    public UserRegisterRequest(String username, String password, String number) {
        this.username = username;
        this.password = password;
        this.number = number;
    }
}
