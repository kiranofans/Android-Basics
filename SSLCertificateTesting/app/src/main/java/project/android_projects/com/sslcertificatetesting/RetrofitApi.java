package project.android_projects.com.sslcertificatetesting;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

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
    Call<AppConfigMod> getRequestTokens(@Query("sys") String system);

}
