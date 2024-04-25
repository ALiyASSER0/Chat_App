package com.example.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.chat.databinding.ActivityLoginInfBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Login_Inf extends AppCompatActivity {
ActivityLoginInfBinding binding;
FirebaseAuth auth=FirebaseAuth.getInstance();
StorageReference storageReference= FirebaseStorage.getInstance().getReference();
DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
Uri ImagePicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding=ActivityLoginInfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(Login_Inf.this, R.color.Whats));
        getSupportActionBar().hide();
        String num= getIntent().getStringExtra("phone");
        binding.Imag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(iGallery,22);
            }
        });
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.progress.setVisibility(View.VISIBLE);
                String name=binding.editText.getText().toString().trim();
                vaildation(name,num);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if(requestCode==22 && data!=null && data.getData()!=null){
        ImagePicture=data.getData();
        binding.Imag.setImageURI(ImagePicture);
    }

    }

    private void vaildation(String name, String num) {
      if(name.isEmpty()){
          binding.progress.setVisibility(View.GONE);
          binding.editText.setError(getString(R.string.req));

      }else{
          auth.createUserWithEmailAndPassword(num+"@gmail.com","name123456").addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                  @Override
                  public void onSuccess(AuthResult authResult) {
                 sentImageToStorage(ImagePicture,name,authResult.getUser().getUid());
                  }
              }).addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {
                      binding.progress.setVisibility(View.GONE);
                      Toast.makeText(Login_Inf.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                  }
              });


          }


      }

    private void sentImageToStorage(Uri imagePicture, String name, String uid) {
          StorageReference rf=storageReference.child("images/").child(auth.getUid()+System.currentTimeMillis());
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
                   Toast.makeText(Login_Inf.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
               }
           });

    }
private void sentDataToRealTime(String name,String uid,String ImgUrl){

    Date currentDate = new Date();

    SimpleDateFormat clockFormat = new SimpleDateFormat("h:mm a");

    ref.child(Constant.KEY_USERS).child(uid).setValue(new ContentChat(name,ImgUrl,clockFormat.format(currentDate),uid,""))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        binding.progress.setVisibility(View.GONE);
                        startActivity(new Intent(Login_Inf.this,MainActivity.class));
                        Login_Inf.this.finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        binding.progress.setVisibility(View.GONE);
                        Toast.makeText(Login_Inf.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

}
}
