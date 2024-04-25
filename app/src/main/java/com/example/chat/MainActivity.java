package com.example.chat;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import android.widget.Toast;

import com.example.chat.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements PopupMenu.OnMenuItemClickListener {
ActivityMainBinding binding;
AdapterPager p;
ArrayList<Fragment> list=new ArrayList<>();
String[] tabb={"Chats","Stutas","Calls"};
ChatFragment chat=new ChatFragment();
DatabaseReference ref=FirebaseDatabase.getInstance().getReference();

FirebaseAuth auth=FirebaseAuth.getInstance();
       StatusFragment status=new StatusFragment();
       CallsFragment calls=new CallsFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
     getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.Whats));
        getSupportActionBar().hide();
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
         p=new AdapterPager(getSupportFragmentManager(),getLifecycle());

        init();
        new TabLayoutMediator(binding.tab, binding.page, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabb[position]);
binding.camera.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
});

            }
        }).attach();

//*********Search********

binding.sentSearch.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String SE=binding.search.getText().toString().trim();
        ref.child(Constant.KEY_USERS).child(auth.getCurrentUser().getUid()).child("search").setValue(SE);

    }
});
binding.close.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ref.child(Constant.KEY_USERS).child(auth.getCurrentUser().getUid()).child("search").setValue("");
        binding.search.setText("");
        ConstraintLayout.LayoutParams params=(ConstraintLayout.LayoutParams )binding.page.getLayoutParams();
        params.setMargins(0,0,0,0);
        binding.textView.setVisibility(View.VISIBLE);
        binding.camera.setVisibility(View.VISIBLE);
        binding.call.setVisibility(View.VISIBLE);
        binding.menu.setVisibility(View.VISIBLE);
        binding.tab.setVisibility(View.VISIBLE);
        binding.search.setVisibility(View.GONE);
        binding.sentSearch.setVisibility(View.GONE);
        binding.close.setVisibility(View.GONE);

    }
});


Log.e("err","getVisibility search:"+binding.search.getVisibility());
Log.e("err","getVisibility:"+binding.sentSearch.getVisibility());
Log.e("err","getVisibility tabs:"+binding.tab.getVisibility());

binding.call.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ConstraintLayout.LayoutParams params=(ConstraintLayout.LayoutParams )binding.page.getLayoutParams();
        params.setMargins(0,250,0,0);
        binding.textView.setVisibility(View.GONE);
        binding.camera.setVisibility(View.GONE);
        binding.call.setVisibility(View.GONE);
        binding.menu.setVisibility(View.GONE);
        binding.tab.setVisibility(View.GONE);
        binding.search.setVisibility(View.VISIBLE);
    binding.sentSearch.setVisibility(View.VISIBLE);
        binding.close.setVisibility(View.VISIBLE);
    }
});

    }
   private void init(){
       list.add(chat);
       list.add(status);
       list.add(calls);
    p.setList(list);
    binding.page.setAdapter(p);
    }
public void showPopup(View view){
PopupMenu p=new PopupMenu(this,view);
    p.inflate(R.menu.popup_menu);
p.setOnMenuItemClickListener(this);

    p.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
      switch (menuItem.getItemId()){
          case R.id.item1:
              Toast.makeText(this, "item1", Toast.LENGTH_SHORT).show();
           return true;
          case R.id.item2:
              Toast.makeText(this, "item2", Toast.LENGTH_SHORT).show();

              return true;
          case R.id.item3:
              startActivity(new Intent(this,Login.class));
              return true;
          default:
              return false;
      }
    }



}