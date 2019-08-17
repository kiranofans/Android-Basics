package com.example.paginglibraryimplementation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PagingAdapter pagingAdapter;
    private NewsMod newsMod;
    private PagedList<ArticleResponse> newsModList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initContent();
    }

    private void initContent(){
        recyclerView = (RecyclerView)findViewById(R.id.news_recycler_view);
        NewsViewModel viewModel = ViewModelProviders.of(this).get(NewsViewModel.class);

        viewModel.newsPagedList.observe(this, new Observer<PagedList<ArticleResponse>>() {
            @Override
            public void onChanged(PagedList<ArticleResponse> newsList) {
                pagingAdapter.submitList(newsList);
                newsModList=newsList;
                //Log.d("TAG",newsModList.toString());
            }
        });
        pagingAdapter = new PagingAdapter(this,newsModList);

        recyclerView.setAdapter(pagingAdapter);
    }
}
