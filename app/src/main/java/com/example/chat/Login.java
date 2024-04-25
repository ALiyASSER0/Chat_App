package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.chat.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    ActivityLoginBinding binding;
    String[] items = {"Egypt", "American", "Europa"};
    ArrayAdapter<String> adapter;
FirebaseAuth auth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(Login.this, R.color.Whats));
        getSupportActionBar().hide();
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, items);
        binding.autoText.setAdapter(adapter);

        binding.autoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                switch (item) {
                    case "Egypt":
                        binding.phoneId.setText("+20");
                        break;
                    case "American":
                        binding.phoneId.setText("+1");
                        break;
                    case "Europa":
                        binding.phoneId.setText("+10");
                        break;
                }
            }
        });
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = binding.editText.getText().toString().trim();
                validateNumberPhone(number);
            }
        });
    }

    private void validateNumberPhone(String num) {
        if (num.isEmpty() ){
            binding.editText.setError(getString(R.string.req));
        }

        else if(num.length() < 11) {

            Toast.makeText(this, "Invalid number", Toast.LENGTH_SHORT).show();


        }
        else if (!num.startsWith("0")) {
                Toast.makeText(this, "Invalid number", Toast.LENGTH_SHORT).show();
            }
        else if(num.charAt(1) != '1' ){
            Toast.makeText(this, "Invalid number", Toast.LENGTH_SHORT).show();
            }
        else if(num.charAt(2) == '1' || num.charAt(2) == '2' || num.charAt(2) == '0' || num.charAt(2) == '5'){

                auth.signInWithEmailAndPassword(num+"@gmail.com","name123456").addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
               Intent intent =new Intent(Login.this,MainActivity.class);
                startActivity(intent);
                Login.this.finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Intent intent =new Intent(Login.this,Login_Inf.class);
                        intent.putExtra("phone",num);
                        startActivity(intent);
                        Login.this.finish();
                    }
                });



//            else{
//                Intent intent =new Intent(Login.this,MainActivity.class);
//                startActivity(intent);
//                Login.this.finish();
//            }



        }else{
            Toast.makeText(this, "Invalid number", Toast.LENGTH_SHORT).show();
        }

        }

    }
