package com.example.chat;



import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class StatusRecycleAdapter extends RecyclerView.Adapter<Holder2> {
   ArrayList<ContentStatus> list;
    private Holder2.OnItemClick onItemClick;

    public void setOnItemClick(Holder2.OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void setList(ArrayList<ContentStatus> list) {
        this.list = list;

    }

    @NonNull
    @Override
    public Holder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.status_content,parent,false);
        return new Holder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder2 holder, int position) {
        holder.init(list.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onItemClick.OnClick(list.get(holder.getLayoutPosition()));
            }
        });


    }



    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }
}
class Holder2 extends RecyclerView.ViewHolder {
    TextView name,partChat;
    ImageView img;

    public Holder2(@NonNull View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.name);
        img=itemView.findViewById(R.id.Imaggg);
        partChat=itemView.findViewById(R.id.partChat);

    }
    public void init(ContentStatus contentStatus) {
        name.setText(contentStatus.getName());
        Log.e("err","FuckCv:"+contentStatus.getImg());
        Glide.with(itemView.getContext()).load(contentStatus.getImg()).into(img);
        partChat.setText(contentStatus.getPartChat());

    }
    interface OnItemClick {

        void OnClick(ContentStatus contentStatus);
    }

}

