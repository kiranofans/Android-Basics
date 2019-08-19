package Utils;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import Model.NewsMod;

public class Repository {
    /**
     * Create a Repository class to interacting to LiveData
     */

    private List<NewsMod.ArticleMod> articleList = new ArrayList<>();
    private MutableLiveData<List<NewsMod.ArticleMod>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public Repository(Application application) {
        this.application = application;
    }
}
