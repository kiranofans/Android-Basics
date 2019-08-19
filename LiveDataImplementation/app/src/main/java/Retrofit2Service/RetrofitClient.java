package Retrofit2Service;

import Utils.APIConstants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static synchronized Retrofit2_API getRetofitApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(APIConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(Retrofit2_API.class);
    }
}
