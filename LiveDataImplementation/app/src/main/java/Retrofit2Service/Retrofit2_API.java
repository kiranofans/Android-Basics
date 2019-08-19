package Retrofit2Service;

import Model.NewsMod;
import Utils.APIConstants;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Retrofit2_API {

    @GET(APIConstants.END_POINT_TOP_HEADLINES)
    Call<NewsMod> getTopHeadLines(@Query("country") String country, @Query("category") String category,
                                  @Query("apiKey") String api_key, @Query("pageSize") String pageSize);

    @GET(APIConstants.END_POINT_EVERYTHING)
    Call<NewsMod> getEverything(@Query("apiKey") String api_key, @Query("pageSize") String pageSize,
                                @Query("sortBy") String sort_by);
}
