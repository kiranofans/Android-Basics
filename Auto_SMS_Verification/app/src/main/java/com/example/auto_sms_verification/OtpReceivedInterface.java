package com.example.auto_sms_verification;

public interface OtpReceivedInterface {
    //A One-Time Password listener to send the OTP to activity or fragment
    void onOtpReceived(String otp);
    void onOtpTimeout();
}
