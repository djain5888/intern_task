package com.news.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Main2Activity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email;
    EditText pass;

    EditText name;
    //EditText phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        mAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        pass=findViewById(R.id.password);
        name=findViewById(R.id.name);
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    public void createAccount(String email,String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("createUserWithEmail:","success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            registration();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("createUserWithEmail:","fail");
                            Toast.makeText(Main2Activity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }}});}

                        public void signin(String email,String password)
                        {mAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.i("signInWithEmail:","success");
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            updateUI(user);
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.i( "signInWithEmail","failure");
                                            Toast.makeText(Main2Activity.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();

                                            updateUI(null);
                                        }}});}
                                        public void getcurrentuser()
                                        {
                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            if (user != null) {
                                                // Name, email address, and profile photo Url
                                                String name = user.getDisplayName();
                                                String email = user.getEmail();
                                                Uri photoUrl = user.getPhotoUrl();

                                                // Check if user's email is verified
                                                boolean emailVerified = user.isEmailVerified();

                                                // The user's ID, unique to the Firebase project. Do NOT use this value to
                                                // authenticate with your backend server, if you have one. Use
                                                // FirebaseUser.getIdToken() instead.
                                                String uid = user.getUid();
                                            }
                                        }
                                        public void updateUI(FirebaseUser user){
                                         if(user!=null)
                                         {
                                             Intent i1=new Intent(this,MainActivity.class);
                                             startActivity(i1);
                                         }

                                        }
                                        public void login(View v)
                                        {
                                            if(email.getText().toString().isEmpty()||pass.getText().toString().isEmpty())
                                            {
                                                Toast.makeText(this, "please give valid entry", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                signin(email.getText().toString(),pass.getText().toString());

                                            }
                                        }
                                        public void signup(View v) {
                                            if (email.getText().toString().isEmpty() || pass.getText().toString().isEmpty()) {
                                                Toast.makeText(this, "please give valid entry", Toast.LENGTH_SHORT).show();
                                            } else {

                                                createAccount(email.getText().toString(), pass.getText().toString());
                                                }
                                        }

                                        public void registration()
                                        {    setContentView(R.layout.registration);
                                            Button b1=findViewById(R.id.button4);
                                             name=findViewById(R.id.regname);


                                            // phone=findViewById(R.id.regphone);
                                            if(name.getText().toString().isEmpty())
                                            {
                                                Toast.makeText(this, "name Can't be null", Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                b1.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {


                                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                                .setDisplayName(name.getText().toString())

                                                                //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                                                                .build();

                                                        user.updateProfile(profileUpdates)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            Log.i("profile", "User profile updated.");
                                                                        }
                                                                    }
                                                                });

                                                        updateUI(user);
                                                    }
                                                });
                                            }
                                        }
                                        public void phone(View v)
                                        {
                                            Intent i2=new Intent(this,phone.class);
                                            startActivity(i2);

                                        }

                                        // ...
                                    }



                        // ...




