package com.codewithremo.myapplication.Filter_Contact;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codewithremo.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class finduser extends AppCompatActivity {

    private RecyclerView muserList;
    private  RecyclerView.Adapter mUserListAdapter;
    private  RecyclerView.LayoutManager mUserListLayoutManager;

    ArrayList<UserObject> userList, contactList;
    String ISO ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finduser);

contactList = new ArrayList<>();
userList = new ArrayList<>();
        initializeRecyclerview();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getContactList();
        }
    }

    String[] projection = new String[] {
            ContactsContract.RawContacts._ID,
            ContactsContract.RawContacts.ACCOUNT_TYPE,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Photo.CONTACT_ID };


    String selectionFields =  ContactsContract.RawContacts.ACCOUNT_TYPE + " = ?";
    String[] selectionArgs = new String[]{"com.whatsapp"};

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getContactList(){



        String ISOPrefix = getCountryISO();
//        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI , null , null , null,null);
        Cursor phones = getContentResolver().query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                selectionFields,
                selectionArgs,

                ContactsContract.Contacts.SORT_KEY_PRIMARY + " ASC");
        while (phones.moveToNext())
        {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

phone = phone.replace(" " , "");
phone = phone.replace("-" , "");
phone = phone.replace("(" , "");
phone = phone.replace(")" , "");

if (!String.valueOf(phone.charAt(0)).equals("+"))
    phone = ISOPrefix + phone;




            UserObject mcontact = new UserObject(name , phone);
            contactList.add(mcontact);
            getuserdetails(mcontact);
        }
    }

    private void getuserdetails(UserObject mcontact) {

        DatabaseReference mUserDB = FirebaseDatabase.getInstance().getReference().child("user");
        mUserDB.keepSynced(true);


        DatabaseReference presenceRef = FirebaseDatabase.getInstance().getReference("disconnectmessage");
// Write a string when this client loses connection
        presenceRef.onDisconnect().setValue("I disconnected!");

//        DatabaseReference userLastOnlineRef = FirebaseDatabase.getInstance().getReference("users/joe/lastOnline");
        DatabaseReference userLastOnlineRef = FirebaseDatabase.getInstance().getReference().child("users");
        userLastOnlineRef.onDisconnect().setValue(ServerValue.TIMESTAMP);



        Query query = mUserDB.orderByChild("phone").equalTo(mcontact.getPhone());
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    String phone = "",
                            name = "";
                    for (DataSnapshot childSnapshot : snapshot.getChildren())
                    {
                        if (childSnapshot.child("phone").getValue()!= null)
                            phone = childSnapshot.child("phone").getValue().toString();

                        if (childSnapshot.child("name").getValue()!= null)
                            name = childSnapshot.child("name").getValue().toString();
//                        if (childSnapshot.child("name").getValue()!= null)
//                            name = childSnapshot.child("name").getValue().toString();


                        UserObject mUser = new UserObject(name , phone);
                        if (name.equals(phone))
                        {
                            for (UserObject mContactIterator : contactList){

if (mContactIterator.getPhone().equals(mUser.getPhone()))
{
    mUser.setName(mContactIterator.getName());
}
                            }
                        }

                        userList.add(mUser);
                        mUserListAdapter.notifyDataSetChanged();
                        return;

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

    }


    private String getCountryISO() {


        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        if (telephonyManager.getNetworkCountryIso() != null)
            if (!telephonyManager.getNetworkCountryIso().toString().equals(""))
                ISO = telephonyManager.getNetworkCountryIso().toString();
                return CountryToPhone.getPhone(ISO);







    }

    private void initializeRecyclerview(){
        muserList = findViewById(R.id.userList);
        muserList.setNestedScrollingEnabled(false);
        muserList.setHasFixedSize(false);
        mUserListLayoutManager = new LinearLayoutManager(getApplicationContext() , LinearLayout.VERTICAL , false);
        muserList.setLayoutManager(mUserListLayoutManager);
        mUserListAdapter = new UserListAdapter(userList);
        muserList.setAdapter(mUserListAdapter);

    }
}