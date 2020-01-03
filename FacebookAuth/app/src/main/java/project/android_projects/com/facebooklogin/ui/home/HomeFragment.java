package project.android_projects.com.facebooklogin.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import project.android_projects.com.facebooklogin.R;

public class HomeFragment extends Fragment {
    private static final String homeTag = HomeFragment.class.getSimpleName();

    private HomeViewModel homeViewModel;

    private TextView textView;
    private ImageView fbProfilePicView;

    private LoginButton fbLoginBtn;
    private CallbackManager fbCallbackMgr;
    private String EMAIL = "email";
    private AccessToken accessToken;

    private Fragment homeFragment;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(homeTag, "onCreate()");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d(homeTag, "onCreateView");

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(homeTag,"onViewCreated()");

        textView = view.findViewById(R.id.email_text);
        fbLoginBtn = view.findViewById(R.id.btn_fb_login);
        fbProfilePicView = view.findViewById(R.id.fb_pic_view);

        FacebookSdk.sdkInitialize(FacebookSdk.getApplicationContext());

        accessToken = AccessToken.getCurrentAccessToken();

        fbLoginBtn.setPermissions(Arrays.asList(EMAIL, "public_profile"));

        /** If you are using in a fragment, call loginButton.setFragment(this);
         * Otherwise, the onActivityResult won't be called.
         * if onActivityResult is not called, the access token will be null */
        fbLoginBtn.setFragment(this);

        fbCallbackMgr = CallbackManager.Factory.create();
        checkLoginStatus();
        loginCallback();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(homeTag, "onActivityCreated()");
        //checkLoginStatus();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(homeTag, "onStart()");
        checkLoginStatus();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(homeTag,"onResume()");
        checkLoginStatus();

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(homeTag, "onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(homeTag,"onStop");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(homeTag,"onDestroyView()");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(homeTag,"onAttach()");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(homeTag,"onDetach()");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Pass login result
        fbCallbackMgr.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loginCallback() {

        LoginManager.getInstance().registerCallback(fbCallbackMgr, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                accessToken = loginResult.getAccessToken();
                homeViewModel.loadUserProfile(accessToken);
//                Toast.makeText(getContext(), "Logged in\n" + "Access token:\n" + accessToken,
//                        Toast.LENGTH_LONG).show();
                Log.d(homeTag, loginResult.toString() + " Logged in successfully");
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void checkLoginStatus() {
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        accessTokenTracker.startTracking();
        if (isLoggedIn) {

            homeViewModel.loadUserProfile(accessToken).observe(this, new Observer<List<String>>() {
                @Override
                public void onChanged(List<String> strings) {

                    textView.setText(strings.get(1));
                    Glide.with(getContext()).load("http://graph.facebook.com/"+strings.get(0)
                            +"/picture?type=normal").override(300, 300)
                            .circleCrop().into(fbProfilePicView);
                }
            });
            //loadUserProfile(accessToken);
        } else {
            fbProfilePicView.setVisibility(View.GONE);
            textView.setText("Logged Out From Facebook");
            /*Toast.makeText(getContext(), "Check login status:" +
                            "\nUser logged out from Facebook", Toast.LENGTH_SHORT).show();*/
        }
    }

    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                   AccessToken currentAccessToken) {
            if (currentAccessToken == null) {
                textView.setText("Logged Out from Facebook");
                fbProfilePicView.setImageResource(0);
                fbProfilePicView.setVisibility(View.GONE);
            } else
                fbProfilePicView.setVisibility(View.VISIBLE);
                homeViewModel.loadUserProfile(accessToken);
        }
    };

    /*public void loadUserProfile(AccessToken newAccessToken) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    if (response != null) {

                       // Toast.makeText(getContext(), "response OK", Toast.LENGTH_SHORT).show();
                        String email = object.getString("email");
                        String profilePicUrl = "http://graph.facebook.com/" + object.getString("id")
                                + "/picture?type=normal";

                        homeViewModel.setEmail(email);
                        homeViewModel.setProfilePicUrl(profilePicUrl);
                        //textView.setText(email);

                        Glide.with(getContext()).load(profilePicUrl).override(300, 300)
                                .circleCrop().into(fbProfilePicView);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle bundle = new Bundle();
        bundle.putString("fields", "email,id");
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }*/
}