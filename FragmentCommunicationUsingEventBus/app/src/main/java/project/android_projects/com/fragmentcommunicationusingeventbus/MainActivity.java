package project.android_projects.com.fragmentcommunicationusingeventbus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentMgr;
    private FragmentTransaction fragmentTrans;

    private FragmentSendText fragmentSend;
    private FragmentReceiveText fragmentReceive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initContent();

    }

    private void initContent(){
        fragmentReceive = FragmentReceiveText.getInstance();
        fragmentSend = FragmentSendText.getInstance();
    }

    private void initSendFragment(Fragment fragment){
        if(fragment != null){
            fragmentMgr=getSupportFragmentManager();
            fragmentMgr.beginTransaction().replace(R.id.send_fragment,
                    new FragmentReceiveText()).commit();
        }
    }

    private void initReceiveFragment(Fragment fragment){
        if(fragment != null){
            fragmentMgr=getSupportFragmentManager();
            fragmentMgr.beginTransaction().replace(R.id.receive_fragment,
                    new FragmentReceiveText()).commit();
        }
    }
}
