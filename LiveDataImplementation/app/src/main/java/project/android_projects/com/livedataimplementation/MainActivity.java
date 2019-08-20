package project.android_projects.com.livedataimplementation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import Adapter.MyRecyclerViewAdapter;
import Model.NewsMod;
import Utils.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;

    private MainViewModel viewModel;

    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initContent();

        getNewsArticles();
        swipeRefreshListener();//lambda expression
    }

    private void initContent() {
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    private void swipeRefreshListener() {
        swipeRefresh.setOnRefreshListener(() -> {
            getNewsArticles();
        });
    }

    private void getNewsArticles() {
        swipeRefresh.setRefreshing(true);
        viewModel.getAllArticles().observe(this, new Observer<List<NewsMod.ArticleMod>>() {
            @Override
            public void onChanged(List<NewsMod.ArticleMod> articleMods) {
                swipeRefresh.setRefreshing(false);
                initRecyclerView(articleMods);
            }
        });
    }

    private void initRecyclerView(List<NewsMod.ArticleMod> list) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new MyRecyclerViewAdapter(this, list);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
