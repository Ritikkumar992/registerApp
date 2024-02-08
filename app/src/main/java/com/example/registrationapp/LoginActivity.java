package com.example.registrationapp;

import static com.example.registrationapp.AppDatabase.database;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    // variable declaration to fetch ui element of the app.
    Button loginBtn;
    EditText emailIdView, passwordView;
    TextView forgetBtn, registerBtn;

    private SessionManager sessionManager;

    // function to  fetch UI elements of the app.
    public void initializeView()
    {
        loginBtn = findViewById(R.id.loginBtn);
        emailIdView = findViewById(R.id.loginEmailId);
        passwordView = findViewById(R.id.loginPasswordId);
        forgetBtn = findViewById(R.id.forgetBtnId);
        registerBtn = findViewById(R.id.registerBtn);
        sessionManager = new SessionManager(getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        this.initializeView();

        // *****************************SESSION MANAGER***************************//
        SessionManager  sessionManager= new SessionManager(getApplicationContext());
        boolean checkSession = sessionManager.checkSession();

        if(checkSession)
            goToMainActivity();
        else {
            forgetBtn.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "We sent an Email with a link to get back into your account. ", Toast.LENGTH_SHORT).show());
            loginBtn.setOnClickListener(v -> checkCredentials());
            registerBtn.setOnClickListener(v -> goToRegisterActivity());
        }
    }
    // Other methods and code...
    // Method to check the validity of entered email and password
    private void checkCredentials(){
        AppDatabase database = AppDatabase.getInstance(this.getApplicationContext());
        String email = emailIdView.getText().toString();
        String password = passwordView.getText().toString();

        User userTable = database.userDAO().getUserByUserEmailAndPassword(email,password);
        if(userTable!=null) {
            // If credentials are valid, show a success toast and navigate to the home page of application
            Toast.makeText(getApplicationContext(), "Login Successfully  ", Toast.LENGTH_SHORT).show();
            SessionManager  sessionManager= new SessionManager(getApplicationContext());
            String name = sessionManager.saveName(userTable.getFullName());
            String contact = sessionManager.saveContact(userTable.getContact());

            sessionManager.createSession  (name, email, contact);

            goToMainActivity();
        }
        else
        {
            // If credentials are not valid, show a  toast message
            Toast.makeText(getApplicationContext(), "Email Address or password are invalid ! Please Try again. ", Toast.LENGTH_SHORT).show();
        }
    }
        // STEP_03: Is the user is new then => Register or signup page is opened
        public void goToRegisterActivity() {
                // Intent object is created of this class Context() to SignUpActivity class

                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
        };


    public void goToMainActivity()
    {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
