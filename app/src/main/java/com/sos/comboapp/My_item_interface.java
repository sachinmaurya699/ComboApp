package com.sos.comboapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface My_item_interface
{

    @GET("posts")

    //call method......
    Call<List<My_item>> getPosts();




}
