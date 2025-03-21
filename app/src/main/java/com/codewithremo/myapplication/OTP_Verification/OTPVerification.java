package com.codewithremo.myapplication.OTP_Verification;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.codewithremo.myapplication.ProfileReUpdate;
import com.codewithremo.myapplication.R;
import com.codewithremo.myapplication.SetupProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OTPVerification extends AppCompatActivity {
    EditText inputnumber1, inputnumber2, inputnumber3, inputnumber4, inputnumber5, inputnumber6;
    Button mverifybutton;
    TextView mresendcode;
    String enteredotp;

    FirebaseAuth firebaseAuth;
    ProgressBar mprogressbar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        getSupportActionBar().hide();


        inputnumber1 = findViewById(R.id.input_otp);
        inputnumber2 = findViewById(R.id.input_otp2);
        inputnumber3 = findViewById(R.id.input_otp3);
        inputnumber4 = findViewById(R.id.input_otp4);
        inputnumber5 = findViewById(R.id.input_otp5);
        inputnumber6 = findViewById(R.id.input_otp6);
        mverifybutton = findViewById(R.id.verify_button);
        mprogressbar2 = findViewById(R.id.progressbar2);
        mresendcode = findViewById(R.id.resend_code);

        firebaseAuth = FirebaseAuth.getInstance();

        TextView textView =(TextView) findViewById(R.id.number_show);
        textView.setText(String.format(
                "+91-%s", getIntent().getStringExtra("mobile")
        ));

        enteredotp = getIntent().getStringExtra("otp");

        mverifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputnumber1.getText().toString().trim().isEmpty() && !inputnumber2.getText().toString().trim().isEmpty()) {
                    String enteredcode = inputnumber1.getText().toString() +
                            inputnumber2.getText().toString() +
                            inputnumber3.getText().toString() +
                            inputnumber4.getText().toString() +
                            inputnumber5.getText().toString() +
                            inputnumber6.getText().toString();
                    if (enteredotp != null) {
                        mprogressbar2.setVisibility(View.VISIBLE);
                        mverifybutton.setVisibility(View.INVISIBLE);


                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                enteredotp, enteredcode
                        );
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        mprogressbar2.setVisibility(View.GONE);
                                        mverifybutton.setVisibility(View.VISIBLE);
                                        if (task.isSuccessful()) {
                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            if (user != null){

                                                final DatabaseReference mUserDb = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());
                                                mUserDb.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                                                        if (!snapshot.exists())
                                                        {
                                                            Map<String, Object> usermap = new HashMap<>();
                                                            usermap.put("phone" , user.getPhoneNumber());
//                                                            usermap.put("name" , user.getDisplayName());

                                                            mUserDb.updateChildren(usermap);

                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                                    }
                                                });
                                            }
                                            Intent intent = new Intent(getApplicationContext(), SetupProfileActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(OTPVerification.this, "Enter the correct code", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(OTPVerification.this, "Please check your internet connectivity", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(OTPVerification.this, "Verify code", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OTPVerification.this, "Please enter the code correctly", Toast.LENGTH_SHORT).show();
                }
            }
        });

        numberotpmove();


        findViewById(R.id.resend_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        OTPVerification.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(OTPVerification.this, e.getMessage(), Toast.LENGTH_SHORT).show();


                            }

                            @Override
                            public void onCodeSent(@NonNull String newotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(newotp, forceResendingToken);
                                enteredotp = newotp;

                                Toast.makeText(OTPVerification.this,"Verification code sent successfully", Toast.LENGTH_SHORT).show();

                            }
                        }
                );

            }
        });

    }

    private void numberotpmove() {
        inputnumber1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputnumber2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputnumber2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputnumber3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputnumber3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputnumber4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputnumber4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputnumber5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputnumber5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputnumber6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputnumber6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}