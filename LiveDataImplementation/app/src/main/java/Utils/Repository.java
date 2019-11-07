package Utils;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import Model.NewsMod;
import Retrofit2Service.Retrofit2_API;
import Retrofit2Service.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    /**
     * Create a Repository class to interacting to LiveData
     *
     * A repository is a architecture component that saves as a data store
     * for all of the application. It's the complete data model for the app,
     * which provides simple data modification & retrieval APIs.
     *
     * Repository gets data by coordinating to fetch, sync, and persisting from different
     * data source
     */

    private List<NewsMod.ArticleMod> articleList = new ArrayList<>();
    private MutableLiveData<List<NewsMod.ArticleMod>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public Repository(Application application) {
        this.application = application;
    }


    public MutableLiveData<List<NewsMod.ArticleMod>> getMutableLiveData() {
        Retrofit2_API apiService = RetrofitClient.getRetofitApi();

        Call<NewsMod> callEverything = apiService.getEverything(APIConstants.API_KEY,
                100, APPConstants.PARAM_SORT_BY);

        Call<NewsMod> callHeadLines = apiService.getTopHeadLines
                (APPConstants.PARAM_COUNTRY_USA, APPConstants.PARAM_CATEGORY_BUSINESS,
                        APIConstants.API_KEY, 100);

        callHeadLines.enqueue(new Callback<NewsMod>() {
            @Override
            public void onResponse(Call<NewsMod> call, Response<NewsMod> response) {
                NewsMod newsMod = response.body();
                if (newsMod != null && newsMod.getArticleList() != null) {
                    articleList = newsMod.getArticleList();
                    mutableLiveData.setValue(articleList);
                }
                Log.d("CHECK NULL", response.body()+" is null");
            }

            @Override
            public void onFailure(Call<NewsMod> call, Throwable t) {

            }
        });

        return mutableLiveData;
    }
}
