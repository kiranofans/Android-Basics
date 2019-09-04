package ApiService;

import Model.Users;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Retrofit2ApiService {

    @GET(ApiConstants.GITHUB_USER_IDENTITY_URL)
    Call<Users> getGitUserIdentity(@Query("client_id") String clientID,
                                   @Query("redirect_uri")String callBackUrl);

    @POST(ApiConstants.GITHUB_ACCESS_TOKEN_URL)
    Call<Users> getGitAccessToken(@Query("client_id") String clientID,
                                  @Query("client_secret") String clientSecret,
                                  @Query("code") String responseCode);
}
