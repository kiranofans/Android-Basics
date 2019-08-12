package com.example.paginglibraryimplementation;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

public class NewsDataSourceFactory extends DataSource.Factory {
    private MutableLiveData<PageKeyedDataSource<Integer, NewsMod>> newsLiveDataSource = new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource<Integer, NewsMod> create() {
        //getting our data source object
        NewsDataSource newsDataSource = new NewsDataSource();

        //posting the datasource to get the values
        newsLiveDataSource.postValue(newsDataSource);

        //returning the datasource
        return newsDataSource;
    }

    //getter for newsliveDataSource
    public MutableLiveData<PageKeyedDataSource<Integer, NewsMod>> getNewsLiveDataSource(){
        return newsLiveDataSource;
    }
}
