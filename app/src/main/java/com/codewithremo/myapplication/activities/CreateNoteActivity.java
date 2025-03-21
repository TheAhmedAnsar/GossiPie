package com.codewithremo.myapplication.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.codewithremo.myapplication.R;
import com.codewithremo.myapplication.database.NotesDatabase;
import com.codewithremo.myapplication.entities.Note;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity {
private EditText InputNoteTitle , InputNotesSubtitle , inputNoteText;
private TextView textDateTime;
private  String selectedNoteColor;
private  View viewSubtitleIndicator;


    Animation animateHide, animateShow;

private AlertDialog dialogDelete;



private  Note alreadyAvailableNote;


ImageView ButtonAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

//getSupportActionBar().hide();

        ImageView imageback = findViewById(R.id.imageback);
        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        InputNoteTitle = findViewById(R.id.inputnotetitle);

        InputNotesSubtitle = findViewById(R.id.inputnote);
        inputNoteText = findViewById(R.id.inputnote);
        textDateTime = findViewById(R.id.textdatetime);
        viewSubtitleIndicator = findViewById(R.id.viewsubtitleindicator);




        animateShow = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.note_show_buton);
        animateHide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.note_hide_button);

        ButtonAnimation = findViewById(R.id.textmiscellaneous);




        textDateTime.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
                .format(new Date())
        );


        ImageView imageSave= findViewById(R.id.imagesave);
        imageSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }

        });

        selectedNoteColor = "#333333";


        if (getIntent().getBooleanExtra("isViewOrUpdate", false)){
            alreadyAvailableNote = (Note) getIntent().getSerializableExtra("note");
            setVieworUpdate();
        }
//
//        findViewById(R.id.imageRemoveimage).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                imageNote.setImageBitmap(null);
//                imageNote.setVisibility(View.GONE);
//                findViewById(R.id.imageRemoveimage).setVisibility(View.GONE);
//                selectImagepath = "";
//            }
//        });
        initMiscelleaneous();
        setSubtitleindicator();


    }




    private  void  setVieworUpdate(){
        InputNoteTitle.setText(alreadyAvailableNote.getTitle());
        InputNotesSubtitle.setText(alreadyAvailableNote.getSubtitle());
        inputNoteText.setText(alreadyAvailableNote.getNoteText());
        textDateTime.setText(alreadyAvailableNote.getDatetime());

//        if (alreadyAvailableNote.getImagePath() !=  null && !alreadyAvailableNote.getImagePath().trim().isEmpty())
//        {
//            imageNote.setImageBitmap(BitmapFactory.decodeFile(alreadyAvailableNote.getImagePath()));
//            imageNote.setVisibility(View.VISIBLE);
////            findViewById(R.id.imageRemoveimage).setVisibility(View.VISIBLE);
//            selectImagepath = alreadyAvailableNote.getImagePath();
//
//
//        }


    }

    private void saveNote()

    {

        if (InputNoteTitle.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Note title can't be empty", Toast.LENGTH_SHORT).show();
            return;

        } else if (InputNotesSubtitle.getText().toString().trim().isEmpty() && inputNoteText.getText().toString().trim().isEmpty()) {

            Toast.makeText(this, "Note can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        final Note note = new Note();
        note.setTitle(InputNoteTitle.getText().toString());
        note.setSubtitle((InputNotesSubtitle.getText().toString()));
        note.setNoteText(inputNoteText.getText().toString());
        note.setDatetime(textDateTime.getText().toString());
        note.setColor(selectedNoteColor);

//        note.setImagePath(selectImagepath);



        if (alreadyAvailableNote != null){
            note.setId(alreadyAvailableNote.getId());
        }

@SuppressLint("StaticFieldLeak")
        class SaveNoteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                NotesDatabase.getNotesDatabase(getApplicationContext()).noteDao().insertNote(note);


                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        new SaveNoteTask().execute();




    }




    private void initMiscelleaneous(){
        final LinearLayout layoutMiscellaneaous = findViewById(R.id.layoutmiscellaneous);

        final BottomSheetBehavior bottomSheetBehavior  = BottomSheetBehavior.from(layoutMiscellaneaous);

        layoutMiscellaneaous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
ButtonAnimation.startAnimation(animateShow);

                }
                else
                {

                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    ButtonAnimation.startAnimation(animateHide);


                }
//                if (alreadyAvailableNote != null)
//                {
//                    layoutMiscellaneaous.findViewById(R.id.layoutDeleteNote).setVisibility(View.VISIBLE);
//                    layoutMiscellaneaous.findViewById(R.id.layoutDeleteNote).set
//                }
            }
        });

        final ImageView imageColor1 = layoutMiscellaneaous.findViewById(R.id.imageView1);
        final ImageView imageColor2 = layoutMiscellaneaous.findViewById(R.id.imageView2);
        final ImageView imageColor3 = layoutMiscellaneaous.findViewById(R.id.imageView3);
        final ImageView imageColor4 = layoutMiscellaneaous.findViewById(R.id.imageView4);
        final ImageView imageColor5 = layoutMiscellaneaous.findViewById(R.id.imageView5);
