package com.example.paginglibraryimplementation;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDataSource extends PageKeyedDataSource<Integer,NewsMod> {
    private Context context;

    public static final int PAGE_SIZE = 50;
    private static final int FIRST_PAGE = 1;
    private static final String SITE_NAME = "newsapi";
    private static final String API_KEY = "c44b38deba074d9ab0fef1e359c80072";

    private final String BASE_URL = "https://newsapi.org";
    private final String CA_TOP_HEADLINES_URL = BASE_URL+
            "/v2/top-headlines?country=ca&apiKey=" +API_KEY;

    private RetrofitClient retrofitClient = RetrofitClient.getInstance();

    private List<NewsMod> newsModList;


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, NewsMod> callback) {

        //Will be called once, load initial data
        retrofitClient.getApi().getArticlesList(API_KEY,FIRST_PAGE, PAGE_SIZE)
                .enqueue(new Callback<List<NewsMod>>() {
            @Override
            public void onResponse(Call<List<NewsMod>> call, Response<List<NewsMod>> response) {
                if(response.body() != null){
                    newsModList = response.body();
                    callback.onResult(newsModList, null, FIRST_PAGE+1);
                }
            }

            @Override
            public void onFailure(Call<List<NewsMod>> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params,
                           @NonNull final LoadCallback<Integer,NewsMod> callback) {
        //newsModList = new ArrayList<>();
        //Load previous data
        RetrofitClient.getInstance().getApi().getArticlesList(API_KEY,params.key, PAGE_SIZE)
                .enqueue(new Callback<List<NewsMod>>() {
                    @Override
                    public void onResponse(Call<List<NewsMod>> call, Response<List<NewsMod>> response) {
                        //if the current page is greater than one
                        //we are decrementing the page number
                        //else there is no previous page
                        if(response.body() != null){
                            newsModList=response.body();
                            Integer adjacentKey = (params.key > 1)?params.key -1 : null;
                            callback.onResult(newsModList, adjacentKey);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<NewsMod>> call, Throwable t) {
                        Log.d("Failure", "Failed to load data");
                    }
                });
    }

    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, NewsMod> callback) {
        RetrofitClient.getInstance().getApi().getArticlesList(API_KEY,params.key, PAGE_SIZE)
                .enqueue(new Callback<List<NewsMod>>() {
                    @Override
                    public void onResponse(Call<List<NewsMod>> call, Response<List<NewsMod>> response) {
                        //if the current page is greater than one
                        //we are decrementing the page number
                        //else there is no previous page

                    }

                    @Override
                    public void onFailure(Call<List<NewsMod>> call, Throwable t) {
                        Log.d("Failure", "Failed to load data");
                    }
                });
    }
}
