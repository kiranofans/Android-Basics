package com.example.paginglibraryimplementation;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

public class DataSourceFactory extends DataSource.Factory {
    //creating the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, NewsMod>> newsLiveDataSource = new MutableLiveData<>();

    @Override
    public DataSource<Integer, NewsMod> create() {
        //getting our data source object
        NewsDataSource itemDataSource = new NewsDataSource();

        //posting the datasource to get the values
        newsLiveDataSource.postValue(itemDataSource);

        //returning the datasource
        return itemDataSource;
    }


    //getter for itemlivedatasource
    public MutableLiveData<PageKeyedDataSource<Integer, NewsMod>> getItemLiveDataSource() {
        return newsLiveDataSource;
    }
}