//        final ImageView imageColor6 = layoutMiscellaneaous.findViewById(R.id.imageView7);


        layoutMiscellaneaous.findViewById(R.id.viewColor1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedNoteColor = "#333333";
                imageColor1.setImageResource(R.drawable.ic_done);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);
                setSubtitleindicator();
            }
        });



        layoutMiscellaneaous.findViewById(R.id.viewColor2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedNoteColor = "#FFEB3B";

                imageColor1.setImageResource(0);
                imageColor2.setImageResource(R.drawable.ic_done);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);
                setSubtitleindicator();
            }
        });



        layoutMiscellaneaous.findViewById(R.id.viewColor3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedNoteColor = "#D8C0DD";

                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(R.drawable.ic_done);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);


                setSubtitleindicator();
            }
        });


        layoutMiscellaneaous.findViewById(R.id.viewColor4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedNoteColor = "#94DCD3";

                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(R.drawable.ic_done);
                imageColor5.setImageResource(0);


                setSubtitleindicator();
            }
        });



        layoutMiscellaneaous.findViewById(R.id.viewColor5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedNoteColor = "#D15151";

                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(R.drawable.ic_done);


                setSubtitleindicator();
            }
        });

        if (alreadyAvailableNote != null && alreadyAvailableNote.getColor() != null && !alreadyAvailableNote.getColor().trim().isEmpty())
        {
            switch (alreadyAvailableNote.getColor())
            {
                case"#FFEB3B":
                    layoutMiscellaneaous.findViewById(R.id.viewColor2).performClick();
                    break;

                    case "#D8C0DD":
                    layoutMiscellaneaous.findViewById(R.id.viewColor3).performClick();
                    break;

                    case "#94DCD3":
                    layoutMiscellaneaous.findViewById(R.id.viewColor4).performClick();
                    break;

                    case "#D15151":
                    layoutMiscellaneaous.findViewById(R.id.viewColor5).performClick();
                    break;



            }
        }

//        layoutMiscellaneaous.findViewById(R.id.layoutAddImage).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                if (ContextCompat.checkSelfPermission(
//                        getApplicationContext() , Manifest.permission.READ_EXTERNAL_STORAGE
//                )!= PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(
//                            CreateNoteActivity.this,
//                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                            REQUEST_CODE_STORAGE_PERMISSION
//                    );
//                }
//                else {
//                    selectImage();
//                }
//            }
//        });

        if (alreadyAvailableNote != null)
        {
            layoutMiscellaneaous.findViewById(R.id.layoutDeleteNote).setVisibility(View.VISIBLE);
            layoutMiscellaneaous.findViewById(R.id.layoutDeleteNote).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    showDeleteDialog();

                }
            });
        }
//
    }

    private  void showDeleteDialog(){


        if (dialogDelete == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(CreateNoteActivity.this);
            View view = LayoutInflater.from(this).inflate(
                    R.layout.layout_delete_note,
                    (ViewGroup) findViewById(R.id.layoutDeleteNoteContainer)
            );
            builder
                    .setView(view);
            dialogDelete = builder.create();
            if (dialogDelete.getWindow()!= null){
                dialogDelete.getWindow().setBackgroundDrawable(new ColorDrawable(0));

            }
            view.findViewById(R.id.textDeleteNote).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    @SuppressLint("StaticFieldLeak")
                    class DeleteNoteTask extends  AsyncTask<Void , Void , Void>{
                        @Override
                        protected Void doInBackground(Void... voids) {
                            NotesDatabase.getNotesDatabase(getApplicationContext()).noteDao()
                                    .deleteNote(alreadyAvailableNote);

                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void unused) {
                            super.onPostExecute(unused);
                            Intent intent = new Intent();
                            intent.putExtra("isNoteDeleted" , true);
                            setResult(RESULT_OK , intent);
                            finish();
                        }
                    }

                    new DeleteNoteTask().execute();

                }
            });

            view.findViewById(R.id.textcancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogDelete.dismiss();
                }
            });
        }

        dialogDelete.show();
    }

    private void  setSubtitleindicator(){


        GradientDrawable gradientDrawable = (GradientDrawable) viewSubtitleIndicator.getBackground();
        gradientDrawable.setColor(Color.parseColor(selectedNoteColor));

    }

}