package com.example.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splash_Screen extends AppCompatActivity {
    DatabaseReference UserRef;
    private String userTpye = "";
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        auth = FirebaseAuth.getInstance();
        UserRef = FirebaseDatabase.getInstance().getReference("Users");

        Thread thread = new Thread() {

            @Override
            public void run() {
                try {

                    sleep(1000);

                } catch (Exception e) {

                    e.printStackTrace();

                } finally {

                    if (auth.getCurrentUser() != null) {
                        getUserTpye();
                    } else {
                        Intent intent = new Intent(Splash_Screen.this, Login.class);
                        startActivity(intent);
                    }

                }


            }
        };
        thread.start();
    }

    private String getUserTpye() {
        UserRef.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    userTpye = dataSnapshot.child("usertype").getValue().toString();
                    if (userTpye.equals("user")) {
                        startActivity(new Intent(getBaseContext(), DashboardActivity.class));
                        finish();
                    } else if (userTpye.equals("admin")) {
                        startActivity(new Intent(getBaseContext(), AdminDashboardActivity.class));
                        finish();

                    } else {
                        Toast.makeText(Splash_Screen.this, "Please login from Mechanic App", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(getBaseContext(), MapsActivity.class));
//                        finish();


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