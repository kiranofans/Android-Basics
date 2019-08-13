package project.android_projects.com.retrofit2implementation;

import java.util.List;

import Model.NewsModResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitAPIService {
    @GET(ApiConstants.BUSINESS_HEADLINES_URL)
    Call<NewsModResponse> getArticlesList(@Query("country")String country,
                                                        @Query("category") String category,
                                                        @Query("apiKey")String apiKey);

}
