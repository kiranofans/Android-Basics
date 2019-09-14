package project.android_projects.com.multi_language_support_practice;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import Utils.LocalManager;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resetTitles();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocalManager.setCurrentLocale(newBase));
    }

    protected void resetTitles(){
        try{
            ActivityInfo activityInfo = getPackageManager()
                    .getActivityInfo(getComponentName(),PackageManager.GET_META_DATA);
            if(activityInfo.labelRes != 0){
                setTitle(activityInfo.labelRes);
            }
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
    }
}
