package com.codewithremo.myapplication;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public  class ProfileReUpdate extends AppCompatActivity {
    CardView cardView;
    ImageView imageView;
    int PICK_Image=123;
    Uri imagepath;
    EditText editText;
    FirebaseAuth firebaseAuth;
    String name;

    UploadTask uploadTask;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    String ImageuriAcessToken;
    FirebaseFirestore firebaseFirestore;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_re_update);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();

        cardView = findViewById(R.id.cardImage);
        imageView = findViewById(R.id.addprofilepic);
        editText = findViewById(R.id.enterusername);
        button = findViewById(R.id.saveprofile_button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editText.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(ProfileReUpdate.this, "Enter your username", Toast.LENGTH_SHORT).show();
                } else {

                    senddatafornewuser();
                    Intent intent = new Intent(ProfileReUpdate.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            private void senddatafornewuser() {

                sendDataToRealTimeDatabase();
            }

            private void sendDataToRealTimeDatabase() {

                name = editText.getText().toString().trim();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
            }
        });


        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(ProfileReUpdate.this, "UID added Succesfully", Toast.LENGTH_SHORT).show();
                senddataToCloudSFiretore();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure( @NotNull Exception e) {
                Toast.makeText(ProfileReUpdate.this, "Uri Failed to add", Toast.LENGTH_SHORT).show();


            }
        });

    }




    private void senddataToCloudSFiretore() {

        DocumentReference documentReference = firebaseFirestore.collection("users").document(firebaseAuth.getUid());
        Map<String , Object> userdata = new HashMap<>();
        userdata.put("name" , name);
//        userdata.put("image" , ImageuriAcessToken);
        userdata.put("uid" , firebaseAuth.getUid());
        userdata.put("status" , "online");

        documentReference.set(userdata).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void avoid) {

                Toast.makeText(getApplicationContext(), "Data on Cloud firebase Stored", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
