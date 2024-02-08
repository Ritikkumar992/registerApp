package com.example.registrationapp;


import android.content.Context;
import android.content.SharedPreferences;
public class SessionManager {
    Context context;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private final String PREF_FILE_NAME = "registerApp";
    private final String KEY_IF_LOGGED_IN = "key_session_if_logged_in";
    private final String KEY_NAME = "key_session_name";
    private final String KEY_EMAIL = "key_session_email";
    private final String KEY_CONATCT = "key_session_contact";
    private  final int PRIVATE_MODE =0;
    public boolean checkSession ()
    {
        if(sp.contains(KEY_IF_LOGGED_IN))
            return true;
        else
            return false;
    }
    public SessionManager(Context context){
        this.context =context;
        sp=context.getSharedPreferences(PREF_FILE_NAME,PRIVATE_MODE);
        editor= sp.edit();
    }
    public void createSession(String username , String email , String contact)
    {
        editor.putString(KEY_NAME , username);
        editor.putString(KEY_EMAIL , email);
        editor.putString(KEY_CONATCT , contact);
        editor.putBoolean(KEY_IF_LOGGED_IN, true);
        editor.commit();
    }
    public String getSessionDetails(String key)
    {
        String value = sp.getString(key,null);
        return value;
    }
    public void clearSession()
    {
        editor.clear();
        editor.commit();
    }
    public String saveName(String name)
    {
        editor.putString(KEY_NAME,name);
        editor.apply();
        return name;
    }
    public String saveEmail (String email)
    {
        editor.putString(KEY_EMAIL,email);
        editor.apply();
        return email;
    }
    public String saveContact(String contact)
    {
        editor.putString(KEY_CONATCT,contact);
        editor.apply();
        return contact;
    }
}
