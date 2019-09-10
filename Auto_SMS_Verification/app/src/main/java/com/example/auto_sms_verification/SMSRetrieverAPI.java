package com.example.auto_sms_verification;

import android.content.Context;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;

public class SMSRetrieverAPI {

    private static volatile GoogleApiClient googleApiClient;
    private Context mContext;
    private SMSRetrieverAPI(Context context){
        mContext = context;
    }
}
