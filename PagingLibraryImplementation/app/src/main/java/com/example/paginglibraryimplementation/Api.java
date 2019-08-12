package com.example.paginglibraryimplementation;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Api {
    @Headers("Accept: application/json")
    @GET("/v2/top-headlines?country=us&category=business")
    Call<NewsMod.ApiResponse> getArticles
            (@Query("api_key")String api_key,@Query("page") int page, @Query("pagesize") int pageSize,
             @Query("site") String site);
}
