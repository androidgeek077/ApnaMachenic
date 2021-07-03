package com.example.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button btnNewCustomer;
    Button signIN;
    EditText username;
    EditText password;
    FirebaseAuth mAuth;
    TextView addMachenicBtn;
    private FirebaseAuth auth;
    private String userTpye="";
    DatabaseReference UserRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();

        UserRef = FirebaseDatabase.getInstance().getReference("Users");
        addMachenicBtn = findViewById(R.id.addMachenicBtn);

        addMachenicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Mechanic_Signup.class));
            }
        });
        btnNewCustomer = findViewById(R.id.btnNewCustomer);
        password = findViewById(R.id.password);
        username = findViewById(R.id.username);
        signIN = findViewById(R.id.signIN);

        mAuth = FirebaseAuth.getInstance();

        btnNewCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Customer_Signup.class));
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
                } else {

                    mAuth.signInWithEmailAndPassword(mEmail, mPassword).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    //    progressbar GONE
                                    //login_progress.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        getUserTpye();
//                                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(Login.this, DashboardActivity.class);
//                                        startActivity(intent);
//                                        finish();
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

    private String getUserTpye() {
//        mLocationList = new ArrayList<>();
//        mLongList = new ArrayList<>();
        UserRef.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    userTpye = dataSnapshot.child("usertype").getValue().toString();
                    Toast.makeText(Login.this, userTpye, Toast.LENGTH_SHORT).show();
                    if (userTpye.equals("user")) {
                        startActivity(new Intent(getBaseContext(), DashboardActivity.class));
                        finish();
                    } else if (userTpye.equals("admin")) {
                        startActivity(new Intent(getBaseContext(), AdminDashboardActivity.class));
                        finish();

                    } else {
                        Toast.makeText(Login.this, "Please login to Mechanic App", Toast.LENGTH_SHORT).show();
                    finish();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        return userTpye;
    }

}