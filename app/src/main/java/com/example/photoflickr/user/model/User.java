package com.example.photoflickr.user.model;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    //Khoa chinh
    @PrimaryKey(autoGenerate = true)
    private int id;

    //Cac thuoc tinh con lai
    private String username;
    private String email;
    private String password;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    //Kiem tra trong va dinh dang email
    public boolean isValidEmail() {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //Kiem tra trong password
    public boolean isValidPassword() {
        return !TextUtils.isEmpty(password) && password.length() >= 6;
    }
}
