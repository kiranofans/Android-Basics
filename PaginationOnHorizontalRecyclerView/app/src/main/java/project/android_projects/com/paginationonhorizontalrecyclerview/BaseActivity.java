package project.android_projects.com.paginationonhorizontalrecyclerview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import Utils.RetrofitClient;
import Utils.RxJavaConnectionListener;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {
    private RetrofitClient retrofitClient;
    private CompositeDisposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofitClient = new RetrofitClient();
        disposable = new CompositeDisposable();
        addInternetConnectionListener();
    }

    RetrofitClient getRetrofitClient(){
        return retrofitClient;
    }

    private void addInternetConnectionListener(){
        disposable.add(RxJavaConnectionListener.getInstance().listenNetworkChange()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>(){
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        onInternetUnavailable();
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    protected void onInternetUnavailable(){
        Toast.makeText(getApplicationContext(),"Internet Unavailable", Toast.LENGTH_SHORT).show();
    }

    private View getView(){
        return findViewById(android.R.id.content);
    }
}
