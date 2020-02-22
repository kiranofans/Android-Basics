package com.example.paginglibraryimplementation.Network;

import com.example.paginglibraryimplementation.Data.Models.NewsMod;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    String ENDPOINT_EVERYTHING="/v2/everything";

    @GET(ENDPOINT_EVERYTHING)
    Call<NewsMod> getArticlesList
            (@Query("language") String language, @Query("q") String q,
             @Query("apiKey") String api_key, @Query("page") long page,
             @Query("pageSize") int pageSize);
}
