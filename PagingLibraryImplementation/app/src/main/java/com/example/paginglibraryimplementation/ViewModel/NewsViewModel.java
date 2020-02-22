package com.example.paginglibraryimplementation.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.example.paginglibraryimplementation.Data.DataSource.NewsDataSource;
import com.example.paginglibraryimplementation.Data.DataSource.NewsDataSourceFactory;
import com.example.paginglibraryimplementation.Data.Models.ArticleResponse;

public class NewsViewModel extends ViewModel {
    public LiveData<PagedList<ArticleResponse>> newsPagedList;
    public MutableLiveData<PageKeyedDataSource<Long, ArticleResponse>> newsLiveDataSource;

    /***viewModels prepare and keep the data for the UI including LiveData,
     * and Observables, survive configuration change, and it is the gateway for the UI controller.
     * The UI controllers (activity and fragment) reach the com.example.paginglibraryimplementation.ViewModel to reach the rest of the application.
     * So it's a data store for UI controllers*/
    public NewsViewModel() {
        NewsDataSourceFactory newsDataFactory = new NewsDataSourceFactory();

        newsLiveDataSource = newsDataFactory.getNewsLiveDataSource();

        PagedList.Config pagingConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false).setPageSize(NewsDataSource.PAGE_SIZE).build();

        newsPagedList = new LivePagedListBuilder<>(newsDataFactory, pagingConfig).build();
    }

}
