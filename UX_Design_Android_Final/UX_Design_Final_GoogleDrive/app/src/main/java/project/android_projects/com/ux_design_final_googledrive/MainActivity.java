package project.android_projects.com.ux_design_final_googledrive;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_CAPTURE_IMAGE = 1;
    private static final int REQUEST_CODE_CREATOR = 2;
    private GoogleApiClient apiClient;
    private DriveClient driveClient;
    private DriveResourceClient driveResClient;

    private GoogleSignInClient signInClient;

    private Bitmap bitmapToSave;
    private TextView emailTv, usernameTV;
    private Button signOutBtn,addAccount,googleAcct;
    private ImageView avatarImg;
    private static String userName,userEmail,imgUrl;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signOutBtn=(Button)findViewById(R.id.signOutBtn);
        addAccount=(Button)findViewById(R.id.addAcctBtn);
        googleAcct=(Button)findViewById(R.id.googleAcctPageBtn);
        avatarImg=(ImageView)findViewById(R.id.imgProfilePic);

        usernameTV=(TextView)findViewById(R.id.usernameTxt);
        emailTv=(TextView)findViewById(R.id.emailTxt);

        setUserInfo();
        buttonOnclicks();
    }
    private void buttonOnclicks(){
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://accounts.google.com/signin/v2/identifier?service=ahsid&continue=https%3A%2F%2Fdevelopers.google.com%2Fidentity%2Fsign-in%2Fandroid%2Fsign-in&flowName=GlifWebSignIn&flowEntry=AddSession")));
            }
        });
        googleAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://myaccount.google.com/?utm_source=OGB&utm_medium=act")));
            }
        });
    }
    private void signOut() {
        signInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    }
                });
    }

    private void setUserInfo(){
        intent=getIntent();
        userName=intent.getStringExtra("USER_NAME");
        userEmail=intent.getStringExtra("USER_EMAIL");
        imgUrl=intent.getStringExtra("IMG_URL");

        usernameTV.setText(userName); emailTv.setText(userEmail);
        Glide.with(MainActivity.this).load(imgUrl)
                .thumbnail(0.5f).crossFade().diskCacheStrategy
                (DiskCacheStrategy.ALL).into(avatarImg);
    }
}

