package com.codewithremo.myapplication.Filter_Contact;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codewithremo.myapplication.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> {

   ArrayList<UserObject> userList;

    public UserListAdapter(ArrayList<UserObject> userList){
        this.userList = userList;
    }


    @NotNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users , null , false);
        RecyclerView.LayoutParams ip = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(ip);
        UserListViewHolder rcv = new UserListViewHolder(layout);

        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull  UserListViewHolder holder, int position) {

        holder.mName.setText(userList.get(position).getName());
        holder.mPhone.setText(userList.get(position).getPhone());


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public  class  UserListViewHolder extends RecyclerView.ViewHolder{
    TextView mName , mPhone;
        public  UserListViewHolder(View view){
            super(view);

            mName = view.findViewById(R.id.name);
            mPhone = view.findViewById(R.id.phone);



        }
}
}
