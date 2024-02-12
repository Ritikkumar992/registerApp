package com.example.registrationapp;
import android.content.Context;
import android.content.SharedPreferences;

// SessionManager class is created.
public class SessionManager {
    Context context;
    SharedPreferences shared_preference;
    SharedPreferences.Editor editor;
    // session management keys:
    private final String PREF_FILE_NAME = "registerApp";
    private final String KEY_IF_LOGGED_IN = "key_session_if_logged_in";
    private final String KEY_NAME = "key_session_name";
    private final String KEY_EMAIL = "key_session_email";
    private final String KEY_CONATCT = "key_session_contact";
    private  final int PRIVATE_MODE =0;
    
    public boolean checkSession ()
    {
        if(shared_preference.contains(KEY_IF_LOGGED_IN))
            return true;
        else
            return false;
    }
    public SessionManager(Context context){
        this.context =context;
        shared_preference = context.getSharedPreferences(PREF_FILE_NAME,PRIVATE_MODE);
        editor= shared_preference.edit();
    }
    // session created with user details.
    public void createSession(String username , String email , String contact)
    {
        editor.putString(KEY_NAME , username);
        editor.putString(KEY_EMAIL , email);
        editor.putString(KEY_CONATCT , contact);
        editor.putBoolean(KEY_IF_LOGGED_IN, true);
        editor.commit();
    }
    // session details 
    public String getSessionDetails(String key)
    {
        String value = shared_preference.getString(key,null);
        return value;
    }
    // session cleared
    public void clearSession()
    {
        editor.clear();
        editor.commit();
    }
    // user name saved.
    public String saveName(String name)
    {
        editor.putString(KEY_NAME,name);
        editor.apply();
        return name;
    }
    // user email saved.
    public String saveEmail (String email)
    {
        editor.putString(KEY_EMAIL,email);
        editor.apply();
        return email;
    }
    // user contact saved.
    public String saveContact(String contact)
    {
        editor.putString(KEY_CONATCT,contact);
        editor.apply();
        return contact;
    }
}
