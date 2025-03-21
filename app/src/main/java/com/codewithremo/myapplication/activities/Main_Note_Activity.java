package com.codewithremo.myapplication.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.codewithremo.myapplication.R;
import com.codewithremo.myapplication.adapters.NotesAdapter;
import com.codewithremo.myapplication.database.NotesDatabase;
import com.codewithremo.myapplication.entities.Note;
import com.codewithremo.myapplication.listeners.NotesListener;

import java.util.ArrayList;
import java.util.List;

public class Main_Note_Activity extends AppCompatActivity  implements NotesListener {

    public  final  static  int REQUEST_CODE_ADD_NOTE = 1;
    public  static  final int REQUEST_CODE_UPDATE_NOTE = 2;
    public  static final int REQUEST_CODE_SHOW_NOTES = 3;

    Animation animateHide, animateShow;


    private RecyclerView notesrecyclerview;
private  List<Note>  noteList;
private NotesAdapter notesAdapter;

private int noteClickedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_notes);

//        getSupportActionBar().hide();





        animateShow = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.show_buton);
        animateHide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hide_button);

        ImageView imageAddNoteMain = findViewById(R.id.imageaddnotemain);

        imageAddNoteMain.setOnClickListener(view -> startActivityForResult(
                new Intent(getApplicationContext(), CreateNoteActivity.class),
                REQUEST_CODE_ADD_NOTE
        ));
        notesrecyclerview = findViewById(R.id.notesRecyclerview);
        notesrecyclerview.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );


        noteList = new ArrayList<>();
        notesAdapter = new NotesAdapter(noteList, this);
        notesrecyclerview.setAdapter(notesAdapter);


getNotes(REQUEST_CODE_SHOW_NOTES , false);

        EditText inputSearch = findViewById(R.id.inputsearch);

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                notesAdapter.canceltimer();

            }

            @Override
            public void afterTextChanged(Editable s) {


                if (noteList.size() != 0){
                    notesAdapter.searchNotes(s.toString());
                }
            }
        });

    }


   

    @Override
    public void onNoteClicked(Note note, int position) {

        noteClickedPosition = position;
        Intent intent = new Intent(getApplicationContext() , CreateNoteActivity.class);
        intent.putExtra("isViewOrUpdate", true);
        intent.putExtra("note" , note);
        startActivityForResult(intent , REQUEST_CODE_UPDATE_NOTE);


    }

    private void getNotes(final  int requestcode, final boolean isNoteDelete){


        @SuppressLint("StaticFieldLeak")
        class GetNotesTask extends AsyncTask<Void , Void , List<Note>>{

            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NotesDatabase
                        .getNotesDatabase(getApplicationContext())
                        .noteDao().getAllNotes();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
            if (requestcode == REQUEST_CODE_SHOW_NOTES){
            noteList.addAll(notes);
            notesAdapter.notifyDataSetChanged();

            }else  if (requestcode== REQUEST_CODE_ADD_NOTE){

                noteList.add(0, notes.get(0));
                notesAdapter.notifyItemInserted(0);
                notesrecyclerview.smoothScrollToPosition(0);

            }


            else if (requestcode == REQUEST_CODE_UPDATE_NOTE){
                noteList.remove(noteClickedPosition);

                if (isNoteDelete){
                    notesAdapter.notifyItemRemoved(noteClickedPosition);
                }
                else
                {
                    noteList.add(noteClickedPosition, notes.get(noteClickedPosition));
                    notesAdapter.notifyItemChanged(noteClickedPosition);
                }

            }
            }

        }
        new GetNotesTask().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK){
            getNotes(REQUEST_CODE_ADD_NOTE, false);

//        }else if (requestCode == REQUEST_CODE_UPDATE_NOTE && resultCode == RESULT_OK){
//            getNotes(REQUEST_CODE_ADD_NOTE);
        }
        else if (requestCode == REQUEST_CODE_UPDATE_NOTE && resultCode == RESULT_OK){
            if (data!= null ){
                getNotes(REQUEST_CODE_UPDATE_NOTE, data.getBooleanExtra("isNoteDeleted" , false));
            }
        }
    }


}