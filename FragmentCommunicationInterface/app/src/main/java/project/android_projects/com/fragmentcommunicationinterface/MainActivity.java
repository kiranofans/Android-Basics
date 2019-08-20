package project.android_projects.com.fragmentcommunicationinterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements FragmentOne.OnTextStrChangeListener{
    private FragmentManager fragmentManager;

    private FragmentOne fragOne;
    private FragmentTwo fragTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        //init fragments
        initFragmentOne(); initFragmentTwo();
    }

    private void initFragmentOne(){
        fragOne = new FragmentOne();
        fragmentManager.beginTransaction().add(R.id.fragment_one,fragOne).commit();
    }

    private void initFragmentTwo(){
        fragTwo = new FragmentTwo();
        fragmentManager.beginTransaction().add(R.id.fragment_two,fragTwo).commit();
    }

    @Override
    public void onTextStrChange(String text) {
        if (fragTwo != null){
            fragTwo.onTextReceived(text);
        }
    }
}
