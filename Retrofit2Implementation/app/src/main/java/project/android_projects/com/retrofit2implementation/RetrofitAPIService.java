package project.android_projects.com.retrofit2implementation;

import Model.NewsModResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitAPIService {
    @GET(ApiConstants.BUSINESS_HEADLINES_URL)
    Call<NewsModResponse> getArticlesList(@Query("country")String country,
                                          @Query("pageSize") int pageSize,
                                          @Query("category")String category,
                                          @Query("apiKey")String apiKey);
    //the ones to query are the parameters in request body

}
