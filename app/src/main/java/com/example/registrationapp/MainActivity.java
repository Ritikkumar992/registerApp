package com.example.registrationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView textViewWelcome, editText;
    Button logOutBtn, updateNameBtn;
    private SessionManager sessionManager;
    EditText newNameTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewWelcome = findViewById(R.id.textViewWelcome);
        logOutBtn = findViewById(R.id.logOutBtnId);
        editText = findViewById(R.id.editTextViewId);
        newNameTxt = findViewById(R.id.newNameId);
        updateNameBtn = findViewById(R.id.updateBtnId);

        sessionManager = new SessionManager(getApplicationContext());


        // Check if user session exists
        String name = sessionManager.getSessionDetails("key_session_name");
        String email = sessionManager.getSessionDetails("key_session_email");
        String number = sessionManager.getSessionDetails("key_session_contact");

        if (name != null) {
            // User is logged in, welcome the user
            textViewWelcome.setText("Welcome, " + name);
        } else {
            // User is not logged in, redirect to login activity or perform other actions
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // close this activity so user cannot go back
            return;
        }

        editText.setOnClickListener(view ->{
             newNameTxt.setVisibility(View.VISIBLE);
             updateNameBtn.setVisibility(View.VISIBLE);
//            showUpdatedName();


        });

        updateNameBtn.setOnClickListener(view -> {
            updateUserName(newNameTxt.getText().toString());
            newNameTxt.setVisibility(View.INVISIBLE);
            updateNameBtn.setVisibility(View.INVISIBLE);
        });


        // Logout button click listener
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear session
                sessionManager.clearSession();
                Toast.makeText(MainActivity.this, "Log Out Successful", Toast.LENGTH_SHORT).show();
                // Redirect to login activity
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish(); // close this activity so user cannot go back
            }
        });
    }

    private void updateUserName(final String newName) {
        // Get the user's email from the session
        final String userEmail = sessionManager.getSessionDetails("key_session_email");
        AppDatabase database = AppDatabase.getInstance(this);

        // Check if the user's email is available
        if (userEmail != null) {
            // Execute the update query in a background thread
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Update the user's name in the database
//
//                    sessionManager.updateNameSession(newName);
                    database.userDAO().updateUserName(newName , userEmail);

                    // Update the displayed name in the UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Update the welcome message with the new name
                            textViewWelcome.setText("Welcome, " + newName);

                            // Show a message indicating successful update
                            Toast.makeText(MainActivity.this, "Name updated successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).start();
        } else {
            // Handle case where user email is not available (should not occur in a logged-in state)
            Toast.makeText(MainActivity.this, "Error: User email not found", Toast.LENGTH_SHORT).show();
        }
    }


}
