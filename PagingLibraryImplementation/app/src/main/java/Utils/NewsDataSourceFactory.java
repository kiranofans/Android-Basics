package Utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import Models.ArticleResponse;
import com.example.paginglibraryimplementation.NewsDataSource;

public class NewsDataSourceFactory extends DataSource.Factory {
    private MutableLiveData<PageKeyedDataSource<Integer, ArticleResponse>> newsLiveDataSource =
            new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource<Integer, ArticleResponse> create() {
        //getting our data source object
        NewsDataSource newsDataSource = new NewsDataSource();

        //posting the datasource to get the values
        newsLiveDataSource.postValue(newsDataSource);

        //returning the datasource
        return newsDataSource;
    }

    //getter for newsliveDataSource
    public MutableLiveData<PageKeyedDataSource<Integer, ArticleResponse>> getNewsLiveDataSource(){
        return newsLiveDataSource;
    }
}
