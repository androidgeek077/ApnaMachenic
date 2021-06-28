package com.example.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    TextView spare_text;
    TextView admin_text;
    TextView feedback_text;
    TextView mechanic_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        spare_text = findViewById(R.id.spareparts);
        spare_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DashboardActivity.this, MainSellerActivity.class);
                startActivity(it);
            }
        });

        admin_text = findViewById(R.id.adminId);
        admin_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DashboardActivity.this, AddProductActivity.class);
                startActivity(it);
            }
        });

        feedback_text = findViewById(R.id.feedback);
        feedback_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DashboardActivity.this, MainUserActivity.class);
                startActivity(it);
            }
        });

        mechanic_text = findViewById(R.id.mechanic);
        mechanic_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DashboardActivity.this, ShopDetails.class);
                startActivity(it);
            }
        });

    }

}