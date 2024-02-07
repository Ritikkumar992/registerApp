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

        //************************  LOGIN CHECK *************************************************//
        AppDatabase database = AppDatabase.getInstance(this.getApplicationContext());
        // When Login Button button is clicked, check user credentials

        loginBtn.setOnClickListener(view ->{

            String email = emailIdView.getText().toString();
            String password = passwordView.getText().toString();

            User userTable = database.userDAO().getUserByUserEmailAndPassword(email,password);


            if(userTable!=null) {
                Toast.makeText(getApplicationContext(), "Login Successfully  ", Toast.LENGTH_SHORT).show();
                // Perform login process (authentication)
                // On successful login, fetch user details
                authenticateUser(email, password);

                goToMainActivity();
            }
            else{
                Toast.makeText(getApplicationContext(), "Email Address or password are invalid ! Please Try again. ", Toast.LENGTH_SHORT).show();
            }
        });

        //STEP_02: OnClickListener is added to forget button to send the password recovery link to tha mail.
        forgetBtn.setOnClickListener(view->{
            Toast.makeText(LoginActivity.this, "Recovery Link is sent to the registerd Email", Toast.LENGTH_SHORT).show();
            // Api call to send the recovery link to the mail.
        });

        // STEP_03: Is the user is new then => Register or signup page is opened
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent object is created of this class Context() to SignUpActivity class
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
    private void authenticateUser(final String email, final String password) {
        // Dummy authentication process
        // Replace with your actual authentication logic
        // For demonstration purpose, assume authentication is successful

        // Once authenticated, fetch user details from Room Database
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Fetch user details from Room Database
                User user = database.userDAO().getUserEmail(email);

                if (user != null) {

                    // Save user details in SessionManager
                    sessionManager.saveName(user.getFullName());
                    sessionManager.saveEmail(email);

                    // Proceed to next activity
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish(); // Close this activity
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "User details not found", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
    public void goToMainActivity()
    {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
