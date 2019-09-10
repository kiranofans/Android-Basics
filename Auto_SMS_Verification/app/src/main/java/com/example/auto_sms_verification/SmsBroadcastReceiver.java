package com.example.auto_sms_verification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;

public class SmsBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = SmsBroadcastReceiver.class.getSimpleName();
    OtpReceivedInterface otpReceivedInterface = null;

    public void setOnOtpListeners(OtpReceivedInterface receivedInterface){
        otpReceivedInterface = receivedInterface;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG,"onReceive: ");
        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            Bundle bundle = intent.getExtras();
            Status status = (Status) bundle.get(SmsRetriever.EXTRA_STATUS);

            switch (status.getStatusCode()){
                case CommonStatusCodes.SUCCESS:
                    //Get SMS message contents
                    String msg = (String) bundle.get(SmsRetriever.EXTRA_SMS_MESSAGE);
                    Log.d(TAG, "onReceive Failure "+msg);

                    if(otpReceivedInterface != null){
                        String otpMsg = msg.replace("<#> Your one-time password code is: ","");
                        otpReceivedInterface.onOtpReceived(otpMsg);
                    }
                break;
                case CommonStatusCodes.TIMEOUT:
                    //Waiting for SMS timed out (5 minutes)
                    Log.d(TAG, "onReceive failure");
                    if(otpReceivedInterface != null){
                        otpReceivedInterface.onOtpTimeout();
                    }
                break;
            }
        }
    }
}
