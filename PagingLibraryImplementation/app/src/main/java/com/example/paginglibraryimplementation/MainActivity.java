package com.example.paginglibraryimplementation;

import androidx.appcompat.app.AppCompatActivity;
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
import java.util.Observer;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PagingAdapter pagingAdapter;
    private NewsMod newsMod;
    private PagedList<News_list> pagedList;

    private final String API_KEY = "c44b38deba074d9ab0fef1e359c80072";
    private final String BASE_URL = "https://newsapi.org";
    private final String CA_TOP_HEADLINES_URL = BASE_URL+
            "/v2/top-headlines?country=ca&apiKey=" +API_KEY;


    private VolleySingleton volleySingleton;
    private List<NewsMod> newsModList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        volleySingleton = VolleySingleton.getInstance(this);
        initContent();

    }

    private void initContent(){


        getJson();

    }

    private void getJson(){
        newsModList = new ArrayList<>();

        volleySingleton.volleyGETRequest(CA_TOP_HEADLINES_URL, new VolleyResultCallback() {
            @Override
            public void jsonResponse(JSONObject response) {
                try{
                    JSONArray articleArr=response.getJSONArray("articles");
                    for(int i =0;i<articleArr.length();i++){
                        JSONObject jObj = articleArr.getJSONObject(i);
                        String imgUrl = jObj.getString("urlToImage");
                        String newsTitle = jObj.getString("title");
                        newsMod = new NewsMod();

                        newsMod.setImgURL(imgUrl); newsMod.setNewsName(newsTitle);
                        newsModList.add(newsMod);
                    }
                    pagingAdapter = new PagingAdapter(getApplicationContext(), newsModList);
                    recyclerView = (RecyclerView)findViewById(R.id.news_recycler_view);
                    recyclerView.setAdapter(pagingAdapter);

                }catch (JSONException je){}
            }

            @Override
            public void responseError(VolleyError error) {

            }
        });

    }
}
