package com.codewithremo.myapplication;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.codewithremo.myapplication.Filter_Contact.finduser;
import com.codewithremo.myapplication.PIE_Features.PieSpy;
import com.codewithremo.myapplication.PIE_Features.piebot;
import com.codewithremo.myapplication.PIE_Features.pienews;
import com.codewithremo.myapplication.activities.Main_Note_Activity;
import com.codewithremo.myapplication.adapters.User;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity {

String UserUid,UserUid1;
FirebaseUser user,user1;

    DatabaseReference reference;
    StorageReference reference1;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsers;
    String post_key = null;
    private StorageReference mStorageRef;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    private TextView txt,url;
    private DatabaseReference mDatabase;

    SwitchCompat switchCompat;

    FirebaseFirestore db;

    String[] permissions = {"android.permission.CAMERA"};

    private static final int REQUEST_IMAGE_CAPTURE = 101;

    private boolean clicked = false;


    private DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    ViewPager pager;
    TabLayout tabLayout;
    TabItem firstItem, secondItem, thirdItem;
    PageAdapter adapter;
    FloatingActionButton fab, fab1, fab2, fab3, fab4;
    ActionBarDrawerToggle actionbar;
    Toolbar toolbar;
    ImageButton button2;
    TextView create, story;
    ImageView private_button;

    Boolean isanimation;
    Animation animateHide, animateShow,rotateOpen,rotateClose , newAnimation , newAnimation1, textanimation;

    View header;




    private boolean paused = true;
    private boolean start = true;
    private boolean isAllFabsVisible = false;


    boolean isFABOpen = false;


    private void showFABMenu() {
        isFABOpen = true;
//        fab2.startAnimation(animateShow);
        fab3.show();
        ;
        fab4.show();

    }

    private void closeFABMenu() {
        isFABOpen = false;
//        fab2.startAnimation(animateHide);
        fab3.hide();
        fab4.hide();
    }

    private void animateFab(int position) {
        switch (position) {
            case 0:
                fab.show();
                fab1.hide();

                fab2.hide();
                fab3.hide();
                fab4.hide();

                create.setVisibility(View.GONE);
                story.setVisibility(View.GONE);

                break;

            case 1:
                fab1.show();
                fab.hide();
                fab2.hide();
                fab3.hide();
                fab4.hide();
                create.setVisibility(View.GONE);
                story.setVisibility(View.GONE);

                break;

            case 2:
                fab2.show();
                fab4.hide();
                fab3.hide();
                fab.hide();
                fab1.hide();
                create.setVisibility(View.GONE);
                story.setVisibility(View.GONE);
                break;

        }
    }
// This code is used to make search bar working
//    @SuppressLint("ResourceType")
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.id.menu,menu);
//        MenuItem menuItem = menu.findItem(R.id.search);
//        SearchView searchView = (SearchView) menuItem.getActionView();
//        searchView.setQueryHint("Search here");
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {





        //Apply for required condition

//        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
//
//            //If night mode is ON then
//            setTheme(R.style.Theme_Dark);
//        }
//        else {
//            //If night mode is OFF then
//
//            setTheme(R.style.Theme_Light);
//
//        }



        super.onCreate(savedInstanceState);










        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.floatingActionButton);
        fab1 = findViewById(R.id.floatingActionButton1);
        fab2 = findViewById(R.id.floatingActionButton2);
        fab3 = findViewById(R.id.createfab);
        fab4 = findViewById(R.id.add_person_fab);
        switchCompat = findViewById(R.id.darkbutton);
        create = findViewById(R.id.create);
        private_button=findViewById(R.id.private_mode);
        story = findViewById(R.id.story);

        Context context = getApplicationContext();





        //This is used for down system navigation color

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.DownNavigationColor));



        Animation inAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        Animation outAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout);
Animation fade = AnimationUtils.loadAnimation(getApplicationContext() , R.anim.fade_anim);

        animateShow = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.show_buton);
        animateHide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hide_button);
        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        newAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.new_animation);
        newAnimation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.new_animation2);
        textanimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.text);

        isanimation = true;


        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    //When dark button will be pressed
                    //Switch to night mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else {
                    //When dark button is pressed again
                    //Switch to light mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });



        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isAllFabsVisible) {
                    fab3.show();
                    fab4.show();

                    fab3.startAnimation(newAnimation);
                    fab4.startAnimation(newAnimation);
                   create.startAnimation(newAnimation);
                    story.startAnimation(newAnimation);
                    fab2.startAnimation(animateShow);

                    create.setVisibility(View.VISIBLE);
                    story.setVisibility(View.VISIBLE);
                    isAllFabsVisible = true;
                } else {

                    fab3.hide();
                    fab4.hide();
                    fab2.startAnimation(animateHide);
fab3.startAnimation(newAnimation1);
fab4.startAnimation(newAnimation1);
create.startAnimation(textanimation);
story.startAnimation(textanimation);
                    create.setVisibility(View.GONE);
                    story.setVisibility(View.GONE);
                    isAllFabsVisible = false;

                }


                fab3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(permissions, 20);
                        }

                    }
                });


            }

        });


        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, finduser.class);
                startActivity(intent);
            }
        });




        private_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent9 = new Intent(MainActivity.this, private_chat.class);
                startActivity(intent9);
            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.whitecolor));
        drawerLayout = findViewById(R.id.drawer);


        navigationView = findViewById(R.id.nav_view);

        drawerLayout.closeDrawers();

        header = navigationView.getHeaderView(0);


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("user");
        UserUid = user.getUid();
        TextView name = (TextView) header.findViewById(R.id.textView);
