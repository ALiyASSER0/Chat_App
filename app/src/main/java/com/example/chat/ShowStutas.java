package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.example.chat.databinding.ActivityShowStutasBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowStutas extends AppCompatActivity {
ActivityShowStutasBinding binding;
DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
FirebaseAuth auth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityShowStutasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(ShowStutas.this,R.color.black));
        getSupportActionBar().hide();
        ContentStatus contentStatus= (ContentStatus) getIntent().getSerializableExtra("object");
        ref.child(Constant.KEY_USERS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    ContentChat m=snapshot1.getValue(ContentChat.class);
                    if(m.getUid().equals(auth.getCurrentUser().getUid())){
                        Glide.with(ShowStutas.this).load(m.getImg()).into(binding.ImgB);
                        Glide.with(ShowStutas.this).load(contentStatus.getImg()).into(binding.ImageF);
                        binding.nameX.setText(contentStatus.getName());
                        binding.nameX2.setText(contentStatus.getPartChat());
                    }
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}