package com.example.paginglibraryimplementation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Api {
    @GET("/v2/top-headlines?country=us&category=business")
    Call<List<NewsMod>> getArticlesList
            (@Query("api_key")String api_key,@Query("page") int page,
             @Query("pagesize") int pageSize);
}
