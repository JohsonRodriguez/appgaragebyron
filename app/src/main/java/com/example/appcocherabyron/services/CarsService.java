package com.example.appcocherabyron.services;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CarsService {

    @Headers("Ocp-Apim-Subscription-Key: fdedf7de7f014ceea33b36bfedd8075e")
    @GET("person/cars/{dni}")
    Call<List<CarsResponse>> getCars(@Header("Authorization")String token, @Path("dni") String dni);

}
