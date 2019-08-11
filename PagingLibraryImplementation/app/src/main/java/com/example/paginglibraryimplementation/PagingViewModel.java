package com.example.paginglibraryimplementation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PagingViewModel extends AndroidViewModel {

    private Executor executor;
    private LiveData<PagedList<NewsMod>> newsPagedList;

    public PagingViewModel(@NonNull Application application) {
        super(application);

        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(2)
                .setPageSize(4)
                .setPrefetchDistance(4)
                .build();

        executor = Executors.newFixedThreadPool(5);

        newsPagedList = (new LivePagedListBuilder<Long, NewsMod>(factory,config)).setFetchExecutor(executor)
                .build();
    }

}
