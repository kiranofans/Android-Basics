package project.android_projects.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import ApiService.ApiConstants;
import ApiService.Retrofit2ApiService;
import ApiService.Retrofit2Client;
import Model.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Retrofit2ApiService apiService;

    private Button testBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String url = ApiConstants.GITHUB_BASE_URL+ApiConstants.GITHUB_USER_IDENTITY_URL
                +"?client_id"+ApiConstants.GITHUB_CLIENT_ID+"&scope=repo&redirect_uri="
                +ApiConstants.GITHUB_CALLBACK_URL;

        testBtn=findViewById(R.id.testBtn);

        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        //getGitUserIdentity();
    }

    private void getGitUserIdentity(){
        apiService= Retrofit2Client.getGitRetrofitClient();
        retrofit2.Call<Users> usersCall = apiService.getGitUserIdentity
                (ApiConstants.GITHUB_CLIENT_ID,
                        ApiConstants.GITHUB_CALLBACK_URL);
        usersCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if(response.body() != null){
                    Log.d("Response:",response.body().getLogin());
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });
    }
}
