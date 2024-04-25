package com.example.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.chat.databinding.CallsFregementBinding;


import java.util.ArrayList;

public class CallsFragment extends Fragment {
    CallsFregementBinding binding;
    CallRecycleAdapter RE=new CallRecycleAdapter();
    ArrayList<ContentCall> list=new ArrayList<>();



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        value();
    }

    private void value(){
        list.add(new ContentCall("Oma","September 3,9:02 AM",requireActivity().getDrawable(R.drawable.img1)));
        list.add(new ContentCall("ALI","September 27,10:02 AM",requireActivity().getDrawable(R.drawable.img2)));
        list.add(new ContentCall("SOSO","August 11,1:47 PM",requireActivity().getDrawable(R.drawable.img3)));
        list.add(new ContentCall("Oma","Today,2:44 PM",requireActivity().getDrawable(R.drawable.img4)));
        list.add(new ContentCall("ALI","Yesterday,3:25 AM",requireActivity().getDrawable(R.drawable.img5)));
        list.add(new ContentCall("SOSO","Today,2:44 PM",requireActivity().getDrawable(R.drawable.img1)));
        list.add(new ContentCall("Oma","August 11,1:47 PM",requireActivity().getDrawable(R.drawable.img2)));
        list.add(new ContentCall("ALI","Yesterday,3:25 AM",requireActivity().getDrawable(R.drawable.img3)));
        list.add(new ContentCall("SOSO","Yesterday,3:25 AM",requireActivity().getDrawable(R.drawable.img4)));
        list.add(new ContentCall("ALI","Yesterday,3:25 AM",requireActivity().getDrawable(R.drawable.img5)));
        list.add(new ContentCall("SOSO","August 11,1:47 PM",requireActivity().getDrawable(R.drawable.img1)));
        list.add(new ContentCall("Oma","Today,2:44 PM",requireActivity().getDrawable(R.drawable.img2)));
        list.add(new ContentCall("ALI","Yesterday,3:25 AM",requireActivity().getDrawable(R.drawable.img3)));
        list.add(new ContentCall("SOSO","Yesterday,3:25 AM",requireActivity().getDrawable(R.drawable.img4)));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.calls_fregement,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding=CallsFregementBinding.bind(view);
        RE.setList(list);
        binding.Reyc.setAdapter(RE);
    }
}