//        name.setText("Remo");

        ImageView imageagain = (ImageView) header.findViewById(R.id.imageView);

//        reference1 = Storage.getReference().child("images/").child(user.getUid());




        reference.child(UserUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);
                if (userprofile !=null)
                {
                    String fullname = userprofile.getName();
                    name.setText(fullname);

                    Glide.with(getApplicationContext()).load(reference1).into(imageagain);

                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });














        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {



            @Override

            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();


                switch (item.getItemId()) {

//                    case R.id.extra_features:
//                        navigationView.getMenu().setGroupVisible(R.id.features,true);
//                        navigationView.getMenu().setGroupVisible(R.id.chat_privacy,false);
//                        return true;
//
//                    case R.id.secure_chat:
//                        navigationView.getMenu().setGroupVisible(R.id.chat_privacy,true);
//                        navigationView.getMenu().setGroupVisible(R.id.features,false);
//                        return true;

                    case R.id.call_history:

                        Intent intent2 = new Intent(MainActivity.this, callhistory.class);
                       startActivity(intent2);
                        return true;



                    case R.id.menuItem2:
                        Intent intent4 = new Intent(MainActivity.this, Main_Note_Activity.class);
                        startActivity(intent4);
                        return true;

                    case R.id.menuItem3:
                        Intent intent5 = new Intent(MainActivity.this, pienews.class);
                        startActivity(intent5);
                        return true;
                    case R.id.check_fake:
                        Intent intent6 = new Intent(MainActivity.this , PieSpy.class);
                        startActivity(intent6);
                        return true;


                    case R.id.menuItem5:
                        Intent intent7 = new Intent(MainActivity.this, piebot.class);
                        startActivity(intent7);
                        return true;

                    case R.id.menuItem6:
                        Intent intent8 = new Intent(MainActivity.this, ContactUs.class);
                        startActivity(intent8);
                        return true;









//
//                        int icon;
//                        if (paused) {
////
//                            paused = false;
//
//                            icon = R.drawable.ic_baseline_wb_sunny_24;
//                            button.startAnimation(inAnimation);
////                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//
//                            Toast.makeText(context, "Turn on", Toast.LENGTH_SHORT).show();
//
//                        } else {
//
//                            paused = true;
//
//                            icon = R.drawable.ic_baseline_nights_stay_24;
//                            button.startAnimation(inAnimation);
//                            Toast.makeText(context, "Turn off", Toast.LENGTH_SHORT).show();
//
////                    button.startAnimation(outAnimation);
//
//                        }
//                        button.setImageDrawable(
//                                ContextCompat.getDrawable(getApplicationContext(), icon));


                }

                //    This helps on Back button bug of navmenu

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            }

        });


        pager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabLayout);
        firstItem = findViewById(R.id.firstItem);
        secondItem = findViewById(R.id.secondItem);
        thirdItem = findViewById(R.id.thirditem);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();


        adapter = new PageAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, tabLayout.getTabCount());
        pager.setAdapter(adapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());

                animateFab(tab.getPosition());


//
//                if (tabLayout.getSelectedTabPosition() == 0) {
//                    Toast.makeText(context, "Hii", Toast.LENGTH_SHORT).show();
//
//
//                }
////
//                if (tabLayout.getSelectedTabPosition() == 2) {
//                    isAllFabsVisible = false;
////                    fab2.startAnimation(animateHide);
//                }

            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                isAllFabsVisible = false;
fab2.clearAnimation();
fab3.clearAnimation();
fab4.clearAnimation();
create.clearAnimation();
story.clearAnimation();

            }


            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }


        });

        tabLayout.getTabAt(1).select();


//This will work for sliding of a tab
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//upper portion will work for sliding a tab


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {


            }
//


            @Override
            public void onPageScrollStateChanged(int state) {


            }
//


        });

        getPermission();
    }



    public void setup() {
        // [START get_firestore_instance]
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // [END get_firestore_instance]

        // [START set_firestore_settings]
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        // [END set_firestore_settings]
    }


    public void setupCacheSize() {
        // [START fs_setup_cache]
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();
        db.setFirestoreSettings(settings);
        // [END fs_setup_cache]
    }


    private void getPermission() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS , Manifest.permission.READ_CONTACTS} , 1);

        }
    }

//    @Override
//    public void recreate() {
//        finish();
//        overridePendingTransition(R.anim.hide_button,
//                R.anim.show_buton);
//        startActivity(getIntent());
//
//    }

    


//    public void LoadNavMenu(int iMenu){
//        navigationView.getMenu().clear();
//        navigationView.inflateMenu(iMenu);
//        navigationView.getMenu().setGroupVisible(R.id.features,false);
//        navigationView.getMenu().setGroupVisible(R.id.chat_privacy,false);
//    }
//
//    private void onAddButtonClicked() {
//        setAnimation(clicked);
//        if(!clicked) clicked=true; else clicked=false;
//    }
//
//    private void setAnimation(boolean clicked) {
//        if(!clicked){
//            fab2.startAnimation(rotateOpen);
//        }
//        else {
//            fab2.startAnimation(rotateClose);
//        }
//    }



// Camera code Down there

//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 20) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Intent takepictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if (takepictureIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivityForResult(takepictureIntent, REQUEST_IMAGE_CAPTURE);
//                }
//                }
//            } else {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    requestPermissions(permissions, 1);
//                }
//            }
//
//
//        }



//    @Override
//    public void onBackPressed() {
//
//
//

//    SearchView searchView = (SearchView) sItem.getActionView();
//


    // Menu Options Selection



//    This helps on Back button bug of navmenu
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){

            drawerLayout.closeDrawer(GravityCompat.START);
        }
//        super.onBackPressed();
    }

}
