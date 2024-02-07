package com.example.registrationapp;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Query("DELETE FROM usersTable")
    void deleteAllUsers();

    @Query("INSERT INTO usersTable(name,userEmail,password,contact) VALUES(:fullName,:userEmail,:password,:contact)")
    void insertUser(String fullName, String userEmail, String password, String contact);

    @Query("SELECT * FROM usersTable WHERE userEmail =:userEmail AND password =:password")
    User getUserByUserEmailAndPassword(String userEmail,String password);


    @Query("SELECT * FROM usersTable WHERE userEmail = :userEmail")
    User ifUsernameIsTaken(String userEmail);

    @Query("SELECT * FROM usersTable WHERE userEmail = :userEmail")
    User getUserEmail(String userEmail);


}
