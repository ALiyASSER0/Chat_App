package com.example.chat;




import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ChatsRecycleAdapter extends RecyclerView.Adapter<ChatsRecycleAdapter.Holder> {
    ArrayList<ContentChat> list;
    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void setList(ArrayList<ContentChat> list) {
        this.list = list;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
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
        return list == null ? 0 : list.size();
    }


    class Holder extends RecyclerView.ViewHolder {
        TextView name, time;
        ImageView Img;
        ConstraintLayout Cart;

        public Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            Img = itemView.findViewById(R.id.Imaggg);
            time = itemView.findViewById(R.id.time);
            Cart = itemView.findViewById(R.id.Cart);


        }

        public void init(ContentChat contentChat) {
            name.setText(contentChat.getName());
            Glide.with(itemView.getContext()).load(contentChat.getImg()).into(Img);
            time.setText(contentChat.getTime());

        }

    }


    interface OnItemClick {

        void OnClick(ContentChat contentChat);
    }
}