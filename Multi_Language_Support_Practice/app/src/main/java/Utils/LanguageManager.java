package Utils;

import android.content.Context;

import java.util.Observable;

import static Utils.LocalManager.LAN_CODE_ENGLISH;

public class LanguageManager extends Observable {
    private static volatile LanguageManager mgrInstance;

    private String currentSysLanguage = LAN_CODE_ENGLISH;
    private Context engContext, cnContext,frContext;

    private boolean isInitialized = false;

    String LAN_ENGLISH = "English";
    String LAN_FRENCH = "Francais";
    String LAN_CHINESE_T = "繁體中文";

    public static LanguageManager getInstance(){
        if(mgrInstance == null){
            mgrInstance = new LanguageManager();
            synchronized (LanguageManager.class){
                if(mgrInstance==null) mgrInstance = new LanguageManager();
            }
        }
        return mgrInstance;
    }
    /*public static String getString(@StringRes int strResId){
        return getString(strResId,currentSysLanguage);
    }*/

}
