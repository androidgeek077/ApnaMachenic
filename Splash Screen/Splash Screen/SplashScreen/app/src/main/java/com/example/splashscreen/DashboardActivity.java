package com.example.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {

    TextView spare_text;
    TextView admin_text;
    TextView feedback_text;
    TextView mechanic_text;
    CardView ViewHistoryCV, bookMachenicCV, sparePartsCV, userCartCV;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        ViewHistoryCV = findViewById(R.id.ViewHistoryCV);
        auth=FirebaseAuth.getInstance();
        ViewHistoryCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DashboardActivity.this, ViewUserBooking.class);
                startActivity(it);
            }
        });

        bookMachenicCV = findViewById(R.id.bookMachenicCV);
        bookMachenicCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DashboardActivity.this, BookMechanicActivity.class);
                startActivity(it);
            }
        });
        sparePartsCV = findViewById(R.id.sparePartsCV);
        sparePartsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DashboardActivity.this, spare_parts_shop.class);
                startActivity(it);
            }
        });
        userCartCV = findViewById(R.id.userCartCV);
        userCartCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DashboardActivity.this, MainUserActivity.class);
                startActivity(it);
            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout_item) {
            auth.signOut();
            startActivity(new Intent(DashboardActivity.this, Login.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}