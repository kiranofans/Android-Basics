package Utils;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import Model.NewsMod;

public class MainViewModel extends AndroidViewModel {
    /**
     * viewModels prepare and keep the data for the UI including LiveData,
     * and Observables, survive configuration change, and it is the gateway for the UI controller.
     * The UI controllers (activity and fragment) reach the ViewModel to reach the rest of
     * the application. So it's a data store for UI controllers
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
