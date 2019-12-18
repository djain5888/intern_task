package com.news.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Image_activity extends AppCompatActivity {
    RecyclerView r1;
    adapter a1;
    DatabaseReference data;
    List<upload> muploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_activity);
        r1=findViewById(R.id.recycler);
        r1.setHasFixedSize(true);
        muploads=new ArrayList<>();
        r1.setLayoutManager(new LinearLayoutManager(this));
        data= FirebaseDatabase.getInstance().getReference("uploads");
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    upload upload=snapshot.getValue(upload.class);
                    Log.i("link",upload.getimageuri());
                    muploads.add(upload);
                }
                a1=  new adapter(Image_activity.this,muploads);
                r1.setAdapter(a1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
