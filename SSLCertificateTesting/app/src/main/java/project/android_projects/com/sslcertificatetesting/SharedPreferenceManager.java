package project.android_projects.com.sslcertificatetesting;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager extends Application {
    private static String PREF_TAG = SharedPreferenceManager.class.getSimpleName();

    private static SharedPreferences sharedPrefs;
    private static SharedPreferences.Editor prefEditeor;
    private Context mContext;

    private static volatile SharedPreferenceManager prefInstance;

    public SharedPreferenceManager(Context context, String prefFileName){
        sharedPrefs = context.getSharedPreferences(prefFileName,Context.MODE_PRIVATE);
        this.mContext = context;
    }

    public String getAccessToken(){
        prefEditeor = sharedPrefs.edit();

        //Storing access token
        prefEditeor.putString("")
    }
}
