package com.example.paginglibraryimplementation.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private final String BASE_URL = "https://newsapi.org";

    private Retrofit retrofit;
    private static RetrofitClient retrofitClient;

    private RetrofitClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public static synchronized RetrofitClient getInstance(){
        return retrofitClient = new RetrofitClient();
    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }
}
