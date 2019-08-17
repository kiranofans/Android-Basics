package com.example.paginglibraryimplementation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

public class NewsViewModel extends ViewModel {
    LiveData<PagedList<ArticleResponse>> newsPagedList;
    LiveData<PageKeyedDataSource<Integer,ArticleResponse>> newsLiveDataSource;

    public NewsViewModel(){
        NewsDataSourceFactory newsDataFactory = new NewsDataSourceFactory();

        newsLiveDataSource = newsDataFactory.getNewsLiveDataSource();

        PagedList.Config pagingConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false).setPageSize(NewsDataSource.PAGE_SIZE).build();

        newsPagedList = (new LivePagedListBuilder(newsDataFactory,pagingConfig)).build();
    }

}
