package com.codewithremo.myapplication.PIE_Features;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codewithremo.myapplication.R;

import java.util.ArrayList;

public class PieSpy extends AppCompatActivity {

    TextView etEmail;
    TextView etSubject;
    EditText etMessage;
    TextView tvAttachment;
    String email;
    String subject;
    String message;
    Button Send;


    ImageView imageView, send;

    ImageView secondimageview;

    ImageButton button;

    ImageButton secondbutton;

    Uri imageUri;
    Uri imageUri2;


    private static final int PICK_IMAGE = 100;
    private static final int PICK_IMAGE1 = 200;


    private static final int PICK_FROM_GALLERY = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_spy);

send = findViewById(R.id.imageView);
        secondimageview = findViewById(R.id.secondview);
//        secondbutton = findViewById(R.id.secondbutton);

        imageView = findViewById(R.id.firstview);
//        button = findViewById(R.id.firstbutton);


        etEmail = findViewById(R.id.etTo);
        etSubject = findViewById(R.id.etSubject);
        etMessage = findViewById(R.id.etMessage);
//        attachment = findViewById(R.id.btAttachment);
        tvAttachment = findViewById(R.id.tvAttachment);
        Send = findViewById(R.id.btSend);
        String ones;



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();

            }
        });



        secondimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery1();




            }
        });

        Send.setEnabled(false);


        etMessage.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().trim().length()==0){
                    Send.setEnabled(false);
                } else {
                    Send.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });


        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

    }



    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);

//        imageView.setVisibility(View.VISIBLE);
//        button.setVisibility(View.GONE);
    }

    private void openGallery1() {


        Intent gallery1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

        gallery1.putExtra("return-data", true);
        startActivityForResult(gallery1 , PICK_IMAGE1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE ){

        imageUri = data.getData();
            imageView.setImageURI(imageUri);
            send.setImageURI(imageUri);
        }

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE1){
            imageUri2 = data.getData();
            secondimageview.setImageURI(imageUri2);

        }
    }

//
public void sendEmail() {
    try {
        email = etEmail.getText().toString();
        subject = etSubject.getText().toString();
        message = etMessage.getText().toString();



        Intent emailSelectorIntent = new Intent(Intent.ACTION_SENDTO);
        emailSelectorIntent.setData(Uri.parse("mailto:"));


        final Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);







        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        emailIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        emailIntent.setSelector( emailSelectorIntent );



        ArrayList<Uri> arrayList = new ArrayList<>();
        arrayList.add(imageUri);
        arrayList.add(imageUri2);


        if (imageUri != null &&  imageUri2  != null) {

emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,arrayList);
        }
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        this.startActivity(Intent.createChooser(emailIntent,null));

        if( emailIntent.resolveActivity(getPackageManager()) != null )
            startActivity(emailIntent);

    }


    catch (Throwable t) {
        Toast.makeText(this, "Request failed try again: "+ t.toString(), Toast.LENGTH_LONG).show();
    }
}

    }
