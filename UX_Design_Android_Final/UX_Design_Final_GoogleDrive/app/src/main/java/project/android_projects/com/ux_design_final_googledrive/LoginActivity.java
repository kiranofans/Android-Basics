package project.android_projects.com.ux_design_final_googledrive;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.OpenFileActivityOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>,GoogleApiClient.OnConnectionFailedListener{
    private static final int REQUEST_CODE_SIGN_IN = 0;
    private static final int REQUEST_READ_CONTACTS = 0;
    private static final int REQUEST_CODE_CAPTURE_IMAGE = 1;
    private static final int REQUEST_CODE_OPEN_ITEM=2;

    private static GoogleSignInOptions googleSignOption;
    private static GoogleSignInClient signInClient;
    private static GoogleApiClient apiClient;
    private static GoogleSignInAccount account;
    private static GoogleSignInResult result;
    private GoogleAuthUtil googleAuthToken;
    private DriveClient driveClient;
    private DriveResourceClient driveResClient;
    private TaskCompletionSource<DriveId> openItemTask;

    private ProgressDialog dialog;
    private LinearLayout linearLayout;

    private SignInButton googleSignIn;
    private static ImageView googleLogo;
    private UserLoginTask mAuthTask = null;
    private static String accEmail;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        googleLogo=(ImageView)findViewById(R.id.googleDriveLogo);
        linearLayout=(LinearLayout)findViewById(R.id.login_form);
        // Set up the login form.
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.emailAutoComplete);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        loginBtn();


    }
    /** Build a Google SignIn client. */
    private Intent buildGoogleSignInClient() {
        String serverClientId="825171040809-jm692qdajf6vbssr69m5udukot76sf20.apps.googleusercontent.com";
        googleSignOption = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(Drive.SCOPE_FILE)
               // .requestScopes(Drive.SCOPE_APPFOLDER)
              //  .requestServerAuthCode(serverClientId)
                .requestEmail().requestProfile().build();

        signInClient=GoogleSignIn.getClient(this, googleSignOption);

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignOption)
                .build();
       // startActivityForResult(signInClient.getSignInIntent(),REQUEST_CODE_SIGN_IN);
        return Auth.GoogleSignInApi.getSignInIntent(apiClient);
    }
    /** Start sign in activity. */
    protected void signIn() {
      /*  Set<Scope> requiredScopes=new HashSet<>(2);
        requiredScopes.add(Drive.SCOPE_FILE);
        requiredScopes.add(Drive.SCOPE_APPFOLDER);
        account=GoogleSignIn.getLastSignedInAccount(this);*/
        /*if(account!=null&&account.getGrantedScopes().containsAll(requiredScopes)){
            initializeDriveClient(account);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }else{
            buildGoogleSignInClient();
        }*/
        Intent signIn = buildGoogleSignInClient();
        startActivityForResult(signIn, REQUEST_CODE_SIGN_IN);
    }
    private void loginBtn(){
        googleSignIn = (SignInButton) findViewById(R.id.loginBtn);
        googleSignIn.setSize(SignInButton.SIZE_STANDARD);
        googleSignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
                if(mAuthTask!=null){
                    signIn();
                    Toast.makeText(getApplicationContext(),"Login Succeed",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }
            }
        });
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        googleSignOption =new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
       /* if(!email.equals("kiranofans111@gmail.com")){
            Toast.makeText(getApplicationContext(),
                    "Oops! Can't find this google account",Toast.LENGTH_SHORT).show();
        }*/
        //return (email.contains("@")&& email.equals(accEmail)? true :false);
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        String pattern = "([a-zA-Z0-9].{5,40})";
        if (password.length() < 6||password.equals("")) {
            Toast.makeText(this,
                    "Password length must be greater than or equal to 6 characters",
                    Toast.LENGTH_LONG).show();
        } else if (!password.matches(pattern)) {
            Toast.makeText(this,
                    "Password does not contain any character other than alphabets and numbers",
                    Toast.LENGTH_LONG).show();
        }
        return password.matches(pattern);

    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       // account=GoogleSignIn.getLastSignedInAccount(this);
        //Task<GoogleSignInAccount> getAccountTask=GoogleSignIn.getSignedInAccountFromIntent(data);
        if (requestCode == REQUEST_CODE_SIGN_IN) {
            //initializeDriveClient(account);
            /*if(getAccountTask.isSuccessful()){
                initializeDriveClient(getAccountTask.getResult());
            }else{
                Log.d("SIGN","Sign-in failed");

            }*/
            try {
                result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                account = result.getSignInAccount();
                Intent sendData = new Intent(LoginActivity.this, MainActivity.class);
                String name, email, imgUrl = "";
                name = account.getDisplayName();
                email = account.getEmail();
                resultCode=RESULT_OK;
                Log.d("EMAIL","Email: "+email);
                accEmail=email;
                imgUrl = account.getPhotoUrl().toString();

               // sendData.putExtra("PERMISSION", )
                sendData.putExtra("REQ_CODE",requestCode);
                sendData.putExtra("RESULT_CODE",resultCode);
                sendData.putExtra("USER_NAME", name);
                sendData.putExtra("USER_EMAIL", email);
                sendData.putExtra("IMG_URL", imgUrl);

                startActivity(sendData);

            } catch (Exception e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else{
            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
        }
        /*if(requestCode==REQUEST_CODE_OPEN_ITEM){
            if(resultCode==RESULT_OK){
                DriveId driveId=data.getParcelableExtra
                        (OpenFileActivityOptions.EXTRA_RESPONSE_DRIVE_ID);
                openItemTask.setResult(driveId);
            }else{
                openItemTask.setException(new RuntimeException("Unable to open file"));
            }

        }*/
        //super.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("conn",connectionResult.isSuccess()+"");
    }
    private void showProgressDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setMessage("Loading...");
            dialog.setIndeterminate(true);
        }

        dialog.show();
    }

    private void hideProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.hide();
        }
    }
    private void initializeDriveClient(GoogleSignInAccount signInAccount) {
        driveClient = Drive.getDriveClient(this, signInAccount);
        driveResClient = Drive.getDriveResourceClient(getApplicationContext(), signInAccount);
       // onDriveClientReady();
    }

   /* @Override
    protected void onDriveClientReady() {

    }*/

    protected DriveClient getDriveClient() {
        return driveClient;
    }

    protected DriveResourceClient getDriveResourceClient() {
        return driveResClient;
    }
}

