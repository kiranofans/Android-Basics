package project.android_projects.com.sslcertificatetesting;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import static project.android_projects.com.sslcertificatetesting.ApiConstants.ENDPOINT_STORE_LIST;

public interface RetrofitApi {
    @Headers({"Content-Type:application/json",
    "User-Agent:http.agent"})
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

    @Headers("Content-Type:application/json")
    @POST(ApiConstants.ENDPOINT_LOGIN)
        //If you don't know what annotation to use, just put raw JSON request body
    Call<ConsumerLogin> login(@Body LoginRequestBodyMod requestBody);
    //ConsumerLogin is the POST response, and LoginRequestBodyMod is the POST request model class

    @GET(ENDPOINT_STORE_LIST)
    Call<StoreListMod.Store> getStoreList(/*@Query("sort_id") int sortId, @Query("limit") int pageSize,
                                    @Query("page") int numOfPages, */@Query("lan")String language,
                                    @Query("c_id") int categoryId);


}
