package com.example.registrationapp;

import androidx.room.Entity;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

// User Table created.
@Entity(tableName = "usersTable")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String fullName;

    @ColumnInfo(name = "userEmail") // userEmail id:
    private String userEmail;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "contact")
    private String contact;

    public User() {
    }

    public User(int id, String fullName, String userEmail, String password, String contact) {
        this.id = id;
        this.fullName = fullName;
        this.userEmail = userEmail;
        this.password = password;
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

}
