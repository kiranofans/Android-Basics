package project.android_projects.com.sslcertificatetesting;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppConfigManager {
    private RetrofitApi apiService = RetrofitClient.getApiService();

    private static volatile AppConfigManager appConfigMgr;

    private String consumerKey, consumerSecret = "";
    private AppConfigMod.AppConfig appConfig;
    private AppConfigMod appConfigMod;

    private Call<AppConfigMod> consumerKeyCall, consumerSecretCall;

    public static AppConfigManager getInstance(){
        if(appConfigMgr == null){
            synchronized (AppConfigManager.class){
                if(appConfigMgr == null) appConfigMgr = new AppConfigManager();
            }
            appConfigMgr = new AppConfigManager();
        }
        return appConfigMgr;
    }

    public void getRequestToken(final Context context){

        Call<AppConfigMod> appConfigCall = apiService.getRequestTokens("android");

        appConfigCall.enqueue(new Callback<AppConfigMod>() {
            @Override
            public void onResponse(Call<AppConfigMod> call, Response<AppConfigMod> response) {
                appConfigMod= response.body();
                if(response.isSuccessful() && appConfigMod != null){
                    consumerKey = appConfigMod.appConfig.getConsumerKey();
                    consumerSecret = appConfigMod.appConfig.getConsumerSecret();
                    Log.d("TAG", "Consumer Key: "+consumerKey+
                            "\nSecret: "+consumerSecret+"\nTime stamp: "
                            +System.currentTimeMillis()/1000);


                    //Pack multiple values into one object and return the object variable
                    appConfigMod.appConfig.setConsumerKey(consumerKey);
                    appConfigMod.appConfig.setConsumerSecret(consumerSecret);
                }
            }

            @Override
            public void onFailure(Call<AppConfigMod> call, Throwable t) {

            }
        });
        //return appConfigMod.appConfig;
    }

    private void oAuthRequestHeader(Context context,String apiURL){
        long time = System.currentTimeMillis();

    }
}
