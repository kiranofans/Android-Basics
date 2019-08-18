package com.example.paginglibraryimplementation;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.ArrayList;
import java.util.List;

import Models.ArticleResponse;
import Utils.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDataSource extends PageKeyedDataSource<Integer, ArticleResponse> {

    public static final int PAGE_SIZE = 100;
    private static final int FIRST_PAGE = 1;
    private static final String API_KEY = "c44b38deba074d9ab0fef1e359c80072";

    private RetrofitClient retrofitClient = RetrofitClient.getInstance();

    private List<ArticleResponse> newsModList;
    private Call<NewsMod> call;

    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull final LoadInitialCallback<Integer, ArticleResponse> callback) {
        call = retrofitClient.getApi().getArticlesList
                ("ca", "business", API_KEY, FIRST_PAGE, PAGE_SIZE);

        newsModList = new ArrayList<>();

        //Will be called once, load initial data
        call.enqueue(new Callback<NewsMod>() {
            @Override
            public void onResponse(Call<NewsMod> call, Response<NewsMod> response) {
                if (response.body() != null) {
                    newsModList = response.body().getArticleList();
                    callback.onResult(newsModList, null, FIRST_PAGE + 1);
                }
            }

            @Override
            public void onFailure(Call<NewsMod> call, Throwable t) {
            }
        });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params,
                           @NonNull final LoadCallback<Integer, ArticleResponse> callback) {
        call = retrofitClient.getApi().getArticlesList
                ("ca", "business", API_KEY, FIRST_PAGE, PAGE_SIZE);
        //Load previous data
        call.enqueue(new Callback<NewsMod>() {
            @Override
            public void onResponse(Call<NewsMod> call, Response<NewsMod> response) {
                //if the current page is greater than one
                //we are decrementing the page number
                //else there is no previous page
                if (response.body() != null) {
                    newsModList = response.body().getArticleList();
                    Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                    callback.onResult(newsModList, adjacentKey);
                }
            }

            @Override
            public void onFailure(Call<NewsMod> call, Throwable t) {
                Log.d("Failure", "Failed to load data");
            }
        });
    }

    public void loadAfter(@NonNull final LoadParams<Integer> params,
                          @NonNull final LoadCallback<Integer, ArticleResponse> callback) {
        call = retrofitClient.getApi().getArticlesList
                ("ca", "business", API_KEY, FIRST_PAGE, PAGE_SIZE);
        call.enqueue(new Callback<NewsMod>() {
            @Override
            public void onResponse(Call<NewsMod> call, Response<NewsMod> response) {
                //if the current page is greater than one
                //we are decrementing the page number
                //else there is no previous page

            }

            @Override
            public void onFailure(Call<NewsMod> call, Throwable t) {
                Log.d("Failure", "Failed to load data");
            }
        });
    }
}
