package project.android_projects.com.ux_design_final_googledrive;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.CreateFileActivityOptions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.events.DriveEventService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_CAPTURE_IMAGE = 1;
    private static final int REQUEST_CODE_CREATOR = 2;
    private static final int REQUEST_CODE_SIGN_IN = 0;
    private static final int CHOOSING_IMG_REQ = 1234;

    private GoogleApiClient apiClient;
    private DriveClient driveClient;
    private DriveResourceClient driveResClient;
    private GoogleSignInClient signInClient;
    private DriveContents contents;
    private DriveEventService driveEventService;

    private Bitmap bitmapToSave;
    private TextView emailTv, usernameTV;
    private Button signOutBtn,addAccount,googleAcct;
    private ImageView avatarImg,displayImg;
    private ImageButton cameraBtn,createBtn;
    private Button uploadBtn,downloadBtn;


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
        cameraBtn=(ImageButton)findViewById(R.id.cameraBtn);
        createBtn=(ImageButton)findViewById(R.id.createBtn);
        uploadBtn=(Button)findViewById(R.id.uploadBtn);
        downloadBtn=(Button)findViewById(R.id.downloadBtn);

        usernameTV=(TextView)findViewById(R.id.usernameTxt);
        emailTv=(TextView)findViewById(R.id.emailTxt);

        setUserInfo();
        buttonOnclicks();
        // getSignInResult();
    }
    private void getRootFolder(){
        driveResClient.getRootFolder();
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
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSignInResult();
            }
        });
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               saveFileToDrive();
            }
        });
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChoosingFile();
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


    private GoogleSignInAccount getSignInResult(){
        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);
        Intent intent=getIntent();
        int resultCode=intent.getIntExtra("RESULT_CODE",-1);
        int requestCode=intent.getIntExtra("REQ_CODE", 0);

        if(RESULT_OK==resultCode&&account!=null){
            if(REQUEST_CODE_CAPTURE_IMAGE==requestCode){
                // Store the image data as a bitmap for writing later.
                bitmapToSave = (Bitmap) intent.getExtras().get("data");
                saveFileToDrive();
            }else if(requestCode==REQUEST_CODE_SIGN_IN){
                driveClient= Drive.getDriveClient(this,
                        GoogleSignIn.getLastSignedInAccount(this));
                driveResClient=Drive.getDriveResourceClient(this,
                        GoogleSignIn.getLastSignedInAccount(this));
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),
                        REQUEST_CODE_CAPTURE_IMAGE );
            }else if(requestCode==REQUEST_CODE_CREATOR){
                bitmapToSave = null;
                // Just start the camera again for another photo.
                startActivityForResult(
                        new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_CODE_CAPTURE_IMAGE);
            }
        }
        return account;
    }
    private void saveFileToDrive(){
        final Bitmap image =bitmapToSave;
       driveResClient.createContents().continueWithTask
               (task -> createFileIntentSender(task.getResult(), image))
               .addOnFailureListener(e-> Log.w("Tag",
                       "Failed to create new content",e));
    }
    private Task<Void> createFileIntentSender(DriveContents driveContents, Bitmap image) {
        Log.i("CREATE", "New contents created.");
        // Get an output stream for the contents.
        OutputStream outputStream = driveContents.getOutputStream();
        // Write the bitmap data from it.
        ByteArrayOutputStream bitmapStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, bitmapStream);
        try {
            outputStream.write(bitmapStream.toByteArray());
        } catch (IOException e) {
            Log.w("IO", "Unable to write file contents.", e);
        }

        // Create the initial metadata - MIME type and title.
        // Note that the user will be able to change the title later.
        MetadataChangeSet metadataChangeSet =
                new MetadataChangeSet.Builder()
                        .setMimeType("image/jpeg")
                        .setTitle("Android Photo.png")
                        .build();
        // Set up options to configure and display the create file activity.
        CreateFileActivityOptions createFileActivityOptions =
                new CreateFileActivityOptions.Builder()
                        .setInitialMetadata(metadataChangeSet)
                        .setInitialDriveContents(driveContents)
                        .build();

        return driveClient
                .newCreateFileActivityIntentSender(createFileActivityOptions)
                .continueWith(
                        task -> {
                            startIntentSenderForResult(task.getResult(), REQUEST_CODE_CREATOR, null, 0, 0, 0);
                            return null;
                        });
    }
    public void showChoosingFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        displayImg=(ImageView)findViewById(R.id.uploadingImgView);

        startActivityForResult(Intent.createChooser(intent, "Select Image"), CHOOSING_IMG_REQ);
        displayImg.setImageResource(CHOOSING_IMG_REQ);

    }

    /*private void setUploadBtn(){
        File file=new File("image/*");
        java.io.File filePath = new java.io.File("image/*");
        FileContent  mediaContent = new FileContent.get("image/jpeg", filePath);
        file = .files().insert(file, mediaContent)
                .setFields("id")
                .execute();
        saveFileToDrive();
    }*/
    protected void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /*@Override
    protected void onDriveClientReady() {

    }*/

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

