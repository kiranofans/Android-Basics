package Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;

    public static synchronized RetrofitApi getApiService(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(ApiConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(RetrofitApi.class);
    }
}
