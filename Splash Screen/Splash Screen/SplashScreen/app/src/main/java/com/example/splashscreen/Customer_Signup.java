package com.example.splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.splashscreen.Models.CustomerSignUpModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Customer_Signup extends AppCompatActivity {

    EditText username;
    EditText email;
    EditText password;
    EditText edtConfirmTxtPassword;
    EditText number;
    EditText guardianContactNumber;
    Button btnSignup;
ProgressBar signUp_progress;
    //private DatabaseReference databaseReference;
   // private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__signup);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        edtConfirmTxtPassword = findViewById(R.id.edtConfirmTxtPassword);
        number = findViewById(R.id.number);
        guardianContactNumber = findViewById(R.id.guardianContactNumber);
        btnSignup = findViewById(R.id.btnSignup);
        signUp_progress = findViewById(R.id.signUp_progress);

        mAuth = FirebaseAuth.getInstance();
      //  firebaseDatabase = FirebaseDatabase.getInstance();
       // databaseReference = firebaseDatabase.getReference("UserData");
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mUserName = username.getText().toString();
                String mEmail = email.getText().toString();
                String mPassword = password.getText().toString();
                String mNumber = number.getText().toString();
                String mGuardianContactNumber = guardianContactNumber.getText().toString();
                if (mUserName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter UserName", Toast.LENGTH_SHORT).show();
                } else if (mEmail.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter Email", Toast.LENGTH_SHORT).show();
                } else if (mPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter Password", Toast.LENGTH_SHORT).show();
                } else if (mNumber.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter Contact Number", Toast.LENGTH_SHORT).show();
                } else if (mGuardianContactNumber.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter Guardian Contact Number", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener
                            (new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {

                                        CustomerSignUpModel customerSignUpModel = new CustomerSignUpModel(mUserName, mEmail, mPassword , mNumber , mGuardianContactNumber);

                                        FirebaseDatabase.getInstance("https://apna-mechanic-cd1cf-default-rtdb.firebaseio.com/").getReference("UserData")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(customerSignUpModel).
                                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            Toast.makeText(Customer_Signup.this, "Successful Registered", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(Customer_Signup.this, Login.class);
                                                            startActivity(intent);
                                                            finish();
                                                        }else {
                                                            Toast.makeText(Customer_Signup.this, "task:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }

                                                        //    progressbar GONE
                                                        //  signUp_progress.setVisibility(View.GONE);


                                                    }
                                                });


                                    } else {
                                        //    progressbar GONE
                                        //signUp_progress.setVisibility(View.GONE);
                                        Toast.makeText(Customer_Signup.this, "Check Email id or Password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


    }


}
