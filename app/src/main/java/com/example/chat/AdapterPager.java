package com.example.chat;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class AdapterPager extends FragmentStateAdapter {
    ArrayList<Fragment> list;
    public AdapterPager(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public void setList(ArrayList<Fragment> list){

        this.list=list;
    }
    @NonNull
    @Override


    public Fragment createFragment(int position) {

        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }
}
