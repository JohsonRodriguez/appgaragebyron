package com.example.appcocherabyron.api;

import com.example.appcocherabyron.services.CarsService;
import com.example.appcocherabyron.services.RegisterService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit getRetrofit(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://lordbyron-dev.azure-api.net/parking-service/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    public static CarsService getPersonaService(){
        CarsService carsService = getRetrofit().create(CarsService.class);
        return carsService;
    }
    public static RegisterService getRegisterService(){
        RegisterService registerService = getRetrofit().create(RegisterService.class);
        return registerService;
    }
}
