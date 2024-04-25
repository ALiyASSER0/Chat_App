package com.example.chat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.chat.databinding.StatusFregementBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StatusFragment extends Fragment {
    StatusFregementBinding binding;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    StatusRecycleAdapter RE=new StatusRecycleAdapter();
    StorageReference storageReference= FirebaseStorage.getInstance().getReference();
    ArrayList<ContentStatus> list=new ArrayList<>();
DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
private final int Gallery_Req_Code=1000;
String name;
String uid;
Uri ImagePicture;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("err","Hereee");
        ref.child(Constant.KEY_USERS).addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        for(DataSnapshot snapshot1:snapshot.getChildren()){
            ContentChat m=snapshot1.getValue(ContentChat.class);
            if(m.getUid().equals(auth.getCurrentUser().getUid())){
                     name=m.getName();
                     uid=m.getUid();
                Glide.with(StatusFragment.this.getContext()).load(m.getImg()).into(binding.ImagC);
            }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});




//        value();
    }

    private void value(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.status_fregement,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding=StatusFregementBinding.bind(view);
        RE.setList(list);
        binding.Reyc.setAdapter(RE);
        RE.setOnItemClick(new Holder2.OnItemClick() {
            @Override
            public void OnClick(ContentStatus contentStatus) {
                Intent intent=new Intent(StatusFragment.this.getContext(),ShowStutas.class);
                intent.putExtra("object",contentStatus);
                startActivity(intent);
            }
        });

        binding.ImagC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                   startActivityForResult(iGallery,Gallery_Req_Code);
            }
        });
        MethodRetrive();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      if(requestCode==Gallery_Req_Code && data!=null && data.getData()!=null){
          ImagePicture=data.getData();
      binding.progress.setVisibility(View.VISIBLE);
          data(ImagePicture);
      }

    }

private void data(Uri imagePicture){
    sentImageToStorage(imagePicture,name,uid);
    Log.e("err","done");
}

    private void sentImageToStorage(Uri imagePicture, String name, String uid) {
        StorageReference rf=storageReference.child("Status/").child(auth.getUid()+System.currentTimeMillis());
        rf.putFile(imagePicture).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                rf.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        sentDataToRealTime(name,uid,uri.toString());
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StatusFragment.this.getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void sentDataToRealTime(String name,String uid,String ImgUrl){
        Date currentDate = new Date();
        SimpleDateFormat clockFormat = new SimpleDateFormat("h:mm a");
        list.clear();
        ref.child(Constant.KEY_STATUS).child(uid).push().setValue(new ContentStatus(name,clockFormat.format(currentDate),ImgUrl))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        binding.progress.setVisibility(View.GONE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        binding.progress.setVisibility(View.GONE);
                        Toast.makeText(StatusFragment.this.getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private void MethodRetrive(){

        ref.child(Constant.KEY_STATUS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                   for(DataSnapshot snapshot2:snapshot1.getChildren()){
                       ContentStatus m=snapshot2.getValue(ContentStatus.class);
                       list.add(m);
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
