package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chat.databinding.ActivityOtpMessageBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpMessage extends AppCompatActivity {
ActivityOtpMessageBinding binding;
FirebaseAuth auth=FirebaseAuth.getInstance();
Long timeOutSeconds=60L;
String verificationCode;
PhoneAuthProvider.ForceResendingToken resendingToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOtpMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(OtpMessage.this, R.color.Whats));
        getSupportActionBar().hide();
        String num= getIntent().getStringExtra("phone");
          binding.title2.setText("+20-"+num);
        setOtp(num,false);
    }
    private void setOtp(String phoneNumber,boolean isResend){
        setInProgress(true);
        PhoneAuthOptions.Builder builder=PhoneAuthOptions.newBuilder(auth).setPhoneNumber(phoneNumber).setTimeout(timeOutSeconds, TimeUnit.SECONDS)
                .setActivity(this).setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signIn(phoneAuthCredential);
                        setInProgress(false);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(OtpMessage.this, "OTP verification failed", Toast.LENGTH_LONG).show();
                    setInProgress(false);
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                    verificationCode=s;
                    resendingToken=forceResendingToken;
                        Toast.makeText(OtpMessage.this, "OTP sent successfully", Toast.LENGTH_LONG).show();
                        setInProgress(false);
                    }
                });
        if(isResend){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        }else{
            PhoneAuthProvider.verifyPhoneNumber(builder.build());

        }
    }


    private void setInProgress(boolean inProgress){
        if(inProgress){
            binding.progress.setVisibility(View.VISIBLE);
           binding.btnNext.setVisibility(View.GONE);
        }else{
            binding.progress.setVisibility(View.GONE);
            binding.btnNext.setVisibility(View.VISIBLE);
        }
    }
    private void signIn(PhoneAuthCredential phoneAuthCredential) {

    }


}