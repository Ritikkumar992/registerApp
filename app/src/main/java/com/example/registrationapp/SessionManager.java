package com.example.registrationapp;

import android.content.Context;
import android.content.SharedPreferences;


public class SessionManager {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_NAME = "name"; // Change key to save user's name
    private static final String KEY_EMAIL = "email";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void saveName(String name) { // Modify method to save user's name
        editor.putString(KEY_NAME, name);
        editor.apply();
    }

    public String getName() { // Modify method to get user's name
        return pref.getString(KEY_NAME, null);
    }

    public void saveEmail(String email) {
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }
    public String getEmail() {
        return pref.getString(KEY_EMAIL, null);
    }

    public void clearSession() {
        editor.clear();
        editor.apply();
    }
}
