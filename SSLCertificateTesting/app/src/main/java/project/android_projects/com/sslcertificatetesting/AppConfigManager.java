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

    private String consumerKey, consumerSecret, accessToken,tokenSecret = "";
    private AppConfigMod.AppConfig appConfig;
    private AppConfigMod appConfigMod;

    private SharedPreferenceManager prefsMgr;

    //Retrofit calls
    private Call<AppConfigMod> consumerKeyCall, consumerSecretCall;
    private Call<StoreListMod.Store> storeListCall;
    private Call<ConsumerLogin> loginCall;

    private OauthMod.Oauth oauth;
    private OauthMod oauthMod;

    private ConsumerLogin consumerLogin;
    private LoginRequestBodyMod requestBodyMod;
    private Records records;

    public static AppConfigManager getInstance() {
        if (appConfigMgr == null) {
            synchronized (AppConfigManager.class) {
                if (appConfigMgr == null) appConfigMgr = new AppConfigManager();
            }
            appConfigMgr = new AppConfigManager();
        }
        return appConfigMgr;
    }

    public void getStoreList(final Context context, RetrofitApi apiService) {
        //Don't need to send access token and secret for this API

        prefsMgr = new SharedPreferenceManager(context, PREF_FILE_GLOBAL);
        storeListCall = apiService.getStoreList("en", 4);

        storeListCall.enqueue(new Callback<StoreListMod.Store>() {
            @Override
            public void onResponse(Call<StoreListMod.Store> call, Response<StoreListMod.Store> response) {
                if (response.isSuccessful() && response.body() != null) {
                    StoreListMod.Store storeMod = response.body();
                    Toast.makeText(context, "Store Name: " + storeMod.getStoreName(),
                            Toast.LENGTH_LONG).show();
                    Log.d("TAG", "Response from url: " + PUBLIC_BASE_URL +
                            ApiConstants.ENDPOINT_STORE_LIST + "\n"
                            + response.body().getLogo());
                }
            }

            @Override
            public void onFailure(Call<StoreListMod.Store> call, Throwable t) {

            }
        });
    }

    public void pingToCheckTokenValidity(Context context, RetrofitApi apiService) {
        //The access token and secret is in login api
        prefsMgr = new SharedPreferenceManager(context, PREF_FILE_GLOBAL);

        consumerLogin(context,apiService);
        getRequestToken(context,apiService);

        long time = System.currentTimeMillis();
        consumerKey = prefsMgr.getConsumerKey();
        consumerSecret = prefsMgr.getConsumerSecret();
        accessToken = prefsMgr.getAccessToken();
        tokenSecret = prefsMgr.getTokenSecret();

        Call<PingingModel> pingCall = apiService.pingServer(consumerKey,
                String.format("%s%08x%05x", "", time / 1000, time), "HMAC-SHA1",
                (time / 1000) + "", "1.0", accessToken, consumerSecret);
        pingCall.enqueue(new Callback<PingingModel>() {
            @Override
            public void onResponse(Call<PingingModel> call, Response<PingingModel> response) {
                if (response.isSuccessful()) {
                    Log.d("PING_TAG", "Response Code: " + response.body().getRC());
                }
            }

            @Override
            public void onFailure(Call<PingingModel> call, Throwable t) {

            }
        });
    }

    public void getRequestToken(final Context context, RetrofitApi apiService) {
        //Oauth 1.0 tokens and secrets
        prefsMgr = new SharedPreferenceManager(context, PREF_FILE_GLOBAL);
        Call<AppConfigMod> appConfigCall = apiService.getSys("android");

        appConfigCall.enqueue(new Callback<AppConfigMod>() {
            @Override
            public void onResponse(Call<AppConfigMod> call, Response<AppConfigMod> response) {
                appConfigMod = response.body();
                if (response.isSuccessful() && appConfigMod != null) {
                    Long time = System.currentTimeMillis()/1000;
                    String consumerKey = appConfigMod.appConfig.getConsumerKey();
                    String consumerSecret = appConfigMod.appConfig.getConsumerSecret();

                    prefsMgr.setConsumerKey(consumerKey);
                    prefsMgr.setConsumerSecret(consumerSecret);

                    Log.d("TAG", "Consumer Key: " + consumerKey +
                            "\nSecret: " + consumerSecret + "\nAccess Token: "
                            + prefsMgr.getAccessToken() + "\nAccess Secret:" +
                            prefsMgr.getTokenSecret()+"\nTime: "+time);
                }
            }

            @Override
            public void onFailure(Call<AppConfigMod> call, Throwable t) {

            }
        });
    }

    public void consumerLogin(final Context context, RetrofitApi apiService) {
        prefsMgr = new SharedPreferenceManager(context, PREF_FILE_GLOBAL);

        loginCall = apiService.login(new LoginRequestBodyMod("sakurala111@yahoo.com", "123456"));
        loginCall.enqueue(new Callback<ConsumerLogin>() {
            @Override
            public void onResponse(Call<ConsumerLogin> call, Response<ConsumerLogin> response) {
                consumerLogin = response.body();
                if (response.isSuccessful() && consumerLogin != null) {
                    if (consumerLogin.getRC() == 200) {
                        String accessToken = consumerLogin.getRecords().getToken();
                        String accessSecret = consumerLogin.getRecords().getSecret();

                        prefsMgr.setAccessToken(accessToken);
                        prefsMgr.setTokenSecret(accessSecret);

                        Toast.makeText(context, "Access Token: " + accessToken
                                        + "\nAccess Secret: " + accessSecret,
                                Toast.LENGTH_LONG).show();
                        Log.d("TAG_LOGIN", "Access Token: " + accessToken
                                + "\nAccess Secret: " + accessSecret);

                    }

                }
            }

            @Override
            public void onFailure(Call<ConsumerLogin> call, Throwable t) {

            }
        });
    }
}
