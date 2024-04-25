package com.example.chat;


import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
   private ArrayList<MessageContent> list=new ArrayList<>();
   private OnclickItem onclickItem;




    public void setOnclickItem(OnclickItem onclickItem){
       this.onclickItem=onclickItem;
   }

int ITEM_SEND_MESSAGE=1;
int ITEM_SEND_PHOTO=2;
public ArrayList<MessageContent> getList() {
    return list;
}

    public void addItem(MessageContent message) {
       list.add(message);
       notifyItemInserted(list.size()-1);

    }
    public void removeItem(int pos) {
        list.remove(pos);
       notifyItemRemoved(pos);
    }
    public void editItem(int pos,MessageContent message) {
        list.set(pos,message);
        notifyItemChanged(pos);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==ITEM_SEND_MESSAGE){
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_message_contant2, parent, false);
    return new Holder(view);
}
        else{
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_message_photo, parent, false);
    return new Holder2(view);
}

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
if(holder.getClass()==Holder.class){
   Holder h= (Holder)holder;
    if(list.get(position).getSenderID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                h.layoutM.setHorizontalGravity(Gravity.START);
        int rec=holder.itemView.getContext().getResources().getIdentifier("background4","drawable",holder.itemView.getContext().getPackageName());
        h.massege.setBackgroundResource(rec);
                h.init(list.get(position).getMessage());
            }else{
                h.layoutM.setHorizontalGravity(Gravity.END);
          int rec=holder.itemView.getContext().getResources().getIdentifier("background3","drawable",holder.itemView.getContext().getPackageName());
                h.massege.setBackgroundResource(rec);
                h.init(list.get(position).getMessage());
            }

   h.init(list.get(position).getMessage());
}else{
    Holder2 h2= (Holder2)holder;
    if(list.get(position).getSenderID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
        h2.layoutB.setHorizontalGravity(Gravity.START);
        h2.init2(list.get(position).getImage());
    }else{
        h2.layoutB.setHorizontalGravity(Gravity.END);
        h2.init2(list.get(position).getImage());
    }
}



    }

//**********************************************************************//
//    @Override
//    public int getItemCount() {
//        return list == null ? 0 : list.size();
//    }
//
//    @NonNull
//    @Override
//    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_message_contant2, parent, false);
//        return new Holder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull Holder holder, int position) {
//    if(list.get(position).getSenderID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
//                holder.layoutM.setHorizontalGravity(Gravity.START);
//                holder.init(list.get(position).getMessage());
//            }else{
//                holder.layoutM.setHorizontalGravity(Gravity.END);
//          int rec=holder.itemView.getContext().getResources().getIdentifier("background3","drawable",holder.itemView.getContext().getPackageName());
//                holder.massege.setBackgroundResource(rec);
//                holder.init(list.get(position).getMessage());
//            }
//
//    }

 //***************************************************************//

    @Override
    public int getItemViewType(int position) {

      if(list.get(position).getType()==null){
          return ITEM_SEND_MESSAGE;
      }
    else if(list.get(position).getType().equals("1")){
            return ITEM_SEND_MESSAGE;
        }else{
            return ITEM_SEND_PHOTO;
        }
    }
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
//*********************************************//
//    class Holder extends RecyclerView.ViewHolder {
//        TextView massege;
//     LinearLayout layoutM;
//        public Holder(@NonNull View itemView) {
//            super(itemView);
//            massege = itemView.findViewById(R.id.MessageText);
//           layoutM=itemView.findViewById(R.id.layoutM);
//          itemView.setOnLongClickListener(new View.OnLongClickListener() {
//              @Override
//              public boolean onLongClick(View v) {
//                  onclickItem.Onclick(list.get(getLayoutPosition()));
//                  return false;
//              }
//          });
//        }
//
//        public void init(String Name) {
//            massege.setText(Name);
//
//        }
//
//    }
    //*******************************************************************//

        class Holder extends RecyclerView.ViewHolder {
        TextView massege;
     LinearLayout layoutM;
        public Holder(@NonNull View itemView) {
            super(itemView);
            massege = itemView.findViewById(R.id.MessageText);
           layoutM=itemView.findViewById(R.id.layoutM);
          itemView.setOnLongClickListener(new View.OnLongClickListener() {
              @Override
              public boolean onLongClick(View v) {
                  onclickItem.Onclick(list.get(getLayoutPosition()));
                  return false;
              }
          });
        }

        public void init(String Name) {
            massege.setText(Name);

        }

    }


    class Holder2 extends RecyclerView.ViewHolder {
       ImageView image;
        LinearLayout layoutB;

        public Holder2(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imagee);
            layoutB=itemView.findViewById(R.id.layoutB);
        }

        public void init2(String uri) {
            Glide.with(itemView.getContext()).load(uri).into(image);
        }

    }

interface OnclickItem{
     void Onclick(MessageContent messageContent);
}
}