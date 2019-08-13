package project.android_projects.com.retrofit2implementation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

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

    private NewsModResponse newsMod;
    private NewsModResponse.Article articleMod;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initContent(this);
    }

    private void initContent(final Context context){
        loadData(context);
    }

    private void loadData(final Context context){
        resultList = new ArrayList<>();
        service = ApiUtils.getService();
        Call<NewsModResponse> call = service.getArticlesList("us",
                "business", ApiConstants.API_KEY);

        call.enqueue(new Callback<NewsModResponse>() {
            @Override
            public void onResponse(Call<NewsModResponse> call, Response<NewsModResponse> response) {
                if(response.isSuccessful()){
                    resultList = response.body().getArticleList();//get the list
                    for(int i = 0; i< resultList.size(); i++){
                        Object obj = response.body().getArticleList().get(i);
                        String title = ((NewsModResponse.Article) obj).getTitle();
                        String imgUrl = ((NewsModResponse.Article) obj).getUrlToImage();

                        articleMod = new NewsModResponse.Article(title, imgUrl);

                        resultList.add(articleMod);
                    }

                    generateList(resultList, context);
                }

            }

            @Override
            public void onFailure(Call<NewsModResponse> call, Throwable t) {

            }
        });
    }

    private void generateList(List<NewsModResponse.Article> list, Context context){
        adapter = new MyRecyclerViewAdapter(context,resultList);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }
}
