package project.android_projects.com.multi_language_support_practice;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import Utils.LocalManager;

public class MultiLanguageAPP extends Application {
    /** Set locales */

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocalManager.setCurrentLocale(base));
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocalManager.setCurrentLocale(this);
    }
}
