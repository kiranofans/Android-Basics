package Utils;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import Model.NewsMod;

public class MainViewModel extends AndroidViewModel {
    /**
     * A ViewModel is a data holder for UI Controllers
     *
     * Difference between AndroidViewModel and ViewModel is only the visibility of Context;
     * AndroidViewModel has an application Context
     */

    private Repository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public LiveData<List<NewsMod.ArticleMod>> getAllArticles() {
        return repository.getMutableLiveData();
    }

}
