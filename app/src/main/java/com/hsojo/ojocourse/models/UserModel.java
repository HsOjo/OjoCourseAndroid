package com.hsojo.ojocourse.models;

import android.content.Context;
import android.content.SharedPreferences;

public class UserModel {
    public String username = "";
    public String name = "";
    public String number = "";
    public String token = "";

    public UserModel(String username, String name, String number, String token) {
        this.username = username;
        this.name = name;
        this.number = number;
        this.token = token;
    }

    public UserModel() {

    }

    public boolean saveData(Context context) {
        SharedPreferences sp = context.getSharedPreferences(this.getClass().getName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString("username", this.username);
        editor.putString("name", this.name);
        editor.putString("number", this.number);
        editor.putString("token", this.token);

        return editor.commit();
    }

    public boolean loadData(Context context) {
        SharedPreferences sp = context.getSharedPreferences(this.getClass().getName(), Context.MODE_PRIVATE);

        this.username = sp.getString("username", "");
        this.name = sp.getString("name", "");
        this.number = sp.getString("number", "");
        this.token = sp.getString("token", "");

        return !this.token.equals("");
    }

    public boolean clearData(Context context) {
        SharedPreferences sp = context.getSharedPreferences(this.getClass().getName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        return editor.commit();
    }
}
