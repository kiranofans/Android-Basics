package Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

public class LocalManager{
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({LAN_CODE_ENGLISH, LAN_CODE_FRENCH, LAN_CODE_TRADITIONAL_CHINESE})
    public @interface LocaleDef {
        String[] SUPPORTED_LOCALES_LANGUAGES = {LAN_CODE_ENGLISH, LAN_CODE_FRENCH, LAN_CODE_TRADITIONAL_CHINESE};
    }

    public static final String LAN_CODE_ENGLISH = "en";
    public static final String LAN_CODE_FRENCH = "fr";
    public static final String LAN_CODE_TRADITIONAL_CHINESE = "zh-Hant";

    //SharedPreferences Key
    private static final String PREF_KEY_LANGUAGES = "pref_key_languages";

    //Set current preferences local
    public static Context setCurrentLocale(Context context){
        return updateResources(context,getLanguagePrefs(context));
    }

    public static Context setNewLocale(Context context,@LocaleDef String lan){
        setLanguagePrefs(context, lan);
        return updateResources(context, lan);
    }

    public static String getLanguagePrefs(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(PREF_KEY_LANGUAGES, LAN_CODE_ENGLISH);
    }

    public static void setLanguagePrefs(Context context,String langLocaleKey){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(PREF_KEY_LANGUAGES,langLocaleKey).apply();
    }

    private static Context updateResources(Context context, String languages){
        Locale locale = new Locale(languages);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if(Build.VERSION.SDK_INT >= 17){
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        }else{
            config.setLocale(locale);
            res.updateConfiguration(config,res.getDisplayMetrics());
        }
        return context;
    }

    //Get current locale language
    public static Locale getLocale(Resources res){
        Configuration config = res.getConfiguration();
        return Build.VERSION.SDK_INT >= 24 ? config.getLocales().get(0) :config.locale;
        //locale is deprecated

    }


}



