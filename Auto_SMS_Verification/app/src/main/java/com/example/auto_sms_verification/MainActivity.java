package com.example.auto_sms_verification;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import receiver.SmsBroadcastReceiver;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        OtpReceivedInterface, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient googleApiClient;
    private SmsBroadcastReceiver smsReceiver;
    private static final int RESOLVE_HINT = 2;

    private EditText inputMobileNum, inputOtpCode;
    private TextView tvMobileNum, tvOtpHint;
    private ImageView ivMobileNum, ivOTP;
    private Button btnVerifyOTP, btnGetOTP;

    private LinearLayout layoutVerifyOTP, layoutGetOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initContentView();

        //Set up google api client
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .enableAutoManage(this, this)
                .addApi(Auth.CREDENTIALS_API).build();
        getHintPhoneNum();
    }

    private void setGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) getApplicationContext())
                .enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) this)
                .addApi(Auth.CREDENTIALS_API).build();
    }

    private void initContentView() {
        inputMobileNum = findViewById(R.id.edit_txt_mobile_num);
        inputOtpCode = findViewById(R.id.edit_txt_otp);

        tvMobileNum = findViewById(R.id.txt_view_mobile_num);
        tvOtpHint = findViewById(R.id.txt_view_otp);

        btnVerifyOTP = findViewById(R.id.btn_verify);
        btnGetOTP = findViewById(R.id.btn_get_otp);

        btnGetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSmsListener();
            }
        });
        layoutVerifyOTP = findViewById(R.id.linear_layout_get_OTP);
        layoutGetOTP = findViewById(R.id.linear_layout_verify_OTP);
    }

    private void getHintPhoneNum() {
        HintRequest hintRqst = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true).build();
        PendingIntent pendingIntent = Auth.CredentialsApi.getHintPickerIntent
                (googleApiClient, hintRqst);

        try {
            startIntentSenderForResult(pendingIntent.getIntentSender(),
                    RESOLVE_HINT, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException intentException) {
            intentException.printStackTrace();
        }
    }

    private void startSmsListener(){
        SmsRetrieverClient retrieverClient = SmsRetriever.getClient(this);
        Task<Void> task = retrieverClient.startSmsRetriever();
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                layoutGetOTP.setVisibility(View.GONE);
                layoutVerifyOTP.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this,"SMS Retriever starts",
                        Toast.LENGTH_SHORT).show();
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,"SMS Retriever Error"+
                                e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Result if we want hint number
        if (requestCode == RESOLVE_HINT) {
            if (requestCode == Activity.RESULT_OK) {
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
        Toast.makeText(this, "OTP Received " + otp, Toast.LENGTH_SHORT).show();
        inputOtpCode.setText(otp);
    }

    @Override
    public void onOtpTimeout() {
        Toast.makeText(this, "Timeout, please resend",
                Toast.LENGTH_SHORT).show();
    }
}
