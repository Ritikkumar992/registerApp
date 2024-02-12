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
    // variable declaration
    private TextView textViewWelcome, editText;
    Button logOutBtn, updateNameBtn;
    private SessionManager sessionManager;
    EditText newNameTxt;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // UI elements
        textViewWelcome = findViewById(R.id.textViewWelcome);
        logOutBtn = findViewById(R.id.logOutBtnId);
        editText = findViewById(R.id.editTextViewId);
        newNameTxt = findViewById(R.id.newNameId);
        updateNameBtn = findViewById(R.id.updateBtnId);

        // session is created.
        sessionManager = new SessionManager(getApplicationContext());


        // Check if user session exists
        String user_name = sessionManager.getSessionDetails("key_session_name");
        String user_email = sessionManager.getSessionDetails("key_session_email");
        String user_number = sessionManager.getSessionDetails("key_session_contact");

        if (user_name != null) {
            textViewWelcome.setText("Welcome, " + name);
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        editText.setOnClickListener(view ->{
             newNameTxt.setVisibility(View.VISIBLE);
             updateNameBtn.setVisibility(View.VISIBLE);
            //showUpdatedName();
        });
        updateNameBtn.setOnClickListener(view -> {
            modifyUserName(newNameTxt.getText().toString());
            newNameTxt.setVisibility(View.INVISIBLE);
            updateNameBtn.setVisibility(View.INVISIBLE);
        });
        // Logout button is clicked.
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.clearSession(); // session is cleared.
                Toast.makeText(MainActivity.this, "Log Out Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish(); // activity closed.
            }
        });
    }
    private void modifyUserName(final String newName) {
        // Get the user's email from the session
        final String userEmail = sessionManager.getSessionDetails("key_session_email");
        UserDatabase database = UserDatabase.getInstance(this);

        // Check if the user's email is available
        if (userEmail != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //sessionManager.updateNameSession(newName);
                    database.userDAO().updateUserName(newName , userEmail);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textViewWelcome.setText("Welcome, " + newName);
                            Toast.makeText(MainActivity.this, "Name updated successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).start();
        }else{
            Toast.makeText(MainActivity.this, "Error: Email Id not found ⚠️", Toast.LENGTH_SHORT).show();
        }
    }
}
