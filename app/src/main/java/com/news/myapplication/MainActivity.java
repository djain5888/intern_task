package com.news.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;



public class MainActivity extends AppCompatActivity {
    int flag=1;
    ImageView i1;
    Button b1;
    Uri Imageuri;
    ProgressBar p1;
    EditText t1;
    private static final int PICK_IMAGE_REQUEST = 1;
    StorageReference mstroage;
    DatabaseReference mdata;
    Button b2;
    StorageTask mupload;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        i1=findViewById(R.id.image);
        b1=findViewById(R.id.upload);
        p1=findViewById(R.id.progressBar);
        t1=findViewById(R.id.editText);
        b2=findViewById(R.id.button2);

        mstroage= FirebaseStorage.getInstance().getReference("uploads");
        mdata= FirebaseDatabase.getInstance().getReference("uploads");
        getcurrentuser();
    }
    public String getcurrentuser()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String name="";
        if (user != null) {
            // Name, email address, and profile photo Url
             name = user.getDisplayName();
            String email = user.getEmail();
            //Uri photoUrl = user.getPhotoUrl();

          //  Toast.makeText(this, name+email, Toast.LENGTH_SHORT).show();
            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();


        }
        return name;
    }
    public void  click(View view)
    {
        Intent i1=new Intent( );
        i1.setType("image/*");
        i1.setAction(i1.ACTION_GET_CONTENT);
        startActivityForResult(i1,PICK_IMAGE_REQUEST);



    }
    public void data1(View view) {
        if (flag == 1) {
            if (Imageuri != null && !(t1.getText().toString().isEmpty())) {
                String ext = getfileextension(Imageuri);
                StorageReference s1 = mstroage.child(System.currentTimeMillis() + "." + ext);
                mupload = s1.putFile(Imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                p1.setProgress(0);
                                flag = 1;
                            }
                        }, 500);
                        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!urlTask.isSuccessful()) ;
                        Uri downloadUrl = urlTask.getResult();

                        upload up = new upload(t1.getText().toString(), downloadUrl.toString(), getcurrentuser());
                        String uploadId = mdata.push().getKey();
                        Log.i("onSuccess ", downloadUrl.toString());

                        Log.i("logs", taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                        mdata.child(uploadId).setValue(up);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        flag = 0;
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        p1.setProgress((int) progress);

                    }
                });
            }
            else if(Imageuri==null||t1.getText().toString().isEmpty()){
                Toast.makeText(this, "Image or Text cannot be null", Toast.LENGTH_SHORT).show();
            }
        }


        else{ Toast.makeText(this, "please wait ", Toast.LENGTH_SHORT).show();}
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Imageuri = data.getData();
            i1.setImageURI(Imageuri);
        }
    }
   String getfileextension(Uri uri)
   {
       ContentResolver c1=getContentResolver();
       MimeTypeMap m1=MimeTypeMap.getSingleton();
       return m1.getExtensionFromMimeType(c1.getType(uri));
   }
    public void upload(View v)
    {
        Intent i1=new Intent(this,Image_activity.class);
        startActivity(i1);
    }
    public void logout(View v)
    {
        FirebaseAuth.getInstance().signOut();
        Intent back=new Intent(this,Main2Activity.class);
        startActivity(back);
    }

}
