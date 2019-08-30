package com.rahmatharyadi.dicodingtest.retrofit;

import com.rahmatharyadi.dicodingtest.model.ModelStartup;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface RequestAPIServices {

    @GET("mulaistartup.json")
    Call<ModelStartup> getStartup(@Header("Content-Type") String contentType);
}