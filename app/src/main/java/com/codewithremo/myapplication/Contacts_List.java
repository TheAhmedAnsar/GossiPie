package com.codewithremo.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class Contacts_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        // Display color in actionBar

        ActionBar actionBar ;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#F87A0CDF"));
        actionBar.setBackgroundDrawable(colorDrawable);
//        actionBar.hide();

        getSupportActionBar().setTitle(Html.fromHtml("<font color =\"white\">"+ getString(R.string.contacts)));

        // This will change the color of Back arrow button
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

    }



    // Displaying Contacts Menu option in contacts list
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contacts , menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.invite:
                Toast.makeText(this, "We will invite them later", Toast.LENGTH_SHORT).show();
                break;
            case R.id.refresh:
                Toast.makeText(this, "Refreshing Contacts", Toast.LENGTH_SHORT).show();
                break;


            // We will display here a circle rotating when contats display here
        }

        return super.onOptionsItemSelected(item);
    }
}