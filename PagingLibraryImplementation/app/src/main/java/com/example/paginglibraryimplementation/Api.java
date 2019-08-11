package com.example.paginglibraryimplementation;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("articles")
    Call<NewsMod.ApiResponse> getArticles(@Query("page") int page,@Query("pagesize") int pageSize,
                                          @Query("site") String site);
}
