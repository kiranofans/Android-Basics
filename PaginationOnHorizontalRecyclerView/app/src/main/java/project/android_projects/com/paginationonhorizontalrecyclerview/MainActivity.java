package project.android_projects.com.paginationonhorizontalrecyclerview;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Adapter.MyPaginationAdatper;
import Adapter.PaginationScrollListener;
import Models.NewsMod;
import Utils.AppConstants;
import Utils.RetrofitApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static Utils.ApiConstants.API_KEY;
import static Utils.ApiConstants.RC_REQUIRE_UPGRADE;
import static Utils.AppConstants.PAGE_START;

public class MainActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private MyPaginationAdatper adapter;
    private LinearLayoutManager linearLayoutMgr;

    private RetrofitApi retrofitApi;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 3;
    private boolean isLoading = false;
    int itemCount = 0;

    private List<NewsMod.ArticleMod> articleModlist;

    private TextView noContentTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        noContentTV = findViewById(R.id.txtView_no_content);

        retrofitApi = getRetrofitClient().getApiService();

        articleModlist = new ArrayList<>();

        initRecyclerView();
    }

    private void loadItem() {
        Call<NewsMod> call = retrofitApi.getTopHeadLines("ca",
                "business", API_KEY, currentPage, AppConstants.PAGE_SIZE);

        call.enqueue(new Callback<NewsMod>() {
            @Override
            public void onResponse(Call<NewsMod> call, Response<NewsMod> response) {
                NewsMod newsMod = response.body();
                if (newsMod != null) {
                    recyclerView.setVisibility(View.VISIBLE);
                    noContentTV.setVisibility(View.GONE);
                    if (currentPage != PAGE_START) {
                        articleModlist = newsMod.getArticleList();
                        adapter.removeLoading();
                        adapter.addAll(articleModlist);
                    }

                    if (currentPage < totalPage) {
                        adapter.addLoading();
                    } else {
                        isLastPage = true;
                        isLoading = false;
                    }
                } else if (response.code() == RC_REQUIRE_UPGRADE) {//Require upgrade code
                    recyclerView.setVisibility(View.GONE);
                    noContentTV.setVisibility(View.VISIBLE);
                    noContentTV.setText(R.string.too_many_request_within_24_hours);
                }
            }

            @Override
            public void onFailure(Call<NewsMod> call, Throwable t) {

            }
        });

    }

    private void initRecyclerView() {
        noContentTV.setVisibility(View.GONE);
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new MyPaginationAdatper(this, articleModlist);
        linearLayoutMgr = new LinearLayoutManager(this);
        linearLayoutMgr.setOrientation(RecyclerView.HORIZONTAL);

        //recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(linearLayoutMgr);
        recyclerView.setAdapter(adapter);

        loadItem();

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

    public void onRetry(View view) {
        loadItem();
    }

    @Override
    protected void onInternetUnavailable() {
        super.onInternetUnavailable();
        recyclerView.setVisibility(View.GONE);
        noContentTV.setVisibility(View.VISIBLE);
        noContentTV.setText("No internet connection");
    }
}
