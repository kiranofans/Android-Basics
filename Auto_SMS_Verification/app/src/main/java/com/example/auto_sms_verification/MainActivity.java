package com.example.auto_sms_verification;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.widget.EditText;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
OtpReceivedInterface,GoogleApiClient.OnConnectionFailedListener{
    private GoogleApiClient googleApiClient;
    private static final int RESOLVE_HINT = 1;

    private EditText inputMobileNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void setGoogleApiClient(){
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) getApplicationContext())
                .enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) this)
                .addApi(Auth.CREDENTIALS_API).build();
    }

    private void initContentView(){
        inputMobileNum = findViewById(R.id.txt_input_mobile_num);

    }
    private void getHintPhoneNum(){
        setGoogleApiClient();
        HintRequest hintRqst = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true).build();
        PendingIntent pendingIntent = Auth.CredentialsApi.getHintPickerIntent
                (googleApiClient,hintRqst);

        try{
            startIntentSenderForResult(pendingIntent.getIntentSender(),
                    RESOLVE_HINT,null,0,0,0);
        }catch (IntentSender.SendIntentException intentException){
            intentException.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Result if we want hint number
        if(requestCode == RESOLVE_HINT){
            if(requestCode == Activity.RESULT_OK){
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                // credential.getId(); <-- will need to process phone number string
                inputMobileNum.setText(credential.getId());
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onOtpReceived(String otp) {

    }

    @Override
    public void onOtpTimeout() {

    }
}
