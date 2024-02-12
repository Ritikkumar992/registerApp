package com.example.registrationapp;
import static com.example.registrationapp.UserDatabase.database;
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

        if(checkSession){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else {
            // forget button is clicked.
            forgetBtn.setOnClickListener(view -> 
                Toast.makeText(getApplicationContext(), "Reset Password link is send to registered email.", Toast.LENGTH_SHORT).show()
            );
            
            // login button is clicked.
            loginBtn.setOnClickListener(view -> 
                isValidUser();
            ); 
            // signup button is clicked.
            registerBtn.setOnClickListener(view ->
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            );
        }
    }
    // Method to check the validity of entered email and password
    private void isValidUser(){
        UserDatabase database = UserDatabase.getInstance(this.getApplicationContext());
        String email = emailIdView.getText().toString();
        String password = passwordView.getText().toString();

        User userTableData = database.userDAO().getUserByUserEmailAndPassword(email,password);
        if(userTableData != null) {
            // If credentials are valid -> show a success toast
            Toast.makeText(getApplicationContext(), "Login Successfully  ", Toast.LENGTH_SHORT).show();
            
            SessionManager  sessionManager= new SessionManager(getApplicationContext());
            String name = sessionManager.saveName(userTable.getFullName());
            String contact = sessionManager.saveContact(userTable.getContact());
            sessionManager.createSession  (name, email, contact);
            
            // After successfull login, user moves to MainActivity 
             Intent intent = new Intent(LoginActivity.this, MainActivity.class);
             startActivity(intent);
        }
        else{
            // If credentials are invalid, show a failed toast message
            Toast.makeText(getApplicationContext(), "Email Address or password are invalid ! Please Try again. ", Toast.LENGTH_SHORT).show();
        }
    }
}
