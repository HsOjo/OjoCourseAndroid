package com.hsojo.ojocourse.services.ojocourse.response;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

public class UserInfo {
    public String name;
    public String number;
    public String token;


    public UserInfo(String name, String number, String token) {
        this.name = name;
        this.number = number;
        this.token = token;
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String toString() {
        return String.format("<UserInfo name='%s' number='%s' token='%s'>", name, number, token);
    }
}
