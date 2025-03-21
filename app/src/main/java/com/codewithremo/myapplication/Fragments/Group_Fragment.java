package com.codewithremo.myapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.codewithremo.myapplication.Invite_Activity;
import com.codewithremo.myapplication.R;
import com.codewithremo.myapplication.Settings_part.SettingsActivity;
import com.codewithremo.myapplication.newgroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Group_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Group_Fragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MenuInflater inflater;
    private MenuItem item;
    MenuItem searchtool;

    public Group_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Group_Fragment newInstance(String param1, String param2) {
        Group_Fragment fragment = new Group_Fragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.group_fragment, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sidemenu2, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }


    // Menu Options Selection

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        switch (item.getItemId()) {



            case R.id.new_group:
                Intent intent = new Intent(getActivity(), newgroup.class);
                startActivity(intent);
                return true;

            case R.id.item2:

            Intent intent1 = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent1);
                return true;
//            overridePendingTransition(R.anim.hide_button,
//                    R.anim.show_buton);
            case R.id.item3:
            Intent intent2 = new Intent(getActivity(), Invite_Activity.class);
            startActivity(intent2);

        }

        return true;

    }
}