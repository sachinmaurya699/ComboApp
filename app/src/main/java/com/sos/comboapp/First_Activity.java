package com.sos.comboapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class First_Activity extends AppCompatActivity
{

    private TextView textView_item;
    private ImageView my_image;
    private   String persolnal_img;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        textView_item=findViewById(R.id.my_textview_item);

        my_image=findViewById(R.id.my_image_view);


        SharedPreferences preferences=getSharedPreferences("My_detail",MODE_PRIVATE);
        String name=preferences.getString("Personal_name","");
        String personal_name=preferences.getString("personal_fmaily","");
        String personal_email=preferences.getString("personal_email","");
        String personal_id=preferences.getString("personal_id","");
        persolnal_img=preferences.getString("persolnal_img","");


        String all_data=name+"\n"+personal_name+"\n"+personal_email+"\n"+personal_id+"\n"+persolnal_img;
        textView_item.setText(all_data);

        Log.d("my_image",persolnal_img);

        Glide.with(this)
                .load(persolnal_img)
                .into(my_image);







    }
}