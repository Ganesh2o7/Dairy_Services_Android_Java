package com.example.drawer1.ui;

import android.annotation.SuppressLint;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.drawer1.R;

import androidx.appcompat.app.AppCompatActivity;

public class seller extends AppCompatActivity {
    private TextView name;
    private ImageView imageView;
    private TextView addr;
    private String n;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle sa){
        super.onCreate(sa);
        setContentView(R.layout.seller_dialog);
        ratingBar=findViewById(R.id.rating);
        getComingIntent();

    }
    private void getComingIntent(){
        if(getIntent().hasExtra("image_url")&&getIntent().hasExtra("name")&&
                getIntent().hasExtra("address")&&getIntent().hasExtra("rating")){
            Integer image=getIntent().getIntExtra("image_url",0);
            String names=getIntent().getStringExtra("name");
            String address=getIntent().getStringExtra("address");
            Double rating=getIntent().getDoubleExtra("rating",0);
            set(image,names,address,rating);
        }
    }
    @SuppressLint("SetTextI18n")
    private void set(Integer url, String nam, String add,Double rat){
        addr=findViewById(R.id.address);
        addr.setText("Address :"+add);
        name= findViewById(R.id.seller_txt);
        name.setText(nam);
        ratingBar.setRating(rat.floatValue());
        imageView=findViewById(R.id.seller_img);
        Glide.with(this)
                .asBitmap()
                .load(url)
                .into(imageView);
    }
}
