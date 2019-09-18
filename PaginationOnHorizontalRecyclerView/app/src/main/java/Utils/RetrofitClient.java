package Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import project.android_projects.com.paginationonhorizontalrecyclerview.HorizontalPaginationApp;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private Retrofit retrofit;

    public RetrofitApi getApiService(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(RetrofitApi.class);
    }

    private OkHttpClient provideOkHttpClient(){
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(30,TimeUnit.SECONDS);

        httpClientBuilder.addInterceptor(new ConnectionInterceptor() {
            @Override
            public boolean isInternetAvailable() {
                return RetrofitClient.this.isNetworkAvailable(HorizontalPaginationApp.getContext());
            }

            @Override
            public void onInternetUnavailable() {
                RxJavaConnectionListener.getInstance().notifyNetworkChange(false);
            }
        });
        return httpClientBuilder.build();
    }

    private boolean isNetworkAvailable(Context context){
        ConnectivityManager conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    }
}
