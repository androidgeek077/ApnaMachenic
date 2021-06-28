package com.example.splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    
    Button btnNewCustomer;
    Button signIN;
    EditText username;
    EditText password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnNewCustomer = findViewById(R.id.btnNewCustomer);
        password = findViewById(R.id.password);
        username = findViewById(R.id.username);
        signIN = findViewById(R.id.signIN);

        mAuth = FirebaseAuth.getInstance();

        btnNewCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , Customer_Signup.class));
            }
        });

        signIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = username.getText().toString();
                String mPassword = password.getText().toString();

                if (mEmail.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter Email", Toast.LENGTH_SHORT).show();
                } else if (mPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter Password", Toast.LENGTH_SHORT).show();
                }else {

                    mAuth.signInWithEmailAndPassword(mEmail, mPassword).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    //    progressbar GONE
                                    //login_progress.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Login.this, DashboardActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {

                                        //    progressbar GONE
                                      //  login_progress.setVisibility(View.GONE);
                                        Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });

                }


            }
        });
        
    }
}