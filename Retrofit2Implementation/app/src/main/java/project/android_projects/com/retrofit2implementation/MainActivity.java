package project.android_projects.com.retrofit2implementation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Adapter.MyRecyclerViewAdapter;
import Model.NewsModResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private MyRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    private RetrofitAPIService service;
    private List<NewsModResponse.Article> resultList;

    private NewsModResponse.Article articleMod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();
    }

    private void loadData() {
        resultList = new ArrayList<>();

        service = ApiUtils.getService();
        Call<NewsModResponse> call = service.getArticlesList("ca",
                50, "business", ApiConstants.API_KEY);//need to

        call.enqueue(new Callback<NewsModResponse>() {
            @Override
            public void onResponse(Call<NewsModResponse> call, Response<NewsModResponse> response) {
                if (response.isSuccessful()) {
                    resultList = response.body().getArticleList();//get the list
                    generateList(resultList);

                    /*for (int i = 0; i < resultList.size(); i++) {
                     *//**Can also use a for loop and Object class to get certain
                     * objects from this articles array*//*
                        Object obj = response.body().getArticleList().get(i);
                        String title = ((NewsModResponse.Article) obj).getTitle();
                        String imgUrl = ((NewsModResponse.Article) obj).getUrlToImage();

                        articleMod = new NewsModResponse.Article(title, imgUrl);

                    }*/
                    //resultList.add(articleMod);
                }
            }

            @Override
            public void onFailure(Call<NewsModResponse> call, Throwable t) {

            }
        });
    }

    private void generateList(List<NewsModResponse.Article> list) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        adapter = new MyRecyclerViewAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);
    }
}
