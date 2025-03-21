package com.codewithremo.myapplication.adapters;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codewithremo.myapplication.R;
import com.codewithremo.myapplication.entities.Note;
import com.codewithremo.myapplication.listeners.NotesListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NotesAdapter extends  RecyclerView.Adapter<NotesAdapter.NoteViewHolder>  {

    private List<Note> notes;
    private NotesListener notesListener;
    private List<Note> notesSource;
    private Timer timer;


    public NotesAdapter(List<Note> notes, NotesListener notesListener) {
        this.notes = notes;
        this.notesListener = notesListener;
        notesSource = notes;

    }

    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new NoteViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_note,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
holder.setNote(notes.get(position));
holder.layoutNote.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        notesListener.onNoteClicked(notes.get(position), position);
    }
});
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    static class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle , textSubtitle , textDateTime;
LinearLayout layoutNote;


         NoteViewHolder(@NonNull  View itemView) {
             super(itemView);

             textTitle = itemView.findViewById(R.id.texttitle);
             textSubtitle =itemView.findViewById(R.id.textsubtitle);
             textDateTime = itemView.findViewById(R.id.textdatetime);
             layoutNote = itemView.findViewById(R.id.layoutnote);


        }

        void  setNote(Note note){
             textTitle.setText(note.getTitle());
             if (note.getSubtitle().trim().isEmpty()){
                 textSubtitle.setVisibility(View.GONE);

             }
             else{
                 textSubtitle.setText(note.getSubtitle());
             }
             textDateTime.setText(note.getDatetime());
            GradientDrawable gradientDrawable = (GradientDrawable) layoutNote.getBackground();
            if (note.getColor() != null){

                gradientDrawable.setColor(Color.parseColor(note.getColor()));

            }
            else {


                gradientDrawable.setColor(Color.parseColor("#333333"));
            }



        }
    }


    public void searchNotes(final String searckeyword){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (searckeyword.trim().isEmpty()){
                    notes = notesSource;
                }else {
                    ArrayList<Note> temp = new ArrayList<>();
                    for (Note note :notesSource){
                        if (note.getTitle().toLowerCase().contains(searckeyword.toLowerCase())
                        || note.getSubtitle().toLowerCase().contains(searckeyword.toLowerCase())){
                            temp.add(note);
                        }

                    }
                    notes = temp;
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                   notifyDataSetChanged();
                    }
                });

            }
        }, 500);
    }
    public void canceltimer(){
        if (timer != null){
            timer.cancel();
        }
    }

}
