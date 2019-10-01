package project.android_projects.com.sslcertificatetesting;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import static project.android_projects.com.sslcertificatetesting.AppConstants.PREF_KEY_ACCESS_SECRET;
import static project.android_projects.com.sslcertificatetesting.AppConstants.PREF_KEY_ACCESS_TOKEN;
import static project.android_projects.com.sslcertificatetesting.AppConstants.PREF_KEY_CONSUMER_KEY;
import static project.android_projects.com.sslcertificatetesting.AppConstants.PREF_KEY_CONSUMER_SECRET;

public class SharedPreferenceManager extends Application {
    private static String PREF_TAG = SharedPreferenceManager.class.getSimpleName();

    private static SharedPreferences sharedPrefs;
    private static SharedPreferences.Editor prefEditor;
    private Context mContext;

    private static volatile SharedPreferenceManager prefInstance;

    private static Records loginRecords;

    public SharedPreferenceManager(Context context, String prefFileName){
        sharedPrefs = context.getSharedPreferences(prefFileName,Context.MODE_PRIVATE);
        this.mContext = context;
    }

    public void setAccessToken(String accessToken){
        prefEditor = sharedPrefs.edit();

        //Storing access token
        prefEditor.putString(PREF_KEY_ACCESS_TOKEN,accessToken).apply();

    }

    public String getAccessToken(){

        return sharedPrefs.getString(PREF_KEY_ACCESS_TOKEN, "");
    }

    public void setTokenSecret(String tokenSecret){
        prefEditor = sharedPrefs.edit();
        prefEditor.putString(PREF_KEY_ACCESS_SECRET,tokenSecret).apply();
    }

    public String getTokenSecret(){
        return sharedPrefs.getString(PREF_KEY_ACCESS_SECRET,"");
    }

    public String getConsumerKey(){
        return sharedPrefs.getString(PREF_KEY_CONSUMER_KEY,"");
    }
    public void setConsumerKey(String consumerKey){
        prefEditor = sharedPrefs.edit();
        prefEditor.putString(PREF_KEY_CONSUMER_KEY,consumerKey).apply();
    }

    public String getConsumerSecret(){
        return sharedPrefs.getString(PREF_KEY_CONSUMER_SECRET,"");
    }
    public void setConsumerSecret(String consumerSecret){
        prefEditor = sharedPrefs.edit();
        prefEditor.putString(PREF_KEY_CONSUMER_SECRET,consumerSecret).apply();
    }
}
