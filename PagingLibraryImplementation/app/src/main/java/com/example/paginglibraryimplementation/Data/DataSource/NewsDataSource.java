package com.example.paginglibraryimplementation.Data.DataSource;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.paginglibraryimplementation.Data.Models.*;

import java.util.ArrayList;
import java.util.List;

import com.example.paginglibraryimplementation.Network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDataSource extends PageKeyedDataSource<Long, ArticleResponse> {
    /***
     * DataSource is a architecture component that contains API to the data sources
     * e.g.: Retrofit for a REST API, Room for local persistence (SQLite), and External
     * content providers for OS
     * **/

    public static final int PAGE_SIZE = 2;
    private static final long FIRST_PAGE = 1;
    private static final String API_KEY = "6051104d60c2436bbee3352d5554addb";

    private RetrofitClient retrofitClient = RetrofitClient.getInstance();

    private List<ArticleResponse> articleList;
    private Call<NewsMod> call;

    public void loadInitial(@NonNull LoadInitialParams<Long> params,
                            @NonNull final LoadInitialCallback<Long, ArticleResponse> callback) {
        call = retrofitClient.getApi().getArticlesList
                ("en", "apple", API_KEY, FIRST_PAGE, PAGE_SIZE);

        articleList = new ArrayList<>();

        //Will be called once, load initial data
        call.enqueue(new Callback<NewsMod>() {
            @Override
            public void onResponse(Call<NewsMod> call, Response<NewsMod> response) {
                if (response.body() != null) {
                    articleList = response.body().getArticleList();
                    callback.onResult(articleList, null, FIRST_PAGE + 1);
                }
            }

            @Override
            public void onFailure(Call<NewsMod> call, Throwable t) {
                Log.d("Failure", "Failed to load data\n"+"Cause: "+t.getCause()
                        +"\nMsg:"+t.getMessage());
            }
        });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Long> params,
                           @NonNull final LoadCallback<Long, ArticleResponse> callback) {
        call = retrofitClient.getApi().getArticlesList
                ("en", "apple", API_KEY, params.key, PAGE_SIZE);
        //Load previous data
        call.enqueue(new Callback<NewsMod>() {
            @Override
            public void onResponse(Call<NewsMod> call, Response<NewsMod> response) {
                //if the current page is greater than one
                //we are decrementing the page number
                //else there is no previous page
                if (response.body() != null) {

                    articleList = response.body().getArticleList();
                    long adjacentKey;
                    if(params.key > 1){
                        adjacentKey = params.key -1;
                    }else{
                        adjacentKey =0;
                    }
                    callback.onResult(articleList, adjacentKey);
                }
            }

            @Override
            public void onFailure(Call<NewsMod> call, Throwable t) {
                Log.d("Failure", "Failed to load data\n"+"Cause: "+t.getCause()
                        +"\nMsg:"+t.getMessage());
            }
        });
    }

    public void loadAfter(@NonNull final LoadParams<Long> params,
                          @NonNull final LoadCallback<Long, ArticleResponse> callback) {
        call = retrofitClient.getApi().getArticlesList
                ("en", "apple", API_KEY, params.key, PAGE_SIZE);
        call.enqueue(new Callback<NewsMod>() {
            @Override
            public void onResponse(Call<NewsMod> call, Response<NewsMod> response) {
                //if the current page is greater than one
                //we are decrementing the page number
                //else there is no previous page
                NewsMod newsMod = response.body();
                if(newsMod!=null){
                    articleList=newsMod.getArticleList();
                    callback.onResult(articleList,params.key+1);
                }
            }

            @Override
            public void onFailure(Call<NewsMod> call, Throwable t) {
                Log.d("Failure", "Failed to load data\n"+"Cause: "+t.getCause()
                        +"\nMsg:"+t.getMessage());
            }
        });
    }
}
