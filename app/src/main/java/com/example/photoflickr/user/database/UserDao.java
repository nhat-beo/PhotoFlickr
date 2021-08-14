package com.example.photoflickr.user.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.photoflickr.user.model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM user WHERE username= :username")
    List<User> checkUser(String username);

    @Query("SELECT * FROM user WHERE username= :username and password= :password")
    List<User> checkExistsUser(String username, String password);
}
