package com.news.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class phone extends AppCompatActivity {
    int flag=0;
    private FirebaseAuth mAuth;
    String verificationId;
    PhoneAuthCredential credential;
    private TextView verify, verifyotp;
    Button b1;
    private EditText editTextusername, editTextemail, editTextpassword, editTextotp;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack;
    Intent i1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        mAuth = FirebaseAuth.getInstance();
        editTextemail=findViewById(R.id.editText2);
        editTextotp=findViewById(R.id.otp);
        verify = findViewById(R.id.verify);
        verifyotp = findViewById(R.id.verifyotp);
        editTextusername=findViewById(R.id.name);
        b1=findViewById(R.id.proceed);

        i1=new Intent(this,MainActivity.class);

        mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationId = s;
                if(verificationId.isEmpty())
                {
                    Toast.makeText(phone.this, "please", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    flag=1;
                }
            }

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                String code = phoneAuthCredential.getSmsCode();
                if (code != null) {
                    editTextotp.setText(code);
                    //verifyCode(code);
                }
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(phone.this, e.getMessage()+"\nFailed Try Again", Toast.LENGTH_SHORT).show();
            }
        };
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    verify.setTextColor(getResources().getColor(R.color.cardview_shadow_start_color));
                    String number = editTextemail.getText().toString();
                    //number = "+91" + number;
                    if (Pattern.compile("[7-9][0-9]{9}").matcher(number).matches()) {
                        sendVerificationCode("+91"+number);
                    } else {
                        Toast.makeText(phone.this, "invalid Number", Toast.LENGTH_SHORT).show();
                    }


               // verifyotp.setVisibility(View.VISIBLE);
            }
        });

        verifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                verifyotp.setTextColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_pressed));

//                if (code.isEmpty() || code.length() < 6) {
//
//                    editTextpassword.setError("Enter code...");
//                    //editTextpassword.requestFocus();
//                    return;
//                }
                //Toast.makeText(RegisterActivity.this, "Verified", Toast.LENGTH_SHORT).show();
                verifyCode();

            }
        });




    }
    private void sendVerificationCode(String number) {
        Toast.makeText(this, "Sending OTP", Toast.LENGTH_SHORT).show();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack

        );

    }




//    private void verifyCode(String code) {
//        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
//    }

    private void verifyCode() {
        String code = editTextotp.getText().toString();
        if(flag==1)
        {
        credential = PhoneAuthProvider.getCredential(verificationId, code);


        signInWithCredential(credential);}
        else{
            Toast.makeText(this, "wrong", Toast.LENGTH_SHORT).show();
        }
    }
    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            b1.setVisibility(View.VISIBLE);



                            //register1.setVisibility(View.VISIBLE);

                            //register1.setVisibility(View.VISIBLE);
                            Log.d("uyh", "signInWithCredential:success");

                        } else {
                            Log.w("lllllllllllllll", "signInWithCredential:failure", task.getException());
                            Toast.makeText(phone.this, "Not A Valid OTP", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void register(View v)
    {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (editTextusername.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Please enter user field ", Toast.LENGTH_SHORT).show();
        }
        else{
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(editTextusername.getText().toString())

                //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Log.i("profile", "User profile updated.");
                            startActivity(i1);
                        }
                    }
                });


    }
    }

}
