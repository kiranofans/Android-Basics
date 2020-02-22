package com.example.paginglibraryimplementation.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paginglibraryimplementation.Data.Models.ArticleResponse;
import com.example.paginglibraryimplementation.R;
import com.example.paginglibraryimplementation.ViewModel.NewsViewModel;
import com.example.paginglibraryimplementation.Adapter.PagingAdapter;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PagingAdapter pagingAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initContent();
    }

    private void initContent() {
        recyclerView = (RecyclerView) findViewById(R.id.news_recycler_view);

        //Can add all the accessible methods or functions in NewsViewModel.class to this line
        NewsViewModel viewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//must add this line
        pagingAdapter = new PagingAdapter(this);

        viewModel.newsPagedList.observe(this, new Observer<PagedList<ArticleResponse>>() {
            @Override
            public void onChanged(PagedList<ArticleResponse> newsList) {
                pagingAdapter.submitList(newsList);
            }
        });

        recyclerView.setAdapter(pagingAdapter);
    }
}
