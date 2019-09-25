package project.android_projects.com.sslcertificatetesting;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import static project.android_projects.com.sslcertificatetesting.ApiConstants.ENDPOINT_STORE_LIST;

public interface RetrofitApi {

    @Headers("Accept:application/json")
    @GET(ApiConstants.ENDPOINT_PINGING)
    Call<PingingModel> pingServer(@Field("oauth_consumer_key") String consumerKey,
                                  @Field("oauth_nonce") String nonce,
                                  @Field("oauth_signature_method")String signatureMethod,
                                  @Field("oauth_timestamp")String timeStamp,
                                  @Field("oauth_version")String oAuthVersion,
                                  @Field("oauth_token") String accessToken,
                                  @Field("oauth_secret") String consumerSecret);

    @GET(ApiConstants.ENDPOINT_APP_CONFIG)
    Call<AppConfigMod> getSys(@Query("sys") String system);

    @POST(ApiConstants.ENDPOINT_LOGIN)
    Call<OauthMod> login(@Query("email")String email, @Query("password")String password,
                               @Query("country_code")String countryCode);

    @GET(ENDPOINT_STORE_LIST)
    Call<StoreListMod.Store> getStoreList(/*@Query("sort_id") int sortId, @Query("limit") int pageSize,
                                    @Query("page") int numOfPages, */@Query("lan")String language,
                                    @Query("c_id") int categoryId);


}
