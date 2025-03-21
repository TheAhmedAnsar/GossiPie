package com.codewithremo.myapplication.Settings_part;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.codewithremo.myapplication.Notification;
import com.codewithremo.myapplication.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);


        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.statusbarColor1));
        }


        //This is used for down system navigation color

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.DownNavigationColor));


        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
//        ActionBar actionBar = getSupportActionBar();
//        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#F87A0CDF"));

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.backgroundColor)));
//        actionBar.setBackgroundDrawable(colorDrawable);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);


            Preference preference1 , preference2,preference3,preference4;


            preference1= findPreference("notification");
            preference2= findPreference("privacy");
            preference3= findPreference("appearance");
            preference4= findPreference("data");

//            getPreferenceScreen().setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//
//                Preference preference;
//
//                @Override
//                public boolean onPreferenceClick(Preference preference) {
//                    return false;
//                }
//            });

            preference1.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(getContext() , Notification.class);
                    getActivity();
                    startActivity(intent);

                    return true;
                }
            });

            preference2.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(getContext() , Privacy.class);
                    getActivity();
                    startActivity(intent);

                    return true;
                }
            });
            preference3.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(getActivity() , Appearance.class);
                    startActivity(intent);
                    return true;
                }
            });
            preference4.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(getActivity() , Data_download.class);
                    startActivity(intent);
                    return true;
                }
            });


        }
    }
}