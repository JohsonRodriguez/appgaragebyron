package com.example.appcocherabyron.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface RegisterService {

    @Headers("Ocp-Apim-Subscription-Key: fdedf7de7f014ceea33b36bfedd8075e")
    @GET("register/searchday/{dia}")
    Call<List<RegisterResponse>> getRegisters(@Header("Authorization")String token, @Path("dia") String dia);
}
