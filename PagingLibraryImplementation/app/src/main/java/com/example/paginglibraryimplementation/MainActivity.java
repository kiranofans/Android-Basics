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
    private List<NewsMod> newsModList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initContent();
    }

    private void initContent(){
        newsModList = new ArrayList<>();
        pagingAdapter = new PagingAdapter(getApplicationContext(), newsModList);
        recyclerView = (RecyclerView)findViewById(R.id.news_recycler_view);
        NewsViewModel viewModel = ViewModelProviders.of(this).get(NewsViewModel.class);

        viewModel.newsPagedList.observe(this, new Observer<PagedList<NewsMod>>() {
            @Override
            public void onChanged(PagedList<NewsMod> newsModList) {
                pagingAdapter.submitList(newsModList);
                Log.d("TAG",newsModList.toString());
            }
        });

        recyclerView.setAdapter(pagingAdapter);
    }
}
