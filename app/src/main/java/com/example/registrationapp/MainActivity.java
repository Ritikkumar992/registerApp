package com.example.registrationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView textViewWelcome;
    Button logOutBtn;
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewWelcome = findViewById(R.id.textViewWelcome);
        logOutBtn = findViewById(R.id.logOutBtnId);
        sessionManager = new SessionManager(getApplicationContext());


        // Check if user session exists
        String name = sessionManager.getName();
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
}