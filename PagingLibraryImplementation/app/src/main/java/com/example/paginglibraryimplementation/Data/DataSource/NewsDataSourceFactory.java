package com.example.paginglibraryimplementation.Data.DataSource;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.example.paginglibraryimplementation.Data.Models.ArticleResponse;


public class NewsDataSourceFactory extends DataSource.Factory {
    private MutableLiveData<PageKeyedDataSource<Long, ArticleResponse>> newsLiveDataSource =
            new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource<Long, ArticleResponse> create() {
        //getting our data source object
        NewsDataSource newsDataSource = new NewsDataSource();

        //posting the datasource to get the values
        newsLiveDataSource.postValue(newsDataSource);

        //returning the datasource
        return newsDataSource;
    }

    //getter for newsliveDataSource
    public MutableLiveData<PageKeyedDataSource<Long, ArticleResponse>> getNewsLiveDataSource() {
        return newsLiveDataSource;
    }
}
