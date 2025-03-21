package com.codewithremo.myapplication.SplashScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codewithremo.myapplication.GetStarted;
import com.codewithremo.myapplication.R;

public class SplashScreen1 extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen1);

//        getSupportActionBar().hide();

        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(500);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(SplashScreen1.this, GetStarted.class);
                    startActivity(intent);

                    finish();
                }
            }

        };
        thread.start();


    }


}
