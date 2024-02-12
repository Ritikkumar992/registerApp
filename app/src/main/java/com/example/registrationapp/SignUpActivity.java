package com.example.registrationapp;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    // variable declaration to fetch ui element of the app.
    EditText userNameView, signUpEmailView, signUpPasswordView,datePicker, userContactView;
    Button signUpBtn;
    TextView loginText;
    AutoCompleteTextView companyNameView;
    Spinner designationView;
    SessionManager sessionManager;

    
    public void initialize()
    {
        userNameView = findViewById(R.id.userNameId);
        signUpEmailView = findViewById(R.id.signUpEmailId);
        signUpPasswordView = findViewById(R.id.signPasswordId);
        userContactView = findViewById(R.id.userContactId);
        signUpBtn = findViewById(R.id.signUpBtnId);
        loginText = findViewById(R.id.loginTextId);
        companyNameView = findViewById(R.id.companyNameId);
        designationView = findViewById(R.id.spinnerId);
        datePicker = findViewById(R.id.datePicker);
    }
    // Array to store the designation of the employee.
    private String DesignationData[]={
            " ","Android Developer","FullStack Developer","Devops Engineer","Tech Lead","Product Manger","React Developer"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        // calling initializeView method to get the UI element of the app.
        this.initialize();

        //fetching the details of user.
        String userName = userNameView.getText().toString();
        String userEmailId = signUpEmailView.getText().toString();
        String contact = userContactView.getText().toString();

        // When Login  button is clicked, navigate to the login activity
        loginText.setOnClickListener(view -> {
            Toast.makeText(SignUpActivity.this, "Move to login page.", Toast.LENGTH_SHORT).show();
            sessionManager.clearSession(); // session cleared.

            // move to the login page.
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        sessionManager= new SessionManager(getApplicationContext());
        sessionManager.createSession(userEmailId, userName , contact);
        
        // When Sign up Button is clicked, check user data for registration
        signUpBtn.setOnClickListener(view -> 
            isValidData();
        );
        
        //Feature_01: Spinner is implemented as a Drop Down to fetch the data.
        // adapter use spinner to populate data.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,DesignationData);
        designationView.setAdapter(arrayAdapter);

        //Feature_02: AutoComplete Text feature in company Name text field:
        String[] companyArr = {
                "Promact Infotech","Amazon","Google","Facebook","Netflix","TowerResearch","Twitter","Adobe","Apple"
        };
        ArrayAdapter<String> companyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,companyArr);
        companyNameView.setAdapter(companyAdapter);
        companyNameView.setThreshold(1);

        //Feature_03: Adding Date Picker functionalities:
        datePicker.setOnClickListener(view->{
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view1, selectedYear, selectedMonth,selectedDayOfMonth)->{
                        datePicker.setText(selectedDayOfMonth + "/" + (selectedMonth+1) + "/" + selectedYear);
                    },
                    year,
                    month,
                    dayOfMonth
            );
            datePickerDialog.show();
        });
    }
    private void isValidData(){
        String userName = userNameView.getText().toString();
        String userEmailId = signUpEmailView.getText().toString();
        String userPassword = signUpPasswordView.getText().toString();
        String contact = userContactView.getText().toString();
        String companyName = companyNameView.getText().toString();
        String designation = designationView.toString();
        String dateOfJoining = datePicker.getText().toString();


        //  edge_case: Entering a valid email ID.
        if(userName.trim().equals("")){
            Toast.makeText(SignUpActivity.this, "Please Enter Your Name ⚠️", Toast.LENGTH_SHORT).show();
            userNameView.setError("Name is Required⚠️");
        }
        else if(userEmailId.trim().equals("")){
            Toast.makeText(SignUpActivity.this, "Please enter your email Id ⚠️", Toast.LENGTH_SHORT).show();
            signUpEmailView.setError("Email is Required⚠️");
        }
        else if(!UtilityClass.isValidEmail(userEmailId)){
            Toast.makeText(SignUpActivity.this, "Please Enter a valid Email Id ⚠️", Toast.LENGTH_SHORT).show();
            signUpEmailView.setError("Valid Email is Required⚠️");
        }
        //edge_cas: Entering a valid Password.
        else if(userPassword.trim().equals("")){
            Toast.makeText(SignUpActivity.this, "Please Enter Your Password 🔒", Toast.LENGTH_SHORT).show();
            signUpPasswordView.setError("Password is Required 🔒");
        }
        else if(!UtilityClass.isValidPassword(userPassword)){
            Toast.makeText(SignUpActivity.this, "Please Enter Valid Password", Toast.LENGTH_SHORT).show();
            signUpPasswordView.setError("Min 1 UpperCase, 1 LowerCase , 1 Digit, 1 Special Character and Min 8 character required 🔒");
        }
        else if(contact.length() != 10 || contact.trim().equals("")){
            Toast.makeText(SignUpActivity.this, "Phone Number Should be min of 10 digit", Toast.LENGTH_SHORT).show();
            userContactView.setError("Correct Phone Number is Required 📱");
        }

        // edge_case: Enter the compayName.
        else if(companyName.trim().equals("")){
            Toast.makeText(SignUpActivity.this, "Please select company Name ⚠️", Toast.LENGTH_SHORT).show();
            companyNameView.setError("Company name is Required⚠️");
        }
        // edge_case : Enter the designation:
        else if(designation.trim().equals("")){
            Toast.makeText(SignUpActivity.this, "Please select your designation⚠️",Toast.LENGTH_SHORT).show();
        }
        // edge_case: Enter the dateOfJoining.
        else if(dateOfJoining.equals("")){
            Toast.makeText(SignUpActivity.this, "Please select date of joining ⚠️", Toast.LENGTH_SHORT).show();
            datePicker.setError("Joining Date is Required⚠️");
        }
        else
        {
            UserDatabase database = UserDatabase.getInstance(this);
            User ifExist = database.userDAO().ifUsernameIsTaken(userEmailId);
            
            if(ifExist==null){
                Toast.makeText(this, "User Registered!", Toast.LENGTH_SHORT).show();
                database.userDAO().insertUser(userName,userEmailId,userPassword, String.valueOf(contact));

                SessionManager  sessionManager= new SessionManager(getApplicationContext());
                User userTable = database.userDAO().getUserByUserEmailAndPassword(userEmailId,userPassword);

                String name = sessionManager.saveName(userTable.getFullName());
                String email = sessionManager.saveEmail(userTable.getUserEmail());
                String contactNo = sessionManager.saveContact(userTable.getContact());

                sessionManager.createSession(name, email, contactNo);

                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                
            }else{
                Toast.makeText(this, "User Already Exits !", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
