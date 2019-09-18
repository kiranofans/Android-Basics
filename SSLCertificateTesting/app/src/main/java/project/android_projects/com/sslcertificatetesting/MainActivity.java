package project.android_projects.com.sslcertificatetesting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static project.android_projects.com.sslcertificatetesting.ApiConstants.RC_ACCESS_DENIED;
import static project.android_projects.com.sslcertificatetesting.ApiConstants.RC_OK;
import static project.android_projects.com.sslcertificatetesting.ApiConstants.RC_TOKEN_REJECTED;

public class MainActivity extends AppCompatActivity {
    private Button btnTest;

    private RetrofitApi apiService;
    private Call<PingingModel> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initContent();
    }

    private void initContent(){
        apiService = RetrofitClient.getApiService();

        btnTest = findViewById(R.id.btn_test);
        setBtnClicks();
    }

    private void setBtnClicks(){
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testSSL();
            }
        });
    }

    private void testSSL(){
        call = apiService.pingServer();
        call.enqueue(new Callback<PingingModel>() {
            @Override
            public void onResponse(Call<PingingModel> call, Response<PingingModel> response) {
                if(response.isSuccessful() && response.body() != null){//Response successful
                    if(response.body().getRC() == RC_ACCESS_DENIED){
                        Toast.makeText(MainActivity.this,
                                "Access Denied",Toast.LENGTH_LONG).show();//can't access token
                    }else if(response.body().getRC() == RC_TOKEN_REJECTED){
                        Toast.makeText(MainActivity.this,
                                "Token Rejected",Toast.LENGTH_LONG).show();//Oauth token problem
                    }else if(response.body().getRC() == RC_OK){
                        Toast.makeText(MainActivity.this,
                                response.body().getRC()+": OK",Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(MainActivity.this,
                            "Error RC: "+response.body().getRC(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PingingModel> call, Throwable t) {

            }
        });
    }
}
