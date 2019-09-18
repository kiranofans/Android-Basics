package project.android_projects.com.sslcertificatetesting;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitApi {

    @GET(ApiConstants.ENDPOINT_PINGING)
    Call<PingingModel> pingServer();

}
