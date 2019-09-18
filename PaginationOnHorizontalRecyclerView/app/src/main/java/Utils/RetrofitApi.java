package Utils;

import Models.NewsMod;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitApi {

    @GET(ApiConstants.ENDPOINT_TOP_HEADLINES)
    Call<NewsMod> getTopHeadLines(@Query("country") String country,
                                  @Query("category") String category,
                                  @Query("apiKey") String api_key,
                                  @Query("page") Integer page, @Query("pageSize") int pageSize);
}

