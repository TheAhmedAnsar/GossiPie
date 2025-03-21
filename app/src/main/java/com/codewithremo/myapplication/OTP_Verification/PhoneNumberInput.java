package com.codewithremo.myapplication.OTP_Verification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.codewithremo.myapplication.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class PhoneNumberInput extends AppCompatActivity {

    EditText menternumber;
    Button mgetopbutton;
//    CountryCodePicker mcountrycodepicker;
    String countrycode;

    ProgressBar mprogressbar1;

    String codesent, phonenumber, number;

    FirebaseAuth firebaseAuth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_input);

        getSupportActionBar().hide();


//        mcountrycodepicker = findViewById(R.id.countrycodepicker);
        mgetopbutton = findViewById(R.id.verifybutton0);
        menternumber = findViewById(R.id.input_number);
        mprogressbar1 = findViewById(R.id.progressbar1);


//        countrycode = mcountrycodepicker.getSelectedCountryCodeWithPlus();
//
//        mcountrycodepicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
//            @Override
//            public void onCountrySelected() {
//                countrycode = mcountrycodepicker.getSelectedCountryCodeWithPlus();
//            }
//        });
//    }
//}

//
        mgetopbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number;
//                number = menternumber.getText().toString();
//                if (number.isEmpty()){
//                    Toast.makeText(PhoneNumberInput.this, "Wrong", Toast.LENGTH_SHORT).show();
//
//                }
//                else if (number.length()<10){
//
//                    Toast.makeText(PhoneNumberInput.this, "Wrong", Toast.LENGTH_SHORT).show();
//                }
//                else{
//
//
//                        mprogressbar1.setVisibility(View.VISIBLE);
//                        mgetopbutton.setVisibility(View.INVISIBLE);
//
//                        phonenumber = countrycode+number;
//                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
//                            .setPhoneNumber(phonenumber)
//                            .setTimeout(60L, TimeUnit.SECONDS)
//                            .setActivity(PhoneNumberInput.this)
//                            .setCallbacks(mCallbacks)
//                            .build();
//
//                    PhoneAuthProvider.verifyPhoneNumber(options);
//
//                }


        //                String number = countrycode+menternumber.getText().toString();
//                phonenumber = countrycode+number;
//
//                if (!menternumber.isEmpty()) {
                if (!menternumber.getText().toString().trim().isEmpty()) {
                    if (menternumber.getText().toString().trim().length() ==10) {

//
                        mprogressbar1.setVisibility(View.VISIBLE);
                        mgetopbutton.setVisibility(View.INVISIBLE);
//                        phonenumber = mcountrycodepicker+menternumber;
//
//
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(

                                "+91"+menternumber.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                PhoneNumberInput.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        mprogressbar1.setVisibility(View.GONE);
                                        mgetopbutton.setVisibility(View.VISIBLE);

                                    }
//
                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        mprogressbar1.setVisibility(View.GONE);
                                        mgetopbutton.setVisibility(View.VISIBLE);
//                                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getApplicationContext(), "Faild to fetch OTP", Toast.LENGTH_SHORT).show();

//
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                                        super.onCodeSent(s, forceResendingToken);
                                        mprogressbar1.setVisibility(View.GONE);
                                        mgetopbutton.setVisibility(View.VISIBLE);
                                        codesent = s;
                                        Intent intent = new Intent(getApplicationContext(), OTPVerification.class);
                                        intent.putExtra("mobile", menternumber.getText().toString());
                                        intent.putExtra("otp",s);
                                        startActivity(intent);
                                    }

                                }
                        );
                        //    Intent intent = new Intent(getApplicationContext(),OTPVerification.class);
                      //  intent.putExtra("mobile",menternumber.getText().toString());
                       // startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Please enter a valid number", Toast.LENGTH_SHORT).show();
                    }
                }  else {
                    Toast.makeText(getApplicationContext(), "Please enter your phone number", Toast.LENGTH_SHORT).show();


                }

//
        }
    });
//        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            @Override
//            public void onVerificationCompleted(@NonNull  PhoneAuthCredential phoneAuthCredential) {
//
//            }
//
//            @Override
//            public void onVerificationFailed(@NonNull  FirebaseException e) {
//                mprogressbar1.setVisibility(View.GONE);
//                                        mgetopbutton.setVisibility(View.VISIBLE);
////                                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                                        Toast.makeText(getApplicationContext(), "hiiii", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onCodeSent(@NonNull  String s, @NonNull  PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                super.onCodeSent(s, forceResendingToken);
//                mprogressbar1.setVisibility(View.GONE);
//                                        mgetopbutton.setVisibility(View.VISIBLE);
//                                        codesent = s;
//                                        Intent intent = new Intent(getApplicationContext(), OTPVerification.class);
//                                        intent.putExtra("mobile", menternumber.getText().toString());
//                                        intent.putExtra("otp", codesent);
//                                        startActivity(intent);
//            }
//        };
//            Intent intent = new Intent(getApplicationContext(),OTPVerification.class);
//                        intent.putExtra("mobile",menternumber.getText().toString());
//                        startActivity(intent);
//
//}
//}
    }


    // This will check if current user is already logged in or not so it can transfer the uer to the main page...

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (FirebaseAuth.getInstance().getCurrentUser()!=null)
//        {
//
//            Intent intent = new Intent(GetStarted.this , MainActivity.class);
//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//startActivity(intent);
//        }
//    }
}