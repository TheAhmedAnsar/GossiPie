package com.codewithremo.myapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codewithremo.myapplication.Invite_Activity;
import com.codewithremo.myapplication.R;
import com.codewithremo.myapplication.Settings_part.SettingsActivity;
import com.codewithremo.myapplication.adapters.NotesAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirestoreRegistrar;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Chat_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */




public class Chat_Fragment extends Fragment {




    private FirebaseFirestore firebaseFirestore;
    LinearLayoutManager linearLayoutManager;
    private FirebaseAuth firebaseAuth;


//    FirestoreRecyclerAdapter<firebasemodel,holder> chatAdapter;

    RecyclerView mrecyclerview;
    Inflater inflater;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Object FragmentContainer;


    public Chat_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Chat_Fragment newInstance(String param1, String param2) {
        Chat_Fragment fragment = new Chat_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);




        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.chat_fragment, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mrecyclerview = v.findViewById(R.id.recyclerview);

//
//        // Query query=firebaseFirestore.collection("Users");
//        Query query=firebaseFirestore.collection("Users").whereNotEqualTo("uid",firebaseAuth.getUid());
//        FirestoreRecyclerOptions<firebasemodel> allusername=new FirestoreRecyclerOptions.Builder<firebasemodel>().setQuery(query,firebasemodel.class).build();
//
//        chatAdapter = new FirestoreRecyclerAdapter<firebasemodel, holder>(allusername) {
//            @Override
//            protected void onBindViewHolder(@NonNull  holder holder, int position, @NonNull  firebasemodel model) {
//
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
////                        Intent intent = new Intent(getActivity() ,"Item is clicked", Toast.LENGTH_SHORT).show
//                        Toast.makeText(getActivity(), "Item is Clicked", Toast.LENGTH_SHORT).show();
////                        intent.putExtra("name",firebasemodel.getName());
////                        intent.putExtra("receiveruid",firebasemodel.getUid());
////                        intent.putExtra("imageuri",firebasemodel.getImage());
////                        startActivity(intent);
//                    }
//                });
//
//            }
//
//            @NonNull
//            @Override
//            public holder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
//
//                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.chatviewlayout,parent,false);
//                return new holder(view);
//            }
//        };
//
//
//        mrecyclerview.setHasFixedSize(true);
//        linearLayoutManager=new LinearLayoutManager(getContext());
//        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
//        mrecyclerview.setLayoutManager(linearLayoutManager);
//        mrecyclerview.setAdapter(chatAdapter);
//
//
//        return v;
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.chat_fragment, container, false);
//    }
//
//    public class holder extends  RecyclerView.ViewHolder{
//
//        private TextView particularusername;
//        private TextView statusofuser;
//
//        public holder(@NonNull View itemView) {
//            super(itemView);
//
//
//            particularusername=itemView.findViewById(R.id.nameofuser);
//            statusofuser=itemView.findViewById(R.id.statusofuser);
////            mimageviewofuser=itemView.findViewById(R.id.imageviewofuser);
//
////            We will display at late
//        }
//    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        chatAdapter.startListening();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if(chatAdapter!=null)
//        {
//            chatAdapter.stopListening();
//        }
//    }


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sidemenu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    // Menu Options Selection

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.item2) {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
//            overridePendingTransition(R.anim.hide_button,
//                    R.anim.show_buton);
        } else if (id == R.id.item3) {
            Intent intent = new Intent(getActivity(), Invite_Activity.class);
            startActivity(intent);
//            Intent intent = new Intent(this , Invite_Activity.class);

        }

        return true;

    }
}