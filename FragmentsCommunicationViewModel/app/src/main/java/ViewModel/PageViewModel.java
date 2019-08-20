package ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {
    /**
     * Live data instance used to collect the input
     * from FirstFragment and show the data on other fragments
     */
    private MutableLiveData<String> liveDataStr = new MutableLiveData<>();

    public void setLiveDataStr(String str) {
        liveDataStr.setValue(str);
    }

    public LiveData<String> getLiveDataStr() {
        return liveDataStr;
    }
}
