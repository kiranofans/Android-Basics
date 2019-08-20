package project.android_projects.com.fragmentscommunicationviewmodel;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {
    /**
     * ViewModel is used to store and manage UI-related changes
     * in a lifecycle conscious way.
     * One ViewModel for one activity (usually)
     * Two or more fragments can share one ViewModel
     */

    private FragmentManager fragMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragMgr = getSupportFragmentManager();
        initFragmentOne(); initFragmentTwo();
    }

    private void initFragmentOne(){
        FragmentOne fragmentOne = new FragmentOne();
        if(fragmentOne != null){
            fragMgr.beginTransaction().add(R.id.fragment_one,
                    fragmentOne).commit();
        }
    }

    private void initFragmentTwo(){
        FragmentTwo fragmentTwo = new FragmentTwo();
        if(fragmentTwo != null){
            fragMgr.beginTransaction().add(R.id.fragment_two,
                    fragmentTwo).commit();
        }
    }
}
