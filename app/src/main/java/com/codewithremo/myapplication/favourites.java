package com.codewithremo.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;

public class favourites extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        ActionBar actionBar ;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#F87A0CDF"));;
        assert actionBar != null;
        actionBar.setBackgroundDrawable(colorDrawable);

//        Setting Title Color of ActionBar

        getSupportActionBar().setTitle(Html.fromHtml("<font color =\"white\">"+ getString(R.string.favourites)));


        // This will change the color of Bcak arrow button
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

    }
}