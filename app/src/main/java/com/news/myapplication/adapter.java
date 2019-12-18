package com.news.myapplication;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

public class adapter extends RecyclerView.Adapter<adapter.ImageViewHolder> {
   private Context mcontext;
   private List<upload> u1;
   public adapter(Context context,List<upload>uploads)
   {
        mcontext=context;
        u1=uploads;
   }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mcontext).inflate(R.layout.card,parent,false);
        return new ImageViewHolder(v);
    }

    @NonNull



    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
       upload uploadcurent=u1.get(position);
       holder.textname.setText(uploadcurent.getname());
       holder.uname.setText(uploadcurent.getuser());
       Log.i("l1",uploadcurent.getimageuri());
        Picasso.with(mcontext)
                .load(uploadcurent.getimageuri())
                .placeholder(R.mipmap.ic_launcher)
                
                .fit()


                .into(holder.image);

    }


    @Override
    public int getItemCount() {
        return u1.size();
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder
    { public TextView textname;
        public TextView uname;
        public ImageView image;
        public ImageViewHolder(View ItemView)
        {


            super(ItemView);
            uname=ItemView.findViewById(R.id.text_view_user);
            textname=ItemView.findViewById(R.id.text_view_name);
            image=ItemView.findViewById(R.id.image_view_upload);


        }
    }
}
