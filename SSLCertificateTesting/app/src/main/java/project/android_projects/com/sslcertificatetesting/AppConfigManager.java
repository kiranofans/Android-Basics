package project.android_projects.com.sslcertificatetesting;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static project.android_projects.com.sslcertificatetesting.ApiConstants.PUBLIC_BASE_URL;
import static project.android_projects.com.sslcertificatetesting.AppConstants.PREF_FILE_GLOBAL;

public class AppConfigManager {
    //private RetrofitApi apiService = RetrofitClient.getApiService();

    private static volatile AppConfigManager appConfigMgr;

    private String consumerKey, consumerSecret = "";
    private AppConfigMod.AppConfig appConfig;
    private AppConfigMod appConfigMod;

    private SharedPreferenceManager prefsMgr;

    private Call<AppConfigMod> consumerKeyCall, consumerSecretCall;
    private Call<StoreListMod.Store> storeListCall;

    private OauthMod.Oauth oauth;

    public static AppConfigManager getInstance(){
        if(appConfigMgr == null){
            synchronized (AppConfigManager.class){
                if(appConfigMgr == null) appConfigMgr = new AppConfigManager();
            }
            appConfigMgr = new AppConfigManager();
        }
        return appConfigMgr;
    }

    public void getStoreList(final Context context, RetrofitApi apiService){
        //Don't need to send access token and secret for this API

        prefsMgr = new SharedPreferenceManager(context,PREF_FILE_GLOBAL);

        storeListCall = apiService.getStoreList(102, 20,
                1, "en",4);

        storeListCall.enqueue(new Callback<StoreListMod.Store>() {
            @Override
            public void onResponse(Call<StoreListMod.Store> call, Response<StoreListMod.Store> response) {
                if(response.isSuccessful()){
                    StoreListMod.Store storeMod = response.body();
                    Toast.makeText(context,"Store Name: "+ storeMod.getStoreName(),
                            Toast.LENGTH_LONG).show();
                    Log.d("Response: ","Url:"+PUBLIC_BASE_URL+ApiConstants.ENDPOINT_STORE_LIST+"\n"
                    +response.body());
                }
            }

            @Override
            public void onFailure(Call<StoreListMod.Store> call, Throwable t) {

            }
        });
    }

    public void pingToCheckTokenValidity(Context context, RetrofitApi apiService){
        //The access token and secret is in login api
        prefsMgr = new SharedPreferenceManager(context,PREF_FILE_GLOBAL);
        long time = System.currentTimeMillis();
        Call<PingingModel> pingCall = apiService.pingServer(consumerKey,
                String.format("%s%08x%05x", "", time / 1000, time),"HMAC-SHA1",
                (time/1000)+"","1.0",oauth.getAccessToken(), consumerSecret);
        pingCall.enqueue(new Callback<PingingModel>() {
            @Override
            public void onResponse(Call<PingingModel> call, Response<PingingModel> response) {
                if(response.isSuccessful()){
                    Log.d("PING_TAG", "Access Token: "+oauth.getAccessToken());
                }
            }

            @Override
            public void onFailure(Call<PingingModel> call, Throwable t) {

            }
        });
    }

    public void getRequestToken(final Context context, RetrofitApi apiService){
        prefsMgr = new SharedPreferenceManager(context ,PREF_FILE_GLOBAL);
        Call<AppConfigMod> appConfigCall = apiService.getSys("android");

        appConfigCall.enqueue(new Callback<AppConfigMod>() {
            @Override
            public void onResponse(Call<AppConfigMod> call, Response<AppConfigMod> response) {
                appConfigMod= response.body();
                if(response.isSuccessful() && appConfigMod != null){
                    consumerKey = appConfigMod.appConfig.getConsumerKey();
                    consumerSecret = appConfigMod.appConfig.getConsumerSecret();
                    prefsMgr.setAccessToken(oauth.getAccessToken());

                    Log.d("TAG", "Consumer Key: "+consumerKey+
                            "\nSecret: "+consumerSecret);


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
