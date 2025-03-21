package com.codewithremo.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.codewithremo.myapplication.Fragments.Chat_Fragment;
import com.codewithremo.myapplication.Fragments.Group_Fragment;
import com.codewithremo.myapplication.Fragments.Story_Fragment;

public class PageAdapter extends FragmentPagerAdapter {
    private  int tabsNumber;
    public PageAdapter(@NonNull FragmentManager fm, int behavior, int tabs) {

        super(fm, behavior);
        this.tabsNumber = tabs;
    }


    @NonNull
//    @org.jetbrains.annotations.NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Group_Fragment();
            case 1:
                return new Chat_Fragment();
            case 2:

                return new Story_Fragment();
            default:
                return null;

        }



    }

    @Override
    public int getCount() {
        return tabsNumber;
    }
}
