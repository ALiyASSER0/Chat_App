package com.example.chat;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CallRecycleAdapter extends RecyclerView.Adapter<Holder3> {
    ArrayList<ContentCall> list;

    public void setList(ArrayList<ContentCall> list) {
        this.list = list;

    }

    @NonNull
    @Override
    public Holder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.call_content,parent,false);
        return new Holder3(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder3 holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.chat.setText(list.get(position).getPartChat());
        holder.img.setImageDrawable(list.get(position).getImg());


    }



    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }
}

class Holder3 extends RecyclerView.ViewHolder {
    TextView name, chat;
    ImageView img;

    public Holder3(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        chat = itemView.findViewById(R.id.partChat);
        img = itemView.findViewById(R.id.Imaggg);


    }
}