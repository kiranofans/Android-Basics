package ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

//And if you need to use an Application Context, you should extend AndroidViewModel
public class PageViewModel extends ViewModel {
    /**
     * Live data instance used to collect the input
     * from FirstFragment and show the data on other fragments
     * Use for transient data (session data or other data that's are temporary)
     */

    //No contexts in ViewModels, but storing Application Context is ok because
    //Application context is tied to the Application lifecycle
    private MutableLiveData<String> liveDataStr = new MutableLiveData<>();

    public void setLiveDataStr(String str) {
        liveDataStr.setValue(str);
    }

    public LiveData<String> getLiveDataStr() {
        return liveDataStr;
    }
    //ViewModels should not contain elements that contain references to UI controllers,
    // such as Views, since this will create an indirect reference to a Context
}
