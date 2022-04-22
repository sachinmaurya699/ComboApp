package com.sos.comboapp.Network;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface ApiService
{
    @POST("send")
    Call<String> sendmessage(
            @HeaderMap HashMap<String,String> header,@Body String messageBody

            );





}
