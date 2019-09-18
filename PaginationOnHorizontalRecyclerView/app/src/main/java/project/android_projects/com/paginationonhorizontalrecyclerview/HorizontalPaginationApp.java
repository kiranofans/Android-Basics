package project.android_projects.com.paginationonhorizontalrecyclerview;

import android.app.Application;

public class HorizontalPaginationApp extends Application {
    private static HorizontalPaginationApp mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
    }

    public static HorizontalPaginationApp getContext(){
        return mContext;
    }
}
