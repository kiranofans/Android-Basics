package project.android_projects.com.sslcertificatetesting;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import static project.android_projects.com.sslcertificatetesting.AppConstants.PREF_KEY_ACCESS_SECRET;
import static project.android_projects.com.sslcertificatetesting.AppConstants.PREF_KEY_ACCESS_TOKEN;

public class SharedPreferenceManager extends Application {
    private static String PREF_TAG = SharedPreferenceManager.class.getSimpleName();

    private static SharedPreferences sharedPrefs;
    private static SharedPreferences.Editor prefEditeor;
    private Context mContext;

    private static volatile SharedPreferenceManager prefInstance;

    private static OauthMod.Oauth oauth;

    public SharedPreferenceManager(Context context, String prefFileName){
        sharedPrefs = context.getSharedPreferences(prefFileName,Context.MODE_PRIVATE);
        this.mContext = context;
    }

    public void setAccessToken(String accessToken){
        prefEditeor = sharedPrefs.edit();

        //Storing access token
        prefEditeor.putString(PREF_KEY_ACCESS_TOKEN,accessToken).apply();

    }

    public String getAccessToken(){

        return sharedPrefs.getString(PREF_KEY_ACCESS_TOKEN, "");
    }

    public void setTokenSecret(){
        prefEditeor = sharedPrefs.edit();
        prefEditeor.putString(PREF_KEY_ACCESS_SECRET,"");
    }

    public String getTokenSecret(){
        return sharedPrefs.getString(PREF_KEY_ACCESS_SECRET,oauth.getmSecret());
    }
}
