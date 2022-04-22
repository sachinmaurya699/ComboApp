package com.sos.comboapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PostProcessor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
{

    private TextView textView;
    private My_item_interface my_item_interface;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.my_text_item);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //retrofit define

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //interface define.......

       My_item_interface m1 =retrofit.create(My_item_interface.class);

       //Network request............
        Call<List<My_item>> call= m1.getPosts();

        //network request responce.......
        call.enqueue(new Callback<List<My_item>>()
        {
            @Override
            public void onResponse(Call<List<My_item>> call, Response<List<My_item>> response)
            {

                if(!response.isSuccessful())
                {
                    textView.setText("code:"  +response.code());
                    return;
                }

                List<My_item> posts=response.body();

                Log.d("my_all_data",response.toString());


                for(My_item my_item :posts)
                {
                    String contentr="";

                    contentr+="ID"+my_item.getId()+"\n";
                    contentr+="User ID:"+my_item.getUser_id()+"\n";
                    contentr+="Title:"+my_item.getTitle()+"\n";
                    contentr+="Text:"+my_item.getText()+"\n\n";

                    textView.append(contentr);

                }

                Toast.makeText(MainActivity.this, "call responce", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<My_item>> call, Throwable t)
            {
                Log.d("my_error",t.getMessage());

                Toast.makeText(MainActivity.this, "Not call responce", Toast.LENGTH_SHORT).show();


            }
        });



    }

}