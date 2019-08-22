package project.android_projects.com.paginationonhorizontalrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import Adapter.MyPaginationAdatper;
import Adapter.PaginationScrollListener;
import Models.NewsMod;
import Utils.AppConstants;
import Utils.RetrofitApi;
import Utils.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static Utils.ApiConstants.API_KEY;
import static Utils.AppConstants.PAGE_START;
import static android.nfc.tech.MifareUltralight.PAGE_SIZE;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyPaginationAdatper adapter;
    private LinearLayoutManager linearLayoutMgr;

    private RetrofitApi retrofitApi;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 3;
    private boolean isLoading = false;
    int itemCount  = 0;

    private List<NewsMod.ArticleMod> articleModlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        articleModlist = new ArrayList<>();

        initRecyclerView();
        //loadItem();
    }

    private void loadItem(){
        retrofitApi = RetrofitClient.getApiService();
        Call<NewsMod> call = retrofitApi.getTopHeadLines("ca",
                "business", API_KEY,currentPage, AppConstants.PAGE_SIZE);

        call.enqueue(new Callback<NewsMod>() {
            @Override
            public void onResponse(Call<NewsMod> call, Response<NewsMod> response) {
                NewsMod newsMod = response.body();
                if (newsMod != null){
                    if (currentPage != PAGE_START) {
                        articleModlist = newsMod.getArticleList();
                       // adapter.removeLoading();
                        adapter.addAll(articleModlist);
                    }

                    if (currentPage < totalPage) {
                        adapter.addLoading();
                    } else {
                        isLastPage = true;
                        isLoading = false;
                    }
                }
            }

            @Override
            public void onFailure(Call<NewsMod> call, Throwable t) {

            }
        });

    }
    private void initRecyclerView(){
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new MyPaginationAdatper(this,articleModlist);
        linearLayoutMgr = new LinearLayoutManager(this);
        linearLayoutMgr.setOrientation(RecyclerView.HORIZONTAL);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(linearLayoutMgr);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutMgr) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                loadItem();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

    }

}
