package com.example.paginglibraryimplementation;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private final String API_KEY = "c44b38deba074d9ab0fef1e359c80072";
    private final String BASE_URL = "https://newsapi.org";
    private final String CA_TOP_HEADLINES_URL = BASE_URL+
            "/v2/top-headlines?country=ca&apiKey=" +API_KEY;

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
