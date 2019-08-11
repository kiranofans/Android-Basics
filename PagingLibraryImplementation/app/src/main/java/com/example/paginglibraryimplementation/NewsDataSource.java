package com.example.paginglibraryimplementation;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDataSource extends PageKeyedDataSource<Integer,NewsMod> {
    private Context context;

    public static final int PAGE_SIZE = 50;
    private static final int FIRST_PAGE = 1;
    private static final String SITE_NAME = "newsapi";

    private final String API_KEY = "c44b38deba074d9ab0fef1e359c80072";
    private final String BASE_URL = "https://newsapi.org";
    private final String CA_TOP_HEADLINES_URL = BASE_URL+
            "/v2/top-headlines?country=ca&apiKey=" +API_KEY;

    private VolleySingleton volleySingleton;
    private List<NewsMod> newsModList;


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, NewsMod> callback) {

        //Will be called once, load initial data
        RetrofitClient.getInstance().getApi().getArticles(FIRST_PAGE, PAGE_SIZE, SITE_NAME)
                .enqueue(new Callback<NewsMod.ApiResponse>() {
            @Override
            public void onResponse(Call<NewsMod.ApiResponse> call, Response<NewsMod.ApiResponse> response) {
                if(response.body() != null){
                    callback.onResult(response.body().newsList, null, FIRST_PAGE+1);
                }
            }

            @Override
            public void onFailure(Call<NewsMod.ApiResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, NewsMod> callback) {
        //Load previous data
        RetrofitClient.getInstance().getApi().getArticles(params.key, PAGE_SIZE, SITE_NAME)
                .enqueue(new Callback<NewsMod.ApiResponse>() {
                    @Override
                    public void onResponse(Call<NewsMod.ApiResponse> call, Response<NewsMod.ApiResponse> response) {
                        //if the current page is greater than one
                        //we are decrementing the page number
                        //else there is no previous page
                        if(response.body() != null){
                            Integer adjacentKey = (params.key > 1)?params.key -1 : null;
                            callback.onResult(response.body().newsList, adjacentKey);
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsMod.ApiResponse> call, Throwable t) {
                        Log.d("Failure", "Failed to load data");
                    }
                });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, NewsMod> callback) {
        RetrofitClient.getInstance().getApi().getArticles(params.key, PAGE_SIZE, SITE_NAME)
                .enqueue(new Callback<NewsMod.ApiResponse>() {
                    @Override
                    public void onResponse(Call<NewsMod.ApiResponse> call, Response<NewsMod.ApiResponse> response) {
                        //if the current page is greater than one
                        //we are decrementing the page number
                        //else there is no previous page
                        if(response.body() != null){
                            Integer key = response.body().hasMore ? params.key +1 : null;
                            callback.onResult(response.body().newsList, key);
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsMod.ApiResponse> call, Throwable t) {
                        Log.d("Failure", "Failed to load data");
                    }
                });
    }
}
