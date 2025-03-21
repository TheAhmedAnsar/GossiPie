package com.codewithremo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.codewithremo.myapplication.OTP_Verification.PhoneNumberInput;
import com.google.firebase.auth.FirebaseAuth;

public class GetStarted extends AppCompatActivity {
Button mstartbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_started);

//        getSupportActionBar().hide();

        mstartbutton = findViewById(R.id.startbutton);
        mstartbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetStarted.this, PhoneNumberInput.class);
                startActivity(intent);
                finish();
            }


        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null)
        {

            Intent intent = new Intent(GetStarted.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}

