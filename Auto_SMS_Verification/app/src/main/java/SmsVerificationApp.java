import android.app.Application;

import Helpers.AppSignatureHelper;

public class SmsVerificationApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppSignatureHelper appSignatureHelper = new AppSignatureHelper(this);
        appSignatureHelper.getAppSignature();
    }
}
