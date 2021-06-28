package com.example.splashscreen;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        Thread thread = new Thread(){

            @Override
            public void run() {
                try {

                    sleep( 1000);

                }
                catch (Exception e){

                    e.printStackTrace();

                }

                finally {

                    Intent intent = new Intent(Splash_Screen.this  ,Login.class );
                    startActivity(intent);

                }




            }
        };thread.start();
    }
}