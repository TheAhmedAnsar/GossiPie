package com.codewithremo.myapplication;

import android.animation.Animator;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;



import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class private_chat extends AppCompatActivity {


  Toolbar toolbar;
//    View background;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.private_chat);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        Start Work
//        background = findViewById(R.id.background);
//        if (savedInstanceState == null) {
//            background.setVisibility(View.INVISIBLE);
//
//            final ViewTreeObserver viewTreeObserver = background.getViewTreeObserver();
//
//
//            if (viewTreeObserver.isAlive()) {
//                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//
//                    @Override
//                    public void onGlobalLayout() {
//                        circularRevealActivity();
//                        background.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                    }
//
//                });
//            }


            //            End work


            if (Build.VERSION.SDK_INT >= 21) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(this.getResources().getColor(R.color.statusbarColor));
            }

            //This is used for down system navigation color

            if (Build.VERSION.SDK_INT >= 21)
                getWindow().setNavigationBarColor(getResources().getColor(R.color.PrivateDownNavigation));

        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.private_toolbar_menu,menu);

        MenuItem menuItem = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search here...");

        return super.onCreateOptionsMenu(menu);
    }

    }
//
//
//    private void circularRevealActivity() {
//        int cx = background.getRight() - getDips(44);
//        int cy = background.getBottom() - getDips(44);
//
//        float finalRadius = Math.max(background.getWidth(), background.getHeight());
//
//        Animator circularReveal = ViewAnimationUtils.createCircularReveal(
//                background,
//                cx,
//                cy,
//                0,
//                finalRadius);
//
//        circularReveal.setDuration(3000);
//        background.setVisibility(View.VISIBLE);
//        circularReveal.start();
//
//    }
//
//    private int getDips(int dps) {
//        Resources resources = getResources();
//        return (int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP,
//                dps,
//                resources.getDisplayMetrics());
//    }
//}