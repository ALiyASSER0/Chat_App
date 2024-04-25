package com.example.chat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.chat.databinding.ChatFregementBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class ChatFragment extends Fragment {
    ChatFregementBinding binding;
    ChatsRecycleAdapter RE=new ChatsRecycleAdapter();
    ArrayList<ContentChat> list=new ArrayList<>();
   DatabaseReference ref=FirebaseDatabase.getInstance().getReference();
FirebaseAuth auth=FirebaseAuth.getInstance();


   @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.chat_fregement,container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding=ChatFregementBinding.bind(view);
        getData();
   RE.setOnItemClick(new ChatsRecycleAdapter.OnItemClick() {
       @Override
       public void OnClick(ContentChat contentChat) {
           Intent intent=new Intent(requireActivity().getBaseContext(),Message.class);
           intent.putExtra("object",contentChat);
           startActivity(intent);
       }
   });


   }
    private void getData(){

       ref.child(Constant.KEY_USERS).child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ContentChat c=snapshot.getValue(ContentChat.class);
                if(c.getSearch()==null){
                    MethodRetrive();
                }

                else if(c.getSearch().isEmpty()){
                    MethodRetrive();
                }

                else{
                    ref.child(Constant.KEY_USERS).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                              for(DataSnapshot snapshot1:snapshot.getChildren()){
                                    ContentChat M=snapshot1.getValue(ContentChat.class);
                                    if(M.getUid().equals(FirebaseAuth.getInstance().getUid())) {
                                            String s=M.getSearch();
                                            ref.child(Constant.KEY_USERS).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                   list.clear();
                                                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                                                        ContentChat M2=snapshot1.getValue(ContentChat.class);
                                                        if(!M2.getUid().equals(FirebaseAuth.getInstance().getUid())) {
                                                            if(M2.getName().equals(s)){
                                                                list.add(M2);
                                                            }
                                                        }


                                                    }
                                                    RE.setList(list);
                                                    binding.Reyc.setAdapter(RE);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });



                                        }


                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void MethodRetrive(){
        list.clear();
        ref.child(Constant.KEY_USERS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    ContentChat M=snapshot1.getValue(ContentChat.class);
                    if(!M.getUid().equals(FirebaseAuth.getInstance().getUid())) {
                        list.add(snapshot1.getValue(ContentChat.class));
                    }

                }
                RE.setList(list);
                binding.Reyc.setAdapter(RE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}

