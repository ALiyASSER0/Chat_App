package com.example.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chat.databinding.ActivityMessageBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Message extends AppCompatActivity {
ActivityMessageBinding binding;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    StorageReference storageReference= FirebaseStorage.getInstance().getReference();
    DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
MessageRecycleAdapter adapter=new MessageRecycleAdapter();
ArrayList<MessageContent>list=new ArrayList<>();
Uri ImagePicture;
    boolean flage=false;
    boolean flage2=false;
    boolean flage3=false;
    boolean flage4=false;
    boolean flage5=false;
    boolean flage6=false;
    String PID;
    String PID2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding=ActivityMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(Message.this,R.color.Whats));
        getSupportActionBar().hide();
        binding.RecMessage.setAdapter(adapter);
      adapter.setOnclickItem(new MessageRecycleAdapter.OnclickItem() {
          @Override
          public void Onclick(MessageContent messageContent) {
              ref.child(Constant.KEY_Message).child(messageContent.getMessageID()).setValue(null);
          }
      });
       ContentChat obj= (ContentChat) getIntent().getSerializableExtra("object");

              binding.name.setText(obj.getName());
             Glide.with(this).load(obj.getImg()).into(binding.ImgB);

         PID= obj.getUid()+FirebaseAuth.getInstance().getCurrentUser().getUid();
         PID2=FirebaseAuth.getInstance().getCurrentUser().getUid()+obj.getUid();

        String c= ref.child(Constant.KEY_Message).child(PID).getKey();
        String c2= ref.child(Constant.KEY_Message).child(PID2).getKey();

        ref.child(Constant.KEY_Message).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    if(snapshot1.getKey().equals(c)){
                        Log.e("err","C");
                        flage=true;
                       flage3=true;
                        flage5=true;
                        break;
                    }else if(snapshot1.getKey().equals(c2)){
                      Log.e("err","C2");
                        flage2=true;
                       flage4=true;
                        flage6=true;
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref.child(Constant.KEY_Message).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(flage3==true){
                    Log.e("err","Flag1");
                    Flag(PID);
                    flage3=false;

                }else if(flage4==true){
                    Log.e("err","Flag2");
                    Flag(PID2);
                    flage4=false;
                }else{
                    Log.e("err","Flag3");
                    Flag(PID);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              String p=ref.push().getKey();
           if(flage==true){
                  ref.child(Constant.KEY_Message).child(PID).child(p).setValue(new MessageContent(binding.message.getText().toString().trim(), FirebaseAuth.getInstance().getCurrentUser().getUid(), p,"1",null));
                  binding.message.setText("");
                  binding.RecMessage.setVerticalScrollbarPosition(list.size());
              flage=false;

              }else if(flage2==true){
                  ref.child(Constant.KEY_Message).child(PID2).child(p).setValue(new MessageContent(binding.message.getText().toString().trim(), FirebaseAuth.getInstance().getCurrentUser().getUid(), p,"1",null));
                  binding.message.setText("");
                  binding.RecMessage.setVerticalScrollbarPosition(list.size());
             flage2=false;

              }else{
               ref.child(Constant.KEY_Message).child(PID).child(p).setValue(new MessageContent(binding.message.getText().toString().trim(), FirebaseAuth.getInstance().getCurrentUser().getUid(), p,"1",null));
               binding.message.setText("");
               binding.RecMessage.setVerticalScrollbarPosition(list.size());

           }

            }
        });

        binding.sentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(iGallery,22);
            }
        });


    }

public void Flag(String PID){
    ref.child(Constant.KEY_Message).child(PID).addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            adapter.addItem(snapshot.getValue(MessageContent.class));
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            MessageContent m = snapshot.getValue(MessageContent.class);
            ArrayList<MessageContent> NewList = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                NewList = (ArrayList<MessageContent>) adapter.getList().stream().filter(new Predicate<MessageContent>() {
                    @Override
                    public boolean test(MessageContent messageContent) {
                        return messageContent.getMessageID().equals(m.getMessageID());
                    }
                });
            }
            if (NewList.size() > -1) {

                int pos = adapter.getList().indexOf(NewList.get(0));
                if(pos!=-1){
                    adapter.editItem(pos,m);
                }

            }
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            MessageContent m = snapshot.getValue(MessageContent.class);
            ArrayList<MessageContent> NewList = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                NewList = (ArrayList<MessageContent>) adapter.getList().stream().filter(new Predicate<MessageContent>() {
                    @Override
                    public boolean test(MessageContent messageContent) {
                        return messageContent.getMessageID().equals(m.getMessageID());
                    }
                }).collect(Collectors.toList());
            }
            if (NewList.size() > -1) {

                int pos = adapter.getList().indexOf(NewList.get(0));
                if(pos!=-1){
                    adapter.removeItem(pos);
                }

            }
        }
        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==22 && data!=null && data.getData()!=null){
            ImagePicture=data.getData();
            binding.progress.setVisibility(View.VISIBLE);
           sentImageToStorage(ImagePicture);


        }

    }
    private void sentImageToStorage(Uri imagePicture) {
        StorageReference rf=storageReference.child("ChatImages/").child(auth.getUid()+System.currentTimeMillis());
        rf.putFile(imagePicture).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                rf.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        sentDataToRealTime(uri.toString());
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Message.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void sentDataToRealTime(String ImgUrl){

        String p=ref.push().getKey();
        if(flage5==true){
            binding.progress.setVisibility(View.GONE);
            Log.e("tag","flage5 true");
            ref.child(Constant.KEY_Message).child(PID).child(p).setValue(new MessageContent(null, FirebaseAuth.getInstance().getCurrentUser().getUid(), p,"2",ImgUrl));
            flage5=false;

        }else if(flage6==true){
            binding.progress.setVisibility(View.GONE);
            Log.e("tag","flage6 true");
            ref.child(Constant.KEY_Message).child(PID2).child(p).setValue(new MessageContent(null, FirebaseAuth.getInstance().getCurrentUser().getUid(), p,"2",ImgUrl));
            binding.message.setText("");
            binding.RecMessage.setVerticalScrollbarPosition(list.size());
            flage6=false;

        }else{
            binding.progress.setVisibility(View.GONE);
            ref.child(Constant.KEY_Message).child(PID).child(p).setValue(new MessageContent(null, FirebaseAuth.getInstance().getCurrentUser().getUid(), p,"2",ImgUrl));
            binding.message.setText("");
            binding.RecMessage.setVerticalScrollbarPosition(list.size());

        }


    }
/*
            String MessID=ref.push().getKey();
            String p=ref.push().getKey();
            if(flage==true){
                ref.child(Constant.KEY_Message).child(PID).child(p).setValue(new MessageContent(null, FirebaseAuth.getInstance().getCurrentUser().getUid(), MessID,"2",null));
                binding.message.setText("");
                binding.RecMessage.setVerticalScrollbarPosition(list.size());
                flage=false;

            }else if(flage2==true){
                ref.child(Constant.KEY_Message).child(PID2).child(p).setValue(new MessageContent(binding.message.getText().toString().trim(), FirebaseAuth.getInstance().getCurrentUser().getUid(), MessID));
                binding.message.setText("");
                binding.RecMessage.setVerticalScrollbarPosition(list.size());
                flage2=false;

            }else{
                ref.child(Constant.KEY_Message).child(PID).child(p).setValue(new MessageContent(binding.message.getText().toString().trim(), FirebaseAuth.getInstance().getCurrentUser().getUid(), MessID));
                binding.message.setText("");
                binding.RecMessage.setVerticalScrollbarPosition(list.size());

            }


 */

}