package com.example.paginglibraryimplementation;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import java.lang.invoke.MutableCallSite;

public class NewsDataFactory extends DataSource.Factory {
    private MutableLiveData<PageKeyedDataSource<Integer, NewsMod>> itemLiveDataSource = new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource<Integer, NewsMod> create() {
        return null;
    }
}
