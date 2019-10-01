package project.android_projects.com.sslcertificatetesting;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static volatile Retrofit retrofit;

    private static final String BASE_URL = " https://api.goopter.com";

    private static OkHttpClient okHttpClient;

    public static RetrofitApi getApiService(){
        okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient).build();
        }
        return retrofit.create(RetrofitApi.class);
    }


    public static void createOauth(){

    }
}
