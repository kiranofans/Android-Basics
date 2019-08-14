package com.example.paginglibraryimplementation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("/v2/top-headlines")
    Call<ArticleResponse> getArticlesList
            (@Query("country")String country,@Query("category") String category,
             @Query("apiKey")String api_key,@Query("page") int page,
             @Query("pageSize") int pageSize);
}
